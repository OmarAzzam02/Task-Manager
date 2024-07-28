package org.eastnets.dto.user;

import org.eastnets.entity.UserType;

public class UserManipulationDTO {

    private int userId;
    private String username;
    private String password;
    private String email;
    private UserType userType;

    public UserManipulationDTO() {}


    public UserManipulationDTO(int userId, String username, String password, String email, UserType userType) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = userType;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserManipulationDTO(int userId , String username, String password, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;

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
