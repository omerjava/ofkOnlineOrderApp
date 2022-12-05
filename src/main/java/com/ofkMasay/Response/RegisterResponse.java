package com.ofkMasay.Response;

import com.ofkMasay.Entity.Type;
import com.ofkMasay.Entity.User;

import java.sql.Timestamp;

public class RegisterResponse {

    private Long id;
    private String username;
    private String email;
    private String name;
    private String tel;
    private String address;
    private Type type;
    private Timestamp createdDate;

    public RegisterResponse(User savedUser) {
        this.id = savedUser.getId();
        this.username = savedUser.getUsername();
        this.email = savedUser.getEmail();
        this.name = savedUser.getName();
        this.tel = savedUser.getTel();
        this.address = savedUser.getAddress();
        this.type = savedUser.getType();
        this.createdDate = savedUser.getCreatedDate();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
