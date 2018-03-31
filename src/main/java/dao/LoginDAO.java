package dao;

import entities.Login;

import entities.User;

public interface LoginDAO {

    void register(User user);

    User validateUser(Login login);

    User findByUsername(String username);

}