package service;

import entities.Login;
import entities.User;

public interface UserService {

    void register(User user);

    User validateUser(Login login);
}