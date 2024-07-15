package org.eastnets.service;

import org.eastnets.databaseservice.DataBaseProvider;
import org.eastnets.entity.User;
import org.eastnets.entity.UserType;

import java.util.List;

public class UserServiceProvider implements  UserService {


   private final DataBaseProvider db = new DataBaseProvider();




    @Override
    public User signin(String username, String password) {
       try {
       User user =  db.login(username, password);
       if(user!=null) return user;
       else  throw new Exception("Invalid username or password");

       }catch (Exception e){
        System.out.println(e.getMessage());
        return null;
       }
    }

    @Override
    public void signup(User user) {
        db.addUser(user);
    }

    @Override
    public List<User> getAllUsers(UserType userType)  {
        try {
        if (userType.hasViewAllTasksAndUsersPrivlage())
            return  db.getAllUsersFromDataBase();

        throw new Exception("You dont have this privlage");

        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }

    }
}
