package com.ofkMasay.Repository;

import com.ofkMasay.Entity.AccessToken;
import com.ofkMasay.Exception.CustomException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.UUID;

@Repository
public class TokenRepository {
    private static final String databaseURL = "jdbc:mysql://127.0.0.1:3306/masay";

    private static final String databaseUsername = "root";

    private static final String databasePassword = "Student12";


    public AccessToken getTokenByUserId(Long userId){
        AccessToken foundToken = new AccessToken();

        Connection conn = null;
        try {conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {

                String sql = "SELECT*FROM token WHERE userId=?;";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setLong(1, userId);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    foundToken.setId(resultSet.getLong("id"));
                    foundToken.setToken(resultSet.getString("token"));
                    foundToken.setUserId(resultSet.getLong("userId"));
                    foundToken.setCreatedDate(resultSet.getTimestamp("createdDate"));
                    foundToken.setExpiryDate(resultSet.getTimestamp("expiryDate"));
                }else{
                    throw new CustomException("No token with User Id: "+userId);
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
        return foundToken;
    }

    public AccessToken getAccessTokenByToken(String token){
        AccessToken foundToken = new AccessToken();

        Connection conn = null;
        try {conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {

                String sql = "SELECT*FROM token WHERE token=?;";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, token);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    foundToken.setId(resultSet.getLong("id"));
                    foundToken.setToken(resultSet.getString("token"));
                    foundToken.setUserId(resultSet.getLong("userId"));
                    foundToken.setCreatedDate(resultSet.getTimestamp("createdDate"));
                    foundToken.setExpiryDate(resultSet.getTimestamp("expiryDate"));
                }else{
                    throw new CustomException("No token with Token: "+token);
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
        return foundToken;
    }

    public AccessToken createToken(Long userId){
        AccessToken newToken = new AccessToken();
        AccessToken foundToken = new AccessToken();

        newToken.setUserId(userId);
        newToken.setToken(UUID.randomUUID().toString());

        Connection conn = null;
        try {conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {

                String sql = "INSERT INTO token (userId, token, createdDate, expiryDate) VALUES(?, ?, ?, ?);";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setLong(1, newToken.getUserId());
                preparedStatement.setString(2, newToken.getToken());
                preparedStatement.setTimestamp(3, newToken.getCreatedDate());
                preparedStatement.setTimestamp(4, newToken.getCreatedDate());


                int affectedRowCount = preparedStatement.executeUpdate();

                if (affectedRowCount == 1){
                    String sql2 = "SELECT*FROM token WHERE userId=?;";
                    PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
                    preparedStatement2.setLong(1, userId);

                    ResultSet resultSet2 = preparedStatement2.executeQuery();

                    if(resultSet2.next()){
                        foundToken.setId(resultSet2.getLong("id"));
                        foundToken.setToken(resultSet2.getString("token"));
                        foundToken.setUserId(resultSet2.getLong("userId"));
                        foundToken.setCreatedDate(resultSet2.getTimestamp("createdDate"));
                        foundToken.setExpiryDate(resultSet2.getTimestamp("expiryDate"));
                    }else{
                        throw new CustomException("No token with User Id: "+userId);
                    }
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
        return foundToken;
    }

    public void deleteTokenByUserId(Long userId) {
        Connection conn = null;
        try {

            conn = DriverManager.getConnection(
                    databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String sql = "DELETE FROM token WHERE userId=?;";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setLong(1, userId);

                if(preparedStatement.executeUpdate()!=1){
                    throw new CustomException("Deleting token is not successful");
                };

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

    public void deleteAccessTokenByToken(String token) {
        Connection conn = null;
        try {

            conn = DriverManager.getConnection(
                    databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String sql = "DELETE FROM token WHERE token=?;";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, token);

                if(preparedStatement.executeUpdate()!=1){
                    throw new CustomException("Deleting token is not successful");
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
}
