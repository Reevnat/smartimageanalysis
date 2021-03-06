package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import entities.User;
import service.UserService;

@Controller
public class RegistrationController {

    @Autowired
    public UserService userService;
    @RequestMapping(value = "/register", method = RequestMethod.GET)

    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("user", new User());

        return mav;
    }

    @RequestMapping(value = "/registerProcess", method = RequestMethod.POST)

    public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response,
                                @ModelAttribute("user") User user, @ModelAttribute("login") Login login) {

        if (userService.findByUsername(user.getEmail()) != null)
        {
            ModelAndView mav = new ModelAndView("register");
            mav.addObject("message", "User already exists");
            return mav;
        }else{
            userService.register(user);

            //return new ModelAndView("/login", "email", user.getEmail());
            ModelAndView mav = new ModelAndView("login");
            mav.addObject("message", "Registration Successful!");
            return mav;
        }
    }
}