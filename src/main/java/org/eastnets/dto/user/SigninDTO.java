package org.eastnets.dto.user;

public class SigninDTO {


    private String username;
    private String password;

    public SigninDTO() {}

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
}