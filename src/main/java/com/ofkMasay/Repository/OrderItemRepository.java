package com.ofkMasay.Repository;

import com.ofkMasay.Entity.OrderItem;
import com.ofkMasay.Exception.CustomException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderItemRepository {

    private static final String databaseURL = "jdbc:mysql://127.0.0.1:3306/masay";

    private static final String databaseUsername = "root";

    private static final String databasePassword = "Student12";


    public List<OrderItem> getOrderItems(){

        List<OrderItem> results = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                try {
                    String sql = "SELECT*FROM order_item;";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setId(resultSet.getLong("id"));
                        orderItem.setName(resultSet.getString("name"));
                        orderItem.setOrderId(resultSet.getLong("orderId"));
                        orderItem.setPrice(resultSet.getBigDecimal("price"));
                        orderItem.setRestaurantId(resultSet.getLong("restaurantId"));
                        orderItem.setQuantity(resultSet.getInt("quantity"));
                        orderItem.setDateOfAddingItem(resultSet.getTimestamp("dateOfAddingItem"));

                        results.add(orderItem);
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

    public boolean createOrderItem(OrderItem orderItem){

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String sql = "INSERT INTO order_item (name, orderId, price, restaurantId, quantity) VALUES(?, ?, ?, ?, ?);";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, orderItem.getName());
                preparedStatement.setLong(2, orderItem.getOrderId());
                preparedStatement.setBigDecimal(3, orderItem.getPrice());
                preparedStatement.setLong(4, orderItem.getRestaurantId());
                preparedStatement.setLong(5, orderItem.getQuantity());

                if(preparedStatement.executeUpdate()==1){
                    return true;
                }else {
                    throw new CustomException("Order item registration is not successful");
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

    public OrderItem getOrderItemById(Long id){
        OrderItem orderItem = new OrderItem();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String sql = "SELECT*FROM order_item WHERE id=?;";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setLong(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    orderItem.setId(resultSet.getLong("id"));
                    orderItem.setName(resultSet.getString("name"));
                    orderItem.setOrderId(resultSet.getLong("orderId"));
                    orderItem.setPrice(resultSet.getBigDecimal("price"));
                    orderItem.setRestaurantId(resultSet.getLong("restaurantId"));
                    orderItem.setQuantity(resultSet.getInt("quantity"));
                    orderItem.setDateOfAddingItem(resultSet.getTimestamp("dateOfAddingItem"));
                }else{
                    throw new CustomException("No order item is found with Id "+id);
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
        return orderItem;
    }

    public void updateOrderItem(Long id, OrderItem item){

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                OrderItem itemToUpdate = getOrderItemById(id);

                if (item.getQuantity()!=null) itemToUpdate.setQuantity(item.getQuantity());

                String sql = "UPDATE order_item SET quantity=? WHERE id=?;";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setInt(1, itemToUpdate.getQuantity());
                preparedStatement.setLong(2, id);


                if(preparedStatement.executeUpdate()!=1){
                    throw new CustomException("Order item update is not successful");
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

    public boolean deleteOrderItem(Long id){

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String sql = "DELETE FROM order_item WHERE id=?;";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setLong(1, id);

                return preparedStatement.executeUpdate()==1;

            } else {
                System.out.println("Failed to make connection!");
                throw new CustomException("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            throw new CustomException("SQL error: "+e.getMessage());
        } catch (Exception e) {
            throw new CustomException("Other connection error in delete order item: " + e.getMessage());
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

    public List<OrderItem> getOrderItemsByOrderId(Long id){

        List<OrderItem> results = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                try {
                    String sql = "SELECT*FROM order_item WHERE orderId=?;";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setLong(1, id);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setId(resultSet.getLong("id"));
                        orderItem.setName(resultSet.getString("name"));
                        orderItem.setOrderId(resultSet.getLong("orderId"));
                        orderItem.setPrice(resultSet.getBigDecimal("price"));
                        orderItem.setRestaurantId(resultSet.getLong("restaurantId"));
                        orderItem.setQuantity(resultSet.getInt("quantity"));
                        orderItem.setDateOfAddingItem(resultSet.getTimestamp("dateOfAddingItem"));

                        results.add(orderItem);
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
