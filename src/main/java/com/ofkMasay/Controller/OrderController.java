package com.ofkMasay.Controller;

import com.ofkMasay.Entity.Order;
import com.ofkMasay.Entity.OrderItem;
import com.ofkMasay.Exception.CustomException;
import com.ofkMasay.Service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orderItem/{userId}")  // create order item and return updated order
    public ResponseEntity<Order> createOrderItem(@PathVariable Long userId, @RequestBody OrderItem orderItem){
        return new ResponseEntity<>(orderService.createOrderItemAndReturnUpdatedOrder( userId, orderItem), HttpStatus.CREATED);
    }

    @PutMapping("/orderItem/{orderItemId}")  // only can change quantity of order item
    public ResponseEntity<Order> updateOrderItem(@PathVariable Long orderItemId, @RequestBody OrderItem orderItem){
        return new ResponseEntity<>(orderService.updateOrderItemAndReturnUpdatedOrder(orderItemId, orderItem), HttpStatus.CREATED);
    }

    @DeleteMapping("/orderItem/{orderItemId}")  // delete order item and return updated order
    public ResponseEntity<Order> deleteOrderItem(@PathVariable Long orderItemId){
        if(orderService.deleteOrderItemAndReturnUpdatedOrder(orderItemId) == null) {
            throw new CustomException("Order has no more any item and is deleted");
        }
        return new ResponseEntity<>(orderService.deleteOrderItemAndReturnUpdatedOrder(orderItemId), HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders(){
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id){
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<Void> updateOrder(@PathVariable Long id, @RequestBody Order order){
        if(orderService.updateOrder(id, order)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
        if(orderService.deleteOrder(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/orders/restaurant/{id}")
    public ResponseEntity<List<Order>> getAllOrdersByRestaurant(@PathVariable Long id){
        return new ResponseEntity<>(orderService.getAllOrdersByRestaurant(id), HttpStatus.OK);
    }

    @GetMapping("/orders/customer/{id}")  // customer orders
    public ResponseEntity<List<Order>> getAllOrdersByCustomer(@PathVariable Long id){
        return new ResponseEntity<>(orderService.getAllOrdersByCustomer(id), HttpStatus.OK);
    }

    @GetMapping("/orders/deliverer/{id}")
    public ResponseEntity<List<Order>> getAllOrdersByDeliverer(@PathVariable Long id){
        return new ResponseEntity<>(orderService.getAllOrdersByDeliverer(id), HttpStatus.OK);
    }

}
