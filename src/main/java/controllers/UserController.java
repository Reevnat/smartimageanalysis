package controllers;

import dao.UserDao;
import entities.Result;
import entities.User;
import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.sql.SQLException;

@Controller
public class UserController implements ServletContextAware {

    private ServletContext context;
    private String connect;

    @Override
    public void setServletContext(final ServletContext servletContext) {
        this.context = servletContext;
        String deploymentEnvironment = context.getInitParameter("deployment.environment");
        connect = context.getInitParameter(deploymentEnvironment.toLowerCase().equals("local") ? "sql.urlLocal": "sql.urlRemote");
    }

    @RequestMapping(value="/users", method = RequestMethod.GET)
    public String viewHome(Model model, String cursor){
        try {
            UserDao dao = new UserDao(connect);
            Result<User> result = dao.listUses(cursor);

            context.setAttribute("users", result.result);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "users";
    }

    @RequestMapping(value="/delete-users", method = RequestMethod.POST)
    public String deleteUsers(@RequestParam("id") Long id){

        try {
            UserDao dao = new UserDao(connect);
            dao.deleteUser(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "users";
    }
}
