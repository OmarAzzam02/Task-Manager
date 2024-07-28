package org.eastnets.repository;

import org.eastnets.entity.User;

import javax.persistence.EntityManager;
import java.util.List;

public interface UserRepository {
    void signup(User user) throws Exception;

    void update(User user);
    void updatePrivlage(User user);

    User login(String username , String password);
    List<User> getAllUsersFromDataBase();

}
