package org.eastnets.dao;

import org.eastnets.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserDAO extends JpaRepository<User, Integer> {


    @Query("SELECT u FROM User u WHERE u.username = :username AND u.password = :password")
    User login(@Param("username") String enteredUsername , @Param("password") String enteredPassword);

}
