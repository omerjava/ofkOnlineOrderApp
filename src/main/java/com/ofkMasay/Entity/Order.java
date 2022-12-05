package com.ofkMasay.Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Order {

    private Long id;
    private List<OrderItem> orderItems;
    private BigDecimal totalPrice;
    private Status status = Status.IN_CART;
    private Long customerId;
    private Long restaurantId;
    private Long delivererId;
    private Boolean needDelivery;
    private Boolean isPaid;
    private Timestamp dateOfOrder;
    private Timestamp dateOfDelivery;

    public Order() {}

    public Order(BigDecimal totalPrice, Status status, Long customerId, Long restaurantId, Boolean needDelivery, Boolean isPaid) {
        this.totalPrice = totalPrice;
        this.status = status;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.needDelivery = needDelivery;
        this.isPaid = isPaid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getDelivererId() {
        return delivererId;
    }

    public void setDelivererId(Long delivererId) {
        this.delivererId = delivererId;
    }

    public Boolean getNeedDelivery() {
        return needDelivery;
    }

    public void setNeedDelivery(Boolean needDelivery) {
        this.needDelivery = needDelivery;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public Timestamp getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Timestamp dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public Timestamp getDateOfDelivery() {
        return dateOfDelivery;
    }

    public void setDateOfDelivery(Timestamp dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderItems=" + orderItems +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", customerId=" + customerId +
                ", restaurantId=" + restaurantId +
                ", delivererId=" + delivererId +
                ", needDelivery=" + needDelivery +
                ", isPaid=" + isPaid +
                ", dateOfOrder=" + dateOfOrder +
                ", dateOfDelivery=" + dateOfDelivery +
                '}';
    }
}
