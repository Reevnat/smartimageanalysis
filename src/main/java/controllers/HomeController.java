package controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import Utils.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
@Controller
public class HomeController  extends HttpServlet {

    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String viewHome(){
        return "home";
    }

    @RequestMapping(value="/upload-file", method = RequestMethod.POST)
    public String viewUploadFile(@RequestParam("file") MultipartFile file) throws ServletException,IOException
    {
        CloudStorageHelper storageService = new CloudStorageHelper();
        storageService.uploadFile(file,"smia");

        return "upload";
    }

    @RequestMapping(value="/delete", method = RequestMethod.GET)
    public String viewSuccess(String fileId){
        CloudStorageHelper storageService = new CloudStorageHelper();
        storageService.deleteImageUrl(fileId,"smia");
        return "success";
    }
}