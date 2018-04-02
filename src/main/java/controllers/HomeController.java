package controllers;


import dao.ImageDao;
import dao.LabelAnnotationDao;
import dao.SearchDao;
import entities.Image;
import entities.Result;
import entities.SearchResult;
import entities.SimilarityScore;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import Utils.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController implements ServletContextAware {

    private ServletContext context;
    private String connect;

    @Override
    public void setServletContext(final ServletContext servletContext) {
        this.context = servletContext;

        String deploymentEnvironment = context.getInitParameter("deployment.environment");
        connect = context.getInitParameter(deploymentEnvironment.toLowerCase().equals("local") ? "sql.urlLocal": "sql.urlRemote");
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public String viewHome(Model model, String keywords){

        try {
            SearchDao dao = new SearchDao(connect);
            Result<SearchResult> result = dao.search(1,keywords,"",50);
            for(int i=0;i<result.result.size();i++){
                ImageDao imageDao = new ImageDao(connect);
                SearchResult searchResult = result.result.get(i);
                Long imageId = new Long(searchResult.getImageId());
                Image image = imageDao.getImage(imageId);

                LabelAnnotationDao labelDao = new LabelAnnotationDao(connect);
                searchResult.setAnnotations(labelDao.list("",image).result);

                searchResult.setSimilarityScores(imageDao.similarityScoreList(image).result);

                result.result.set(i, searchResult);
            }

            context.setAttribute("result", result.result);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        context.setAttribute("keywords", keywords);

        return "home";
    }
}