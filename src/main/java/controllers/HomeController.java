package controllers;


import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import Utils.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
@Controller
public class HomeController implements ServletContextAware {

    private ServletContext context;
    private String connect;

    @Override
    public void setServletContext(final ServletContext servletContext) {
        this.context = servletContext;
        connect = context.getInitParameter("sql.urlLocal");
    }

    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String viewHome(Model model){
        model.addAttribute("Test", context.getInitParameter("storage.bucket"));

        return "home";
    }

    @RequestMapping(value="/upload-file", method = RequestMethod.POST)
    public String viewUploadFile(@RequestParam("file") MultipartFile file) throws ServletException,IOException
    {
        CloudStorageHelper storageService = new CloudStorageHelper();
        storageService.uploadFile(file,context.getInitParameter("storage.bucket"));

        return "upload";
    }

    @RequestMapping(value="/delete", method = RequestMethod.GET)
    public String viewSuccess(String fileId){
        CloudStorageHelper storageService = new CloudStorageHelper();
        storageService.deleteImageUrl(fileId,context.getInitParameter("storage.bucket"));
        return "success";
    }
}