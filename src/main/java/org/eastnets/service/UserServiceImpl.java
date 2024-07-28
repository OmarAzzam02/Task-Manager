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
    public UserServiceImpl(UserRepositoryImpl userRepositoryImpl ) {
        db = userRepositoryImpl;

    }


    @Override
    public User signin(String username, String password) {
        try {
            logger.info("Attempting to signin user  {}", username);
            User user = db.login(username, password);


            logger.info("user not null returning user");
            return user;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            logger.info("Attempting to update user  {}", user.getUsername());
            db.update(user);
            logger.info("user updated");
        }catch (Exception e) {
            logger.error( "in the update user {}", e.getMessage());
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
    public void updatePrivilege(User user, UserType role) {
        try {
         logger.info("Attempting to update privilege user {}", user.getUsername());
        if(!role.hasUpdatePrivlage()) throw new Exception("User Does not have update Type Privlage");

        db.updatePrivlage(user);

        }catch (Exception e){
            logger.error("Error updating privilege user {}", user.getUsername());
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
