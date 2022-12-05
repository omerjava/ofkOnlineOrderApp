package com.ofkMasay.Service;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.ofkMasay.Entity.Order;
import com.ofkMasay.Entity.OrderItem;
import com.ofkMasay.Entity.Status;
import com.ofkMasay.Exception.CustomException;
import com.ofkMasay.Repository.OrderItemRepository;
import com.ofkMasay.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }


    public List<Order> getAllOrders() {
        try{
            return orderRepository.getOrders();
        }catch (Exception e){
            throw new CustomException("Orders could not found due to: "+e.getMessage());
        }
    }

    public Order getOrderById(Long id) {
        try{
            return orderRepository.getOrderById(id);
        }catch (Exception e){
            throw new CustomException("Order could not be found due to: "+e.getMessage());
        }
    }

    public boolean updateOrder(Long id, Order order) {
        try{
            return orderRepository.updateOrder(id, order);
        }catch (Exception e){
            throw new CustomException("Order could not be updated due to: "+e.getMessage());
        }
    }

    public boolean deleteOrder(Long id) {
        try{
            return orderRepository.deleteOrder(id);
        }catch (Exception e){
            throw new CustomException("Order could not be deleted due to: "+e.getMessage());
        }
    }

    public List<Order> getAllOrdersByRestaurant(Long id) {
        try{
            return orderRepository.getOrdersByRestaurantId(id);
        }catch (Exception e){
            throw new CustomException("Orders could not found due to: "+e.getMessage());
        }
    }

    public List<Order> getAllOrdersByCustomer(Long id) {
        try{
            return orderRepository.getOrdersByUserId(id);
        }catch (Exception e){
            throw new CustomException("Orders could not found due to: "+e.getMessage());
        }
    }

    public List<Order> getAllOrdersByDeliverer(Long id) {
        try{
            return orderRepository.getOrdersByDelivererId(id);
        }catch (Exception e){
            throw new CustomException("Orders could not found due to: "+e.getMessage());
        }
    }

    public Order createOrderItemAndReturnUpdatedOrder(Long userId, OrderItem orderItem) {
        try{
            List<Order> orders = orderRepository.getOrdersByUserId(userId);
            Order order = orders.stream().filter(v -> v.getStatus() == Status.IN_CART &&
                    Objects.equals(v.getRestaurantId(), orderItem.getRestaurantId())).findAny().orElse(null);
            if (order!=null) orderItem.setOrderId(order.getId());
            if(orderItem.getOrderId()!=null){
                    orderItemRepository.createOrderItem(orderItem);
                    orderRepository.updateOrder(orderItem.getOrderId(), new Order());  // updating total price only
                    return orderRepository.getOrderById(orderItem.getOrderId());
            }
            else{
                if(orderRepository.createOrder(new Order(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())),
                        Status.IN_CART, userId, orderItem.getRestaurantId(), true, false ))){
                    List<Order> orders2 = orderRepository.getOrdersByUserId(userId);
                    Order order2 = orders2.stream().filter(v -> v.getStatus() == Status.IN_CART &&
                            Objects.equals(v.getRestaurantId(), orderItem.getRestaurantId())).findAny().orElse(null);
                    if (order2!=null) {
                        orderItem.setOrderId(order2.getId());
                        orderItemRepository.createOrderItem(orderItem);
                        orderRepository.updateOrder(order2.getId(), new Order());
                        return orderRepository.getOrderById(order2.getId());
                    }
                    else throw new CustomException("No order is found in cart!");
                }
                else throw new CustomException("New order could not be created!");
            }
        }catch (Exception e){
            throw new CustomException("Create order item could not done due to: "+e.getMessage());
        }
    }

    public Order updateOrderItemAndReturnUpdatedOrder(Long orderItemId, OrderItem orderItem) {
        try{
            orderItemRepository.updateOrderItem(orderItemId, orderItem);
            Long orderId = orderItemRepository.getOrderItemById(orderItemId).getOrderId();
            orderRepository.updateOrder(orderId, new Order());
            return orderRepository.getOrderById(orderId);
        }catch (Exception e){
            throw new CustomException("Update order item could not done due to: "+e.getMessage());
        }
    }

    public Order deleteOrderItemAndReturnUpdatedOrder(Long orderItemId) {
        try{
            Long orderId = orderItemRepository.getOrderItemById(orderItemId).getOrderId();
            orderItemRepository.deleteOrderItem(orderItemId);
            orderRepository.updateOrder(orderId, new Order());
            if (orderRepository.getOrderById(orderId).getOrderItems().size()==0){
                orderRepository.deleteOrder(orderId);
                return null;
            }else {
                return orderRepository.getOrderById(orderId);
            }
        }catch (Exception e){
            throw new CustomException("Delete order item could not done due to: "+e.getMessage());
        }

    }
}
