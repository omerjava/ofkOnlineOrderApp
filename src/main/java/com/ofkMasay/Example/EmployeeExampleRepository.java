package com.ofkMasay.Example;

import com.ofkMasay.Exception.CustomException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeExampleRepository implements EmployeeExampleRepositoryI {

    private static final String databaseURL = "jdbc:mysql://127.0.0.1:3306/dbname";

    private static final String databaseUsername = "root";

    private static final String databasePassword = "yourPassword";


    @Override
    public List<Employee> getEmployees(){

        List<Employee> results = new ArrayList<>();

        Connection conn = null;
        try {conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                try {
                    String sql = "SELECT*FROM employee;";     // employee -> database table name
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        Employee foundEmployee = new Employee();
                        foundEmployee.setId(resultSet.getLong("id"));
                        foundEmployee.setName(resultSet.getString("name"));
                        foundEmployee.setAge(resultSet.getInt("age"));
                        foundEmployee.setTitle(resultSet.getString("title"));
                        foundEmployee.setSalary(resultSet.getDouble("salary"));
                        foundEmployee.setDateOfRegistry(resultSet.getTimestamp("dateOfRegistry"));

                        results.add(foundEmployee);
                    }
                    System.out.println(results);
                }catch (Exception e){
                    throw new CustomException("Employees could not be found due to: "+e.getMessage());
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
    public boolean createEmployee(Employee employee){

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");


                String sql = "INSERT INTO employee (name, age, title, salary) VALUES(?, ?, ?, ?);";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, employee.getName());
                preparedStatement.setInt(2, employee.getAge());
                preparedStatement.setString(3, employee.getTitle());
                preparedStatement.setDouble(4, employee.getSalary());

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
    public Employee getEmployeeById(Long employeeId){
        Employee foundEmployee = new Employee();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String sql = "SELECT*FROM employee WHERE id=?;";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setLong(1, employeeId);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    foundEmployee.setId(resultSet.getLong("id"));
                    foundEmployee.setName(resultSet.getString("name"));
                    foundEmployee.setAge(resultSet.getInt("age"));
                    foundEmployee.setTitle(resultSet.getString("title"));
                    foundEmployee.setSalary(resultSet.getDouble("salary"));
                    foundEmployee.setDateOfRegistry(resultSet.getTimestamp("dateOfRegistry"));

                }else{
                    throw new CustomException("No employee with Id "+employeeId);
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
        return foundEmployee;
    }
    @Override
    public boolean updateEmployee(Long id, Employee employee){

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                Employee employeeToUpdate = getEmployeeById(id);  // get existing employee from database to edit and update in database

                if (employee.getName()!=null) employeeToUpdate.setName(employee.getName());
                if (employee.getAge()!=null) employeeToUpdate.setAge(employee.getAge());
                if (employee.getTitle()!=null) employeeToUpdate.setTitle(employee.getTitle());
                if (employee.getSalary()!=null) employeeToUpdate.setSalary(employee.getSalary());

                String sql = "UPDATE employee SET name=?, age=?, title=?, salary=? WHERE id=?;";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, employeeToUpdate.getName());
                preparedStatement.setInt(2, employeeToUpdate.getAge());
                preparedStatement.setString(3, employeeToUpdate.getTitle());
                preparedStatement.setDouble(4, employeeToUpdate.getSalary());
                preparedStatement.setLong(5, id);

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

    @Override
    public boolean deleteEmployee(Long id){

        Connection conn = null;
        try {

            conn = DriverManager.getConnection(
                    databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String sql = "DELETE FROM employee WHERE id=?;";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setLong(1, id);

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
    @Override
    public Employee findByName(String name) {
        Employee foundEmployee = new Employee();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
            if (conn != null) {
                System.out.println("Connected to the database!");

                String sql = "SELECT*FROM employee WHERE name=?;";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, name);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    foundEmployee.setId(resultSet.getLong("id"));
                    foundEmployee.setName(resultSet.getString("name"));
                    foundEmployee.setAge(resultSet.getInt("age"));
                    foundEmployee.setTitle(resultSet.getString("title"));
                    foundEmployee.setSalary(resultSet.getDouble("salary"));
                    foundEmployee.setDateOfRegistry(resultSet.getTimestamp("dateOfRegistry"));
                }else{
                    throw new CustomException("No employee with Name: "+name);
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
        return foundEmployee;
    }
}
