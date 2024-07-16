package org.eastnets.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class User {
    private final static Logger logger = LogManager.getLogger(User.class);
    private int userId;
    private String username;
    private String password;
    private String email;
    private UserType userType;

    public User(int userId, String username, String password, String email, UserType userType) {
        setUserId(userId);
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setUserType(userType);
        logger.info("User created");
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userType=" + userType +
                '}';
    }
}
