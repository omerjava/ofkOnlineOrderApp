package com.ofkMasay.Repository;

import com.ofkMasay.Entity.Order;
import com.ofkMasay.Entity.Status;
import com.ofkMasay.Exception.CustomException;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

    private static final String databaseURL = "jdbc:mysql://127.0.0.1:3306/masay";

    private static final String databaseUsername = "root";

    private static final String databasePassword = "Student12";

    private final OrderItemRepository orderItemRepository;

    public OrderRepository(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }


    public List<Order> getOrders(){

        List<Order> results = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                try {
                    String sql = "SELECT*FROM orders;";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        Order order = new Order();
                        order.setId(resultSet.getLong("id"));
                        order.setStatus(Status.valueOf(resultSet.getString("status")));
                        order.setTotalPrice(resultSet.getBigDecimal("totalPrice"));
                        order.setRestaurantId(resultSet.getLong("restaurantId"));
                        order.setCustomerId(resultSet.getLong("customerId"));
                        order.setDelivererId(resultSet.getLong("delivererId"));
                        order.setNeedDelivery(resultSet.getBoolean("needDelivery"));
                        order.setPaid(resultSet.getBoolean("isPaid"));
                        order.setDateOfOrder(resultSet.getTimestamp("dateOfOrder"));
                        order.setDateOfDelivery(resultSet.getTimestamp("dateOfDelivery"));
                        order.setOrderItems(orderItemRepository.getOrderItemsByOrderId(order.getId()));

                        results.add(order);
                    }
                    System.out.println(results);
                }catch (Exception e){
                    throw new CustomException("Orders could not be found due to: "+e.getMessage());
                }

            } else {
                System.out.println("Failed to make connection!");
                throw new CustomException("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            throw new CustomException("SQL error: "+e.getMessage());
        } catch (Exception e) {
            throw new CustomException("Other connection error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connection is closed");
                } catch (Exception e) {
                    throw new CustomException("Connection close error: " + e.getMessage());
                }
            }
        }
        return results;
    }

    public boolean createOrder(Order order){

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String sql = "INSERT INTO orders (totalPrice, status, customerId, restaurantId, needDelivery, isPaid) VALUES(?, ?, ?, ?, ?, ?);";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setBigDecimal(1, order.getTotalPrice());
                preparedStatement.setString(2, order.getStatus().toString());
                preparedStatement.setLong(3, order.getCustomerId());
                preparedStatement.setLong(4, order.getRestaurantId());
                preparedStatement.setBoolean(5, order.getNeedDelivery());
                preparedStatement.setBoolean(6, order.getPaid());

                if(preparedStatement.executeUpdate()==1){
                    return true;
                }else {
                    throw new CustomException("Order registration is not successful");
                }

            } else {
                System.out.println("Failed to make connection!");
                throw new CustomException("Failed to make connection!");

            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            throw new CustomException("SQL error: "+e.getMessage());
        } catch (Exception e) {
            throw new CustomException("Other connection error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connection is closed");
                } catch (Exception e) {
                    throw new CustomException("Connection close error: " + e.getMessage());
                }
            }
        }
    }

    public Order getOrderById(Long id){
        Order order = new Order();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String sql = "SELECT*FROM orders WHERE id=?;";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setLong(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    order.setId(resultSet.getLong("id"));
                    order.setStatus(Status.valueOf(resultSet.getString("status")));
                    order.setTotalPrice(resultSet.getBigDecimal("totalPrice"));
                    order.setRestaurantId(resultSet.getLong("restaurantId"));
                    order.setCustomerId(resultSet.getLong("customerId"));
                    order.setDelivererId(resultSet.getLong("delivererId"));
                    order.setNeedDelivery(resultSet.getBoolean("needDelivery"));
                    order.setPaid(resultSet.getBoolean("isPaid"));
                    order.setDateOfOrder(resultSet.getTimestamp("dateOfOrder"));
                    order.setDateOfDelivery(resultSet.getTimestamp("dateOfDelivery"));
                    order.setOrderItems(orderItemRepository.getOrderItemsByOrderId(order.getId()));

                }else{
                    throw new CustomException("No order is found with Id "+id);
                }
            } else {
                System.out.println("Failed to make connection!");
                throw new CustomException("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            throw new CustomException("SQL error: "+e.getMessage());
        } catch (Exception e) {
            throw new CustomException("Other connection error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connection is closed");
                } catch (Exception e) {
                    throw new CustomException("Connection close error: " + e.getMessage());
                }
            }
        }
        return order;
    }

    public boolean updateOrder(Long id, Order order){

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                Order orderToUpdate = getOrderById(id);

                if (order.getStatus()!=null) orderToUpdate.setStatus(order.getStatus());
                if (order.getPaid()!=null) orderToUpdate.setPaid(order.getPaid());
                if (order.getNeedDelivery()!=null) orderToUpdate.setNeedDelivery(order.getNeedDelivery());
                if (order.getDelivererId()!=null) orderToUpdate.setDelivererId(order.getDelivererId());
                if (order.getDateOfDelivery()!=null) orderToUpdate.setDateOfDelivery(order.getDateOfDelivery());
                if (orderToUpdate.getOrderItems() != null) {
                    orderToUpdate.setTotalPrice(orderToUpdate.getOrderItems().stream()
                            .map(v -> v.getPrice().multiply(BigDecimal.valueOf(v.getQuantity())))
                            .reduce((acc, v) -> acc.add(v)).orElseThrow(() -> new CustomException("Total price is 0.") ));
                }

                System.out.println(orderToUpdate.getTotalPrice());

                String sql = "UPDATE orders SET status=?, isPaid=?, needDelivery=?, delivererId=?, dateOfOrder=?, dateOfDelivery=?, totalPrice=? WHERE id=?;";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, orderToUpdate.getStatus().toString());
                preparedStatement.setBoolean(2, orderToUpdate.getPaid());
                preparedStatement.setBoolean(3, orderToUpdate.getNeedDelivery());
                preparedStatement.setLong(4, orderToUpdate.getDelivererId());
                preparedStatement.setTimestamp(5, orderToUpdate.getDateOfOrder());
                preparedStatement.setTimestamp(6, orderToUpdate.getDateOfDelivery());
                preparedStatement.setBigDecimal(7, orderToUpdate.getTotalPrice());

                preparedStatement.setLong(8, id);

                return preparedStatement.executeUpdate()==1;

            } else {
                System.out.println("Failed to make connection!");
                throw new CustomException("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("At update order class in repo SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            throw new CustomException("SQL error: "+e.getMessage());
        } catch (Exception e) {
            throw new CustomException("Other connection error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connection is closed");
                } catch (Exception e) {
                    throw new CustomException("Connection close error: " + e.getMessage());
                }
            }
        }
    }

    public boolean deleteOrder(Long id){

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String sql = "DELETE FROM orders WHERE id=?;";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setLong(1, id);

                return preparedStatement.executeUpdate()!=1;

            } else {
                System.out.println("Failed to make connection!");
                throw new CustomException("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            throw new CustomException("SQL error: "+e.getMessage());
        } catch (Exception e) {
            throw new CustomException("Other connection error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connection is closed");
                } catch (Exception e) {
                    throw new CustomException("Connection close error: " + e.getMessage());
                }
            }
        }
    }

    public List<Order> getOrdersByRestaurantId(Long id){

        List<Order> results = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                try {
                    String sql = "SELECT*FROM orders WHERE restaurantId=?;";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setLong(1, id);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        Order order = new Order();
                        order.setId(resultSet.getLong("id"));
                        order.setStatus(Status.valueOf(resultSet.getString("status")));
                        order.setTotalPrice(resultSet.getBigDecimal("totalPrice"));
                        order.setRestaurantId(resultSet.getLong("restaurantId"));
                        order.setCustomerId(resultSet.getLong("customerId"));
                        order.setDelivererId(resultSet.getLong("delivererId"));
                        order.setNeedDelivery(resultSet.getBoolean("needDelivery"));
                        order.setPaid(resultSet.getBoolean("isPaid"));
                        order.setDateOfOrder(resultSet.getTimestamp("dateOfOrder"));
                        order.setDateOfDelivery(resultSet.getTimestamp("dateOfDelivery"));
                        order.setOrderItems(orderItemRepository.getOrderItemsByOrderId(order.getId()));

                        results.add(order);
                    }
                    System.out.println(results);
                }catch (Exception e){
                    throw new CustomException("Order items could not be found due to: "+e.getMessage());
                }

            } else {
                System.out.println("Failed to make connection!");
                throw new CustomException("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            throw new CustomException("SQL error: "+e.getMessage());
        } catch (Exception e) {
            throw new CustomException("Other connection error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connection is closed");
                } catch (Exception e) {
                    throw new CustomException("Connection close error: " + e.getMessage());
                }
            }
        }
        return results;
    }

    public List<Order> getOrdersByUserId(Long id){

        List<Order> results = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                try {
                    String sql = "SELECT*FROM orders WHERE customerId=?;";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setLong(1, id);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        Order order = new Order();
                        order.setId(resultSet.getLong("id"));
                        order.setStatus(Status.valueOf(resultSet.getString("status")));
                        order.setTotalPrice(resultSet.getBigDecimal("totalPrice"));
                        order.setRestaurantId(resultSet.getLong("restaurantId"));
                        order.setCustomerId(resultSet.getLong("customerId"));
                        order.setDelivererId(resultSet.getLong("delivererId"));
                        order.setNeedDelivery(resultSet.getBoolean("needDelivery"));
                        order.setPaid(resultSet.getBoolean("isPaid"));
                        order.setDateOfOrder(resultSet.getTimestamp("dateOfOrder"));
                        order.setDateOfDelivery(resultSet.getTimestamp("dateOfDelivery"));
                        order.setOrderItems(orderItemRepository.getOrderItemsByOrderId(order.getId()));

                        results.add(order);
                    }
                    System.out.println(results);
                }catch (Exception e){
                    throw new CustomException("Order items could not be found due to: "+e.getMessage());
                }

            } else {
                System.out.println("Failed to make connection!");
                throw new CustomException("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            throw new CustomException("SQL error: "+e.getMessage());
        } catch (Exception e) {
            throw new CustomException("Other connection error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connection is closed");
                } catch (Exception e) {
                    throw new CustomException("Connection close error: " + e.getMessage());
                }
            }
        }
        return results;
    }

    public List<Order> getOrdersByDelivererId(Long id) {

        List<Order> results = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                try {
                    String sql = "SELECT*FROM orders WHERE delivererId=?;";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setLong(1, id);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        Order order = new Order();
                        order.setId(resultSet.getLong("id"));
                        order.setStatus(Status.valueOf(resultSet.getString("status")));
                        order.setTotalPrice(resultSet.getBigDecimal("totalPrice"));
                        order.setRestaurantId(resultSet.getLong("restaurantId"));
                        order.setCustomerId(resultSet.getLong("customerId"));
                        order.setDelivererId(resultSet.getLong("delivererId"));
                        order.setNeedDelivery(resultSet.getBoolean("needDelivery"));
                        order.setPaid(resultSet.getBoolean("isPaid"));
                        order.setDateOfOrder(resultSet.getTimestamp("dateOfOrder"));
                        order.setDateOfDelivery(resultSet.getTimestamp("dateOfDelivery"));
                        order.setOrderItems(orderItemRepository.getOrderItemsByOrderId(order.getId()));

                        results.add(order);
                    }
                    System.out.println(results);
                }catch (Exception e){
                    throw new CustomException("Order items could not be found due to: "+e.getMessage());
                }

            } else {
                System.out.println("Failed to make connection!");
                throw new CustomException("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            throw new CustomException("SQL error: "+e.getMessage());
        } catch (Exception e) {
            throw new CustomException("Other connection error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connection is closed");
                } catch (Exception e) {
                    throw new CustomException("Connection close error: " + e.getMessage());
                }
            }
        }
        return results;
    }

}
