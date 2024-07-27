package org.eastnets.dto;

import org.eastnets.entity.UserType;

public class UserDTO {


    private String username;
    private String password;
    private String email;
    private UserType userType;

    public UserDTO() {}

    public UserDTO(String username, String password, String email, UserType userType) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = userType;
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
