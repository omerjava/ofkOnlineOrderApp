package com.ofkMasay.Repository;

import com.ofkMasay.Entity.OpenHours;
import com.ofkMasay.Exception.CustomException;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OpenHoursRepository {

    private static final String databaseURL = "jdbc:mysql://127.0.0.1:3306/masay";

    private static final String databaseUsername = "root";

    private static final String databasePassword = "Student12";


    public List<OpenHours> getOpenHours(){

        List<OpenHours> results = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                try {
                    String sql = "SELECT*FROM open_hours;";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        OpenHours openHours = new OpenHours();
                        openHours.setId(resultSet.getLong("id"));
                        openHours.setDay(resultSet.getString("day"));
                        openHours.setOpenTime(resultSet.getString("openTime"));
                        openHours.setCloseTime(resultSet.getString("closeTime"));
                        openHours.setRestaurantId(resultSet.getLong("restaurantId"));

                        results.add(openHours);
                    }
                    System.out.println(results);
                }catch (Exception e){
                    throw new CustomException("Open hours could not be found due to: "+e.getMessage());
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

    public boolean createOpenHours(OpenHours openHours){

        Connection conn = null;
        try {

            conn = DriverManager.getConnection(
                    databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String sql = "INSERT INTO open_hours (day, openTime, closeTime, restaurantId) VALUES(?, ?, ?, ?);";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, openHours.getDay());
                preparedStatement.setString(2, openHours.getOpenTime());
                preparedStatement.setString(3, openHours.getCloseTime());
                preparedStatement.setLong(4, openHours.getRestaurantId());

                if(preparedStatement.executeUpdate()==1){
                    return true;
                }else {
                    throw new CustomException("Open hours registration is not successful");
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

    public OpenHours getOpenHoursById(Long id){
        OpenHours openHours = new OpenHours();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");


                String sql = "SELECT*FROM open_hours WHERE id=?;";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setLong(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    openHours.setId(resultSet.getLong("id"));
                    openHours.setDay(resultSet.getString("day"));
                    openHours.setOpenTime(resultSet.getString("openTime"));
                    openHours.setCloseTime(resultSet.getString("closeTime"));
                    openHours.setRestaurantId(resultSet.getLong("restaurantId"));
                }else{
                    throw new CustomException("No Open hours is found with Id: "+id);
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
        return openHours;
    }

    public boolean updateOpenHours(Long id, OpenHours openHours){

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                OpenHours openHoursToUpdate = getOpenHoursById(id);

                if (openHours.getDay()!=null) openHoursToUpdate.setDay(openHours.getDay());
                if (openHours.getOpenTime()!=null) openHoursToUpdate.setOpenTime(openHours.getOpenTime());
                if (openHours.getCloseTime()!=null) openHoursToUpdate.setCloseTime(openHours.getCloseTime());
                if (openHours.getRestaurantId()!=null) openHoursToUpdate.setRestaurantId(openHours.getRestaurantId());


                String sql = "UPDATE open_hours SET day=?, openTime=?, closeTime=?, restaurantId=? WHERE id=?;";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, openHoursToUpdate.getDay());
                preparedStatement.setString(2, openHoursToUpdate.getOpenTime());
                preparedStatement.setString(3, openHoursToUpdate.getCloseTime());
                preparedStatement.setLong(4, openHoursToUpdate.getRestaurantId());

                preparedStatement.setLong(5, id);

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

    public boolean deleteOpenHours(Long id){

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String sql = "DELETE FROM open_hours WHERE id=?;";

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

    public List<OpenHours> getOpenHoursByRestaurantId(Long id){

        List<OpenHours> results = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                try {
                    String sql = "SELECT*FROM open_hours WHERE restaurantId=?;";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setLong(1, id);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        OpenHours openHours = new OpenHours();
                        openHours.setId(resultSet.getLong("id"));
                        openHours.setDay(resultSet.getString("day"));
                        openHours.setOpenTime(resultSet.getString("openTime"));
                        openHours.setCloseTime(resultSet.getString("closeTime"));
                        openHours.setRestaurantId(resultSet.getLong("restaurantId"));

                        results.add(openHours);
                    }
                    System.out.println(results);
                }catch (Exception e){
                    throw new CustomException("Open hours could not be found due to: "+e.getMessage());
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
