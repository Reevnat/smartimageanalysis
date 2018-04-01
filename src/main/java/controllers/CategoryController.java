package controllers;

import Utils.CloudStorageHelper;
import Utils.CloudVisionHelper;
import dao.CategoryDao;
import dao.LabelDao;
import entities.Category;
import entities.Label;
import entities.Result;
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
import java.util.List;

@Controller
public class CategoryController implements ServletContextAware {
    private ServletContext context;
    private String connect;

    @Override
    public void setServletContext(final ServletContext servletContext) {
        this.context = servletContext;
        String deploymentEnvironment = context.getInitParameter("deployment.environment");
        connect = context.getInitParameter(deploymentEnvironment.toLowerCase().equals("local") ? "sql.urlLocal": "sql.urlRemote");
    }
    @RequestMapping(value="/categories", method = RequestMethod.GET)
    public String listCategories(){

        try {
            CategoryDao dao = new CategoryDao(connect);
            Result<Category> result = dao.list("");
            for(int i=0;i<result.result.size();i++){
                LabelDao labelDao = new LabelDao(connect);
                Category category = result.result.get(i);
                category.setLabels(labelDao.list("",category).result);
                result.result.set(i,category);
            }

            context.setAttribute("result", result.result);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "categories";
    }

    @RequestMapping(value="/add-category", method = RequestMethod.GET)
    public String addCategory(){
        return "add-category";
    }

    @RequestMapping(value="/add-category", method = RequestMethod.POST)
    public AbstractView addCategory(String name) throws ServletException, SQLException,Exception
    {
        try {
            CategoryDao dao = new CategoryDao(connect);
            Category entity = new Category();
            entity.setName(name);
            dao.create(entity);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new RedirectView("/categories");
    }

    @RequestMapping(value="/delete-category", method = RequestMethod.POST)
    public AbstractView deleteCategory(@RequestParam("id") Long id){
        try {
            CategoryDao dao = new CategoryDao(connect);
            dao.delete(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new RedirectView("/categories");
    }

    @RequestMapping(value="/add-label", method = RequestMethod.GET)
    public String addLabel(Model model, int categoryId){
        model.addAttribute("categoryId",categoryId);
        return "add-label";
    }

    @RequestMapping(value="/add-label", method = RequestMethod.POST)
    public AbstractView addLabel(String description, double threshold, int categoryId) throws ServletException, SQLException,Exception
    {
        try {
            LabelDao dao = new LabelDao(connect);
            Label entity = new Label();
            entity.setDescription(description);
            entity.setThreshold(threshold);
            entity.setCategoryId(categoryId);
            dao.create(entity);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new RedirectView("/categories");
    }
}
