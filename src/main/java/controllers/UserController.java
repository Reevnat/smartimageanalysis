package controllers;

import dao.UserDao;
import entities.Result;
import entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletContext;
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
    public AbstractView deleteUsers(@RequestParam("id") Long id){

        try {
            UserDao dao = new UserDao(connect);
            dao.deleteUser(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }



        return new RedirectView("/users");
    }

    @RequestMapping(value="/edit-user", method = RequestMethod.POST)
    public AbstractView editUser(@RequestParam("id") Long id, String password, boolean isAdmin){

        try {
            UserDao dao = new UserDao(connect);
            dao.updateUser(id, password, isAdmin);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new RedirectView("/users");
    }

    @RequestMapping(value="/edit-user", method = RequestMethod.GET)
    public String editUser(@RequestParam("id") Long id){

        try {
            UserDao dao = new UserDao(connect);
            User user = dao.getUser(id);
            this.context.setAttribute("id",user.getId());
            this.context.setAttribute("username",user.getEmail());
            this.context.setAttribute("password",user.getPassword());
            this.context.setAttribute("isAdmin", user.isAdmin());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "editUser";
    }
}
