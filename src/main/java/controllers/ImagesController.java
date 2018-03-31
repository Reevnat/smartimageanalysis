package controllers;

import Utils.CloudStorageHelper;
import Utils.CloudVisionHelper;
import dao.ImageDao;
import dao.LabelAnnotationDao;
import dao.UserDao;
import entities.Image;
import entities.LabelAnnotation;
import entities.Result;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.RedirectView;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller
public class ImagesController implements ServletContextAware {
    private ServletContext context;
    private String connect;
    private String bucket;

    @Autowired
    private UserService userService;

    @Override
    public void setServletContext(final ServletContext servletContext) {
        this.context = servletContext;
        connect = context.getInitParameter("sql.urlLocal");
        bucket = context.getInitParameter("storage.bucket");
    }

    User getPrincipal(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userService.findByUsername(currentPrincipalName);
    }

    @RequestMapping(value="/my-images", method = RequestMethod.GET)
    public String listMyImages(){

        try {
            ImageDao dao = new ImageDao(connect);
            Result<Image> result = dao.listImages("", getPrincipal());
            for(int i=0;i<result.result.size();i++){
                LabelAnnotationDao labelDao = new LabelAnnotationDao(connect);
                Image image = result.result.get(i);
                image.setAnnotations(labelDao.list("",image).result);
                result.result.set(i,image);
            }

            context.setAttribute("images", result.result);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "my-images";
    }

    @RequestMapping(value="/delete-image", method = RequestMethod.POST)
    public AbstractView deleteImage(@RequestParam("id") Long id){
        try {
            ImageDao dao = new ImageDao(connect);
            Image image = dao.getImage(id);
            dao.deleteImage(id);
            CloudStorageHelper storageService = new CloudStorageHelper();
            storageService.deleteImageUrl(image.getUrl(),bucket);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new RedirectView("/my-images");
    }

    @RequestMapping(value="/add-image", method = RequestMethod.GET)
    public String addImage(){
        return "add-image";
    }

    @RequestMapping(value="/add-image", method = RequestMethod.POST)
    public AbstractView addImage(@RequestParam("image") MultipartFile image) throws ServletException,IOException, SQLException,Exception
    {
        CloudStorageHelper storageService = new CloudStorageHelper();
        String url = storageService.uploadFile(image,bucket);

        try {
            ImageDao dao = new ImageDao(connect);
            Image entity = new Image();
            entity.setUrl(url);
            dao.createImage(entity,getPrincipal());

            // get annotation labels
            String path = new CloudStorageHelper().getGcsPath(entity.getUrl(),bucket);
            List<LabelAnnotation> annotations = CloudVisionHelper.detectLabels(path);
            LabelAnnotationDao annotationDao = new LabelAnnotationDao(connect);
            annotations.forEach(a->
            {
                a.setImageId(entity.getId());
                try {
                    annotationDao.create(a);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new RedirectView("/my-images");
    }

    @RequestMapping(value="/add-annotations", method = RequestMethod.POST)
    public AbstractView addAnnotation(@RequestParam("id") Long id) throws  Exception{
        try {
            ImageDao dao = new ImageDao(connect);
            Image image = dao.getImage(id);
            LabelAnnotation annotation = new LabelAnnotation();

            String path = new CloudStorageHelper().getGcsPath(image.getUrl(),bucket);
            List<LabelAnnotation> annotations = CloudVisionHelper.detectLabels(path);
            LabelAnnotationDao annotationDao = new LabelAnnotationDao(connect);
            annotations.forEach(a->
            {
                a.setImageId(image.getId());
                try {
                    annotationDao.create(a);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new RedirectView("/my-images");
    }
}