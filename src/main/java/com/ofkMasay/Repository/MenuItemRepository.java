package com.ofkMasay.Repository;

import com.ofkMasay.Entity.MenuItem;
import com.ofkMasay.Exception.CustomException;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MenuItemRepository {

    private static final String databaseURL = "jdbc:mysql://127.0.0.1:3306/masay";

    private static final String databaseUsername = "root";

    private static final String databasePassword = "Student12";

    public List<MenuItem> getMenuItems(){

        List<MenuItem> results = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                try {
                    String sql = "SELECT*FROM menu_item;";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        MenuItem menuItem = new MenuItem();
                        menuItem.setId(resultSet.getLong("id"));
                        menuItem.setName(resultSet.getString("name"));
                        menuItem.setDescription(resultSet.getString("description"));
                        menuItem.setPrice(resultSet.getBigDecimal("price"));
                        menuItem.setRestaurantId(resultSet.getLong("restaurantId"));
                        menuItem.setPhotoUrl(resultSet.getString("photoUrl"));
                        menuItem.setSize(resultSet.getString("size"));

                        results.add(menuItem);
                    }
                    System.out.println(results);
                }catch (Exception e){
                    throw new CustomException("Menu items could not be found due to: "+e.getMessage());
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

    public boolean createMenuItem(MenuItem menuItem){

        Connection conn = null;
        try {

            conn = DriverManager.getConnection(
                    databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String sql = "INSERT INTO menu_item (name, description, price, restaurantId, photoUrl, size) VALUES(?, ?, ?, ?, ?, ?);";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, menuItem.getName());
                preparedStatement.setString(2, menuItem.getDescription());
                preparedStatement.setBigDecimal(3, menuItem.getPrice());
                preparedStatement.setLong(4, menuItem.getRestaurantId());
                preparedStatement.setString(5, menuItem.getPhotoUrl());
                preparedStatement.setString(6, menuItem.getSize());

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

    public MenuItem getMenuItemById(Long id){
        MenuItem menuItem = new MenuItem();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");


                String sql = "SELECT*FROM menu_item WHERE id=?;";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setLong(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    menuItem.setId(resultSet.getLong("id"));
                    menuItem.setName(resultSet.getString("name"));
                    menuItem.setDescription(resultSet.getString("description"));
                    menuItem.setPrice(resultSet.getBigDecimal("price"));
                    menuItem.setRestaurantId(resultSet.getLong("restaurantId"));
                    menuItem.setPhotoUrl(resultSet.getString("photoUrl"));
                    menuItem.setSize(resultSet.getString("size"));
                }else{
                    throw new CustomException("No menu item is found with Id "+id);
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
        return menuItem;
    }

    public boolean updateMenuItem(Long id, MenuItem item){

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                MenuItem itemToUpdate = getMenuItemById(id);

                if (item.getName()!=null) itemToUpdate.setName(item.getName());
                if (item.getPrice()!=null) itemToUpdate.setPrice(item.getPrice());
                if (item.getDescription()!=null) itemToUpdate.setDescription(item.getDescription());
                if (item.getPhotoUrl()!=null) itemToUpdate.setPhotoUrl(item.getPhotoUrl());
                if (item.getSize()!=null) itemToUpdate.setSize(item.getSize());


                String sql = "UPDATE menu_item SET name=?, price=?, description=?, photoUrl=?, size=? WHERE id=?;";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, itemToUpdate.getName());
                preparedStatement.setBigDecimal(2, itemToUpdate.getPrice());
                preparedStatement.setString(3, itemToUpdate.getDescription());
                preparedStatement.setString(4, itemToUpdate.getPhotoUrl());
                preparedStatement.setString(5, itemToUpdate.getSize());

                preparedStatement.setLong(6, id);

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

    public boolean deleteMenuItem(Long id){

        Connection conn = null;
        try {

            conn = DriverManager.getConnection(
                    databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String sql = "DELETE FROM menu_item WHERE id=?;";

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

    public List<MenuItem> getMenuItemsByRestaurantId(Long id){

        List<MenuItem> results = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                try {
                    String sql = "SELECT*FROM menu_item WHERE restaurantId=?;";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setLong(1, id);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        MenuItem menuItem = new MenuItem();
                        menuItem.setId(resultSet.getLong("id"));
                        menuItem.setName(resultSet.getString("name"));
                        menuItem.setDescription(resultSet.getString("description"));
                        menuItem.setPrice(resultSet.getBigDecimal("price"));
                        menuItem.setRestaurantId(resultSet.getLong("restaurantId"));
                        menuItem.setPhotoUrl(resultSet.getString("photoUrl"));
                        menuItem.setSize(resultSet.getString("size"));

                        results.add(menuItem);
                    }
                    System.out.println(results);
                }catch (Exception e){
                    throw new CustomException("Menu items could not be found due to: "+e.getMessage());
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
