package controllers;

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
    public AbstractView deleteUsers(@RequestParam("id") Long id){
        String connect = context.getInitParameter("sql.urlRemote");

        try {
            ImageDao dao = new ImageDao(connect);
            dao.deleteImage(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new RedirectView("/my-images");
    }
}