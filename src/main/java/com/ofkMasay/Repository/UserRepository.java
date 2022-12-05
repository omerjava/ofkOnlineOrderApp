package com.ofkMasay.Repository;

import com.ofkMasay.Entity.Type;
import com.ofkMasay.Entity.User;
import com.ofkMasay.Exception.CustomException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository implements UserRepositoryI {

    private static final String databaseURL = "jdbc:mysql://127.0.0.1:3306/masay";

    private static final String databaseUsername = "root";

    private static final String databasePassword = "Student12";

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<User> getUsers(){

        List<User> results = new ArrayList<>();

        Connection conn = null;
        try {conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                try {
                    String sql = "SELECT*FROM user;";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        User foundUser = new User();
                        foundUser.setId(resultSet.getLong("id"));
                        foundUser.setUsername(resultSet.getString("username"));
                        foundUser.setPassword(resultSet.getString("password"));
                        foundUser.setEmail(resultSet.getString("email"));
                        foundUser.setName(resultSet.getString("name"));
                        foundUser.setTel(resultSet.getString("tel"));
                        foundUser.setAddress(resultSet.getString("address"));
                        foundUser.setType(Type.valueOf(resultSet.getString("type")));
                        foundUser.setCreatedDate(resultSet.getTimestamp("createdDate"));

                        results.add(foundUser);
                    }
                    System.out.println(results);
                }catch (Exception e){
                    throw new CustomException("Users could not be found due to: "+e.getMessage());
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
    @Override
    public boolean createUser(User user){

        Connection conn = null;
        try {

            conn = DriverManager.getConnection(
                    databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");


                String sql = "INSERT INTO user (username, email, password, name, tel, address, type) VALUES(?, ?, ?, ?, ?, ?, ?);";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, passwordEncoder.encode(user.getPassword()));
                preparedStatement.setString(4, user.getName());
                preparedStatement.setString(5, user.getTel());
                preparedStatement.setString(6, user.getAddress());
                preparedStatement.setString(7, user.getType().toString());

                if(preparedStatement.executeUpdate()==1){
                    return true;
                }else {
                    throw new CustomException("Registration is not successful");
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
    @Override
    public User getUserById(Long userId){
        User foundUser = new User();

        Connection conn = null;
        try {

            conn = DriverManager.getConnection(
                    databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");


                String sql = "SELECT*FROM user WHERE id=?;";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setLong(1, userId);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    foundUser.setId(resultSet.getLong("id"));
                    foundUser.setUsername(resultSet.getString("username"));
                    foundUser.setPassword(resultSet.getString("password"));
                    foundUser.setEmail(resultSet.getString("email"));
                    foundUser.setName(resultSet.getString("name"));
                    foundUser.setTel(resultSet.getString("tel"));
                    foundUser.setAddress(resultSet.getString("address"));
                    foundUser.setType(Type.valueOf(resultSet.getString("type")));
                    foundUser.setCreatedDate(resultSet.getTimestamp("createdDate"));

                }else{
                    throw new CustomException("No user with Id "+userId);
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
        return foundUser;
    }
    @Override
    public boolean updateUser(Long id, User user){

        Connection conn = null;
        try {

            conn = DriverManager.getConnection(
                    databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                User userToUpdate = getUserById(id);

                if (user.getUsername()!=null) userToUpdate.setUsername(user.getUsername());
                if (user.getEmail()!=null) userToUpdate.setEmail(user.getEmail());
                if (user.getPassword()!=null) userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
                if (user.getName()!=null) userToUpdate.setName(user.getName());
                if (user.getTel()!=null) userToUpdate.setTel(user.getTel());
                if (user.getAddress()!=null) userToUpdate.setAddress(user.getAddress());

                String sql = "UPDATE user SET username=?, email=?, password=?, name=?, tel=?, address=?, type=? WHERE id=?;";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, userToUpdate.getUsername());
                preparedStatement.setString(2, userToUpdate.getEmail());
                preparedStatement.setString(3, userToUpdate.getPassword());
                preparedStatement.setString(4, userToUpdate.getName());
                preparedStatement.setString(5, userToUpdate.getTel());
                preparedStatement.setString(6, userToUpdate.getAddress());
                preparedStatement.setString(7, userToUpdate.getType().toString());
                preparedStatement.setLong(8, id);


                if(preparedStatement.executeUpdate()==1){
                    return true;
                }else {
                    throw new CustomException("Update is not successful");
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
    @Override
    public boolean deleteUser(Long id){

        Connection conn = null;
        try {

            conn = DriverManager.getConnection(
                    databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String sql = "DELETE FROM user WHERE id=?;";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setLong(1, id);

                if(preparedStatement.executeUpdate()==1){
                    return true;
                }else {
                    throw new CustomException("Delete is not successful");
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
    @Override
    public User findByUsername(String username) {
        User foundUser = new User();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String sql = "SELECT*FROM user WHERE username=?;";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, username);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    foundUser.setId(resultSet.getLong("id"));
                    foundUser.setUsername(resultSet.getString("username"));
                    foundUser.setPassword(resultSet.getString("password"));
                    foundUser.setEmail(resultSet.getString("email"));
                    foundUser.setName(resultSet.getString("name"));
                    foundUser.setTel(resultSet.getString("tel"));
                    foundUser.setAddress(resultSet.getString("address"));
                    foundUser.setType(Type.valueOf(resultSet.getString("type")));
                    foundUser.setCreatedDate(resultSet.getTimestamp("createdDate"));

                }else{
                    throw new CustomException("No user with Username: "+username);
                }

            } else {
                System.out.println("Failed to make connection!");
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
        return foundUser;
    }

}
