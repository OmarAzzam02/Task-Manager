package org.eastnets.entity;

public class User {

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
}
