package org.eastnets.service;

import org.eastnets.entity.User;
import org.eastnets.entity.UserType;

import java.util.List;

public interface UserService{
    User signin(String username , String password );
    void signup(User user);
    List<User> getAllUsers(UserType userType);
}
