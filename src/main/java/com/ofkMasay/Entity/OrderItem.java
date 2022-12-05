package com.ofkMasay.Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderItem {

    private Long id;
    private String name;
    private Long orderId;
    private Long restaurantId;
    private BigDecimal price;
    private Integer quantity;
    private Timestamp dateOfAddingItem;

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Timestamp getDateOfAddingItem() {
        return dateOfAddingItem;
    }

    public void setDateOfAddingItem(Timestamp dateOfAddingItem) {
        this.dateOfAddingItem = dateOfAddingItem;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", orderId=" + orderId +
                ", restaurantId=" + restaurantId +
                ", price=" + price +
                ", quantity=" + quantity +
                ", dateOfAddingItem=" + dateOfAddingItem +
                '}';
    }
}
