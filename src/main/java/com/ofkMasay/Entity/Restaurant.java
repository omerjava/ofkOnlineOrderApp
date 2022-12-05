package com.ofkMasay.Entity;

import java.sql.Timestamp;
import java.util.List;

public class Restaurant {

    private Long id;
    private String name;
    private List<MenuItem> menu;
    private List<Order> orders;
    private Long ownerId;
    private String address;
    private String tel;
    private String email;
    private Boolean isActive;
    private List<OpenHours> openHours;
    private String logoUrl;
    private Timestamp registerDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        this.isActive = active;
    }

    public List<OpenHours> getOpenHours() {
        return openHours;
    }

    public void setOpenHours(List<OpenHours> openHours) {
        this.openHours = openHours;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Timestamp getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Timestamp registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", menu=" + menu +
                ", orders=" + orders +
                ", ownerId=" + ownerId +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", openHours=" + openHours +
                ", logoUrl='" + logoUrl + '\'' +
                ", registerDate=" + registerDate +
                '}';
    }
}
