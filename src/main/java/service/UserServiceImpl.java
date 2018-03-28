package service;

import entities.Login;
import org.springframework.beans.factory.annotation.Autowired;

import dao.LoginDAO;
import entities.Login;
import entities.User;

public class UserServiceImpl implements UserService {

    @Autowired
    public LoginDAO userDao;

    public void register(User user) {
        userDao.register(user);
    }

    public User validateUser(Login login) {
        return userDao.validateUser(login);
    }

}