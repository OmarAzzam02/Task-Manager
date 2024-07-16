package org.eastnets.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eastnets.databaseservice.DataBaseProvider;
import org.eastnets.entity.User;
import org.eastnets.entity.UserType;

import java.util.List;

public class UserServiceProvider implements UserService {

    private final static Logger logger = LogManager.getLogger(User.class);
    private final DataBaseProvider db = new DataBaseProvider();


    @Override
    public User signin(String username, String password) {
        try {
            logger.info("Attempting to signin user  {}", username);
            User user = db.login(username, password);
            logger.debug("user after signin: {}", user);
            if (user == null)
                logger.error("User not found", new Exception("Invalid username or password"));

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
            db.addUser(user);
            logger.debug("user added {} ", user.getUsername());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
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
