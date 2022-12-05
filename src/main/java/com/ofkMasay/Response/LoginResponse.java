package com.ofkMasay.Response;

import com.ofkMasay.Entity.AccessToken;
import com.ofkMasay.Entity.Type;
import com.ofkMasay.Entity.User;

import java.sql.Timestamp;

public class LoginResponse {

    private Long id;
    private String username;
    private String token;

    private Type userType;
    private Timestamp expiryDate;

    public LoginResponse(User userFound, AccessToken token) {
        this.id = userFound.getId();
        this.username = userFound.getUsername();
        this.userType = userFound.getType();
        this.token = token.getToken();
        this.expiryDate = token.getExpiryDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Type getUserType() {
        return userType;
    }

    public void setUserType(Type userType) {
        this.userType = userType;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }
}
