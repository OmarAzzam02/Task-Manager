package org.eastnets.repository;

import org.eastnets.entity.User;

import javax.persistence.EntityManager;
import java.util.List;

public interface UserRepository {
    void signup(User user) throws Exception;



    User login(String username , String password);
    List<User> getAllUsersFromDataBase();

}
