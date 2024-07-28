package org.eastnets.service;

import org.eastnets.entity.User;
import org.eastnets.entity.UserType;

import java.util.List;

public interface UserService{
    User signin(String username , String password );
    void updateUser(User user);
    void signup(User user);
    void updatePrivilege(User user, UserType role);
    List<User> getAllUsers(UserType userType);
}
