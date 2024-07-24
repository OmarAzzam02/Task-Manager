package org.eastnets.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.eastnets.entity.User;
import org.eastnets.entity.UserType;
import org.eastnets.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final UserRepositoryImpl db;


    @Autowired
    UserServiceImpl(UserRepositoryImpl userRepositoryImpl ) {
        db = userRepositoryImpl;

    }


    @Override
    public User signin(String username, String password) {
        try {
            logger.info("Attempting to signin user  {}", username);
            User user = db.login(username, password);
            if (user == null)
                logger.error("User not found", new Exception("Invalid username or password"));

            logger.info("user not null returning user");
            return user;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void signup(User user) {
        try {
            logger.info("Attempting to signup user {}", user.getUsername());
            db.signup(user);
            logger.info("user added {} ", user.getUsername());
        } catch (Exception ex) {
            logger.error("Error signing ", ex);

        }
    }

    @Override
    public List<User> getAllUsers(UserType userType) {
        try {
            logger.info("Attempting to get all users");
            if (!userType.hasViewAllTasksAndUsersPrivlage())
                logger.error("" ,new Exception("You dont have this privlage"));
            return db.getAllUsersFromDataBase();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }
}