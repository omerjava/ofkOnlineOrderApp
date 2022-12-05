package com.ofkMasay.Example;

import com.ofkMasay.Example.Employee;
import java.util.List;

public interface EmployeeExampleRepositoryI {
    List<Employee> getEmployees();
    boolean createEmployee(Employee employee);
    Employee getEmployeeById(Long employeeId);
    boolean updateEmployee(Long id, Employee employee);
    boolean deleteEmployee(Long id);
    Employee findByName(String name);

}
