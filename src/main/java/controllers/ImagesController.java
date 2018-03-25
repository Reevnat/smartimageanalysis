package controllers;

import Utils.CloudStorageHelper;
import dao.ImageDao;
import entities.Image;
import entities.Result;
import entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

@Controller
public class ImagesController implements ServletContextAware {
    private ServletContext context;

    @Override
    public void setServletContext(final ServletContext servletContext) {
        this.context = servletContext;
    }

    @RequestMapping(value="/my-images", method = RequestMethod.GET)
    public String listMyImages(){
        String connect = context.getInitParameter("sql.urlLocal");

        try {
            ImageDao dao = new ImageDao(connect);
            Result<Image> result = dao.listImages("");

            context.setAttribute("images", result.result);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "my-images";
    }

    @RequestMapping(value="/delete-image", method = RequestMethod.POST)
    public AbstractView deleteImage(@RequestParam("id") Long id){
        String connect = context.getInitParameter("sql.urlLocal");

        try {
            ImageDao dao = new ImageDao(connect);
            dao.deleteImage(id);

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
    public AbstractView addImage(@RequestParam("image") MultipartFile image) throws ServletException,IOException
    {
        CloudStorageHelper storageService = new CloudStorageHelper();
        String url = storageService.uploadFile(image,context.getInitParameter("storage.bucket"));

        String connect = context.getInitParameter("sql.urlLocal");
        try {
            ImageDao dao = new ImageDao(connect);
            Image entity = new Image();
            entity.setUrl(url);
            dao.createImage(entity);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new RedirectView("/my-images");
    }
}