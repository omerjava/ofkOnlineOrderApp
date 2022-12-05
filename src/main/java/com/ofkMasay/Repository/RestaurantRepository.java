package com.ofkMasay.Repository;

import com.ofkMasay.Entity.Restaurant;
import com.ofkMasay.Exception.CustomException;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantRepository {

    private static final String databaseURL = "jdbc:mysql://127.0.0.1:3306/masay";

    private static final String databaseUsername = "root";

    private static final String databasePassword = "Student12";

    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;
    private final OpenHoursRepository openHoursRepository;

    public RestaurantRepository(MenuItemRepository menuItemRepository, OrderRepository orderRepository, OpenHoursRepository openHoursRepository) {
        this.menuItemRepository = menuItemRepository;
        this.orderRepository = orderRepository;
        this.openHoursRepository = openHoursRepository;
    }


    public List<Restaurant> getRestaurants(){

        List<Restaurant> results = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                try {
                    String sql = "SELECT*FROM restaurant;";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        Restaurant foundRestaurant = new Restaurant();
                        foundRestaurant.setId(resultSet.getLong("id"));
                        foundRestaurant.setName(resultSet.getString("name"));
                        foundRestaurant.setOwnerId(resultSet.getLong("ownerId"));
                        foundRestaurant.setEmail(resultSet.getString("email"));
                        foundRestaurant.setTel(resultSet.getString("tel"));
                        foundRestaurant.setAddress(resultSet.getString("address"));
                        foundRestaurant.setActive(resultSet.getBoolean("isActive"));
                        foundRestaurant.setRegisterDate(resultSet.getTimestamp("registerDate"));
                        foundRestaurant.setLogoUrl(resultSet.getString("logoUrl"));
                        foundRestaurant.setMenu(menuItemRepository.getMenuItemsByRestaurantId(foundRestaurant.getId()));
                        foundRestaurant.setOrders(orderRepository.getOrdersByRestaurantId(foundRestaurant.getId()));
                        foundRestaurant.setOpenHours(openHoursRepository.getOpenHoursByRestaurantId(foundRestaurant.getId()));

                        results.add(foundRestaurant);
                    }
                    System.out.println(results);
                }catch (Exception e){
                    throw new CustomException("Companies could not be found due to: "+e.getMessage());
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

    public boolean createRestaurant(Restaurant restaurant){

        Connection conn = null;
        try {

            conn = DriverManager.getConnection(
                    databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");


                String sql = "INSERT INTO restaurant (name, ownerId, address, tel, email, isActive, logoUrl) VALUES(?, ?, ?, ?, ?, ?, ?);";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, restaurant.getName());
                preparedStatement.setLong(2, restaurant.getOwnerId());
                preparedStatement.setString(3, restaurant.getAddress());
                preparedStatement.setString(4, restaurant.getTel());
                preparedStatement.setString(5, restaurant.getEmail());
                preparedStatement.setBoolean(6, restaurant.getActive());
                preparedStatement.setString(7, restaurant.getLogoUrl());

                return preparedStatement.executeUpdate() == 1;

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

    public Restaurant getRestaurantById(Long id){
        Restaurant foundRestaurant = new Restaurant();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");


                String sql = "SELECT*FROM restaurant WHERE id=?;";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setLong(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){

                    foundRestaurant.setId(resultSet.getLong("id"));
                    foundRestaurant.setName(resultSet.getString("name"));
                    foundRestaurant.setOwnerId(resultSet.getLong("ownerId"));
                    foundRestaurant.setEmail(resultSet.getString("email"));
                    foundRestaurant.setTel(resultSet.getString("tel"));
                    foundRestaurant.setAddress(resultSet.getString("address"));
                    foundRestaurant.setActive(resultSet.getBoolean("isActive"));
                    foundRestaurant.setRegisterDate(resultSet.getTimestamp("registerDate"));
                    foundRestaurant.setLogoUrl(resultSet.getString("logoUrl"));
                    foundRestaurant.setMenu(menuItemRepository.getMenuItemsByRestaurantId(foundRestaurant.getId()));
                    foundRestaurant.setOrders(orderRepository.getOrdersByRestaurantId(foundRestaurant.getId()));
                    foundRestaurant.setOpenHours(openHoursRepository.getOpenHoursByRestaurantId(foundRestaurant.getId()));

                }else{
                    throw new CustomException("No restaurant is found with Id "+id);
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
        return foundRestaurant;
    }

    public boolean updateRestaurant(Long id, Restaurant restaurant){

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                Restaurant restaurantToUpdate = getRestaurantById(id);

                if (restaurant.getName()!=null) restaurantToUpdate.setName(restaurant.getName());
                if (restaurant.getOwnerId()!=null) restaurantToUpdate.setOwnerId(restaurant.getOwnerId());
                if (restaurant.getAddress()!=null) restaurantToUpdate.setAddress(restaurant.getAddress());
                if (restaurant.getTel()!=null) restaurantToUpdate.setTel(restaurant.getTel());
                if (restaurant.getEmail()!=null) restaurantToUpdate.setEmail(restaurant.getEmail());
                if (restaurant.getActive()!=null) restaurantToUpdate.setActive(restaurant.getActive());
                if (restaurant.getLogoUrl()!=null) restaurantToUpdate.setLogoUrl(restaurant.getLogoUrl());

                String sql = "UPDATE restaurant SET name=?, ownerId=?, address=?, tel=?, email=?, isActive=?, logoUrl=? WHERE id=?;";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, restaurantToUpdate.getName());
                preparedStatement.setLong(2, restaurantToUpdate.getOwnerId());
                preparedStatement.setString(3, restaurantToUpdate.getAddress());
                preparedStatement.setString(4, restaurantToUpdate.getTel());
                preparedStatement.setString(5, restaurantToUpdate.getEmail());
                preparedStatement.setBoolean(6, restaurantToUpdate.getActive());
                preparedStatement.setString(7, restaurantToUpdate.getLogoUrl());
                preparedStatement.setLong(8, id);

                return preparedStatement.executeUpdate()==1;

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

    public boolean deleteRestaurant(Long id){

        Connection conn = null;
        try {

            conn = DriverManager.getConnection(
                    databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String sql = "DELETE FROM restaurant WHERE id=?;";

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

    public List<Restaurant> getRestaurantsByOwnerId(Long userId) {

        List<Restaurant> results = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                try {
                    String sql = "SELECT*FROM restaurant WHERE ownerId=?;";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setLong(1, userId);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        Restaurant foundRestaurant = new Restaurant();
                        foundRestaurant.setId(resultSet.getLong("id"));
                        foundRestaurant.setName(resultSet.getString("name"));
                        foundRestaurant.setOwnerId(resultSet.getLong("ownerId"));
                        foundRestaurant.setEmail(resultSet.getString("email"));
                        foundRestaurant.setTel(resultSet.getString("tel"));
                        foundRestaurant.setAddress(resultSet.getString("address"));
                        foundRestaurant.setActive(resultSet.getBoolean("isActive"));
                        foundRestaurant.setRegisterDate(resultSet.getTimestamp("registerDate"));
                        foundRestaurant.setLogoUrl(resultSet.getString("logoUrl"));
                        foundRestaurant.setMenu(menuItemRepository.getMenuItemsByRestaurantId(foundRestaurant.getId()));
                        foundRestaurant.setOrders(orderRepository.getOrdersByRestaurantId(foundRestaurant.getId()));
                        foundRestaurant.setOpenHours(openHoursRepository.getOpenHoursByRestaurantId(foundRestaurant.getId()));

                        results.add(foundRestaurant);
                    }
                    System.out.println(results);
                }catch (Exception e){
                    throw new CustomException("Companies could not be found due to: "+e.getMessage());
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
