package com.ofkMasay.Example;

import com.ofkMasay.Entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeExampleRepository employeeExampleRepository;


    public EmployeeController(EmployeeExampleRepository employeeExampleRepository) {
        this.employeeExampleRepository = employeeExampleRepository;
    }

    @PostMapping("/employee")
    public ResponseEntity<Boolean> createUser(@RequestBody Employee employee){
        return new ResponseEntity<>(employeeExampleRepository.createEmployee(employee), HttpStatus.CREATED);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllUsers(){
        return new ResponseEntity<>(employeeExampleRepository.getEmployees(), HttpStatus.OK);
    }

    @GetMapping("/employee/{name}")
    public ResponseEntity<Employee> findByName(@PathVariable String name){
        return new ResponseEntity<>(employeeExampleRepository.findByName(name), HttpStatus.OK);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getUserById(@PathVariable Long id){
        return new ResponseEntity<>(employeeExampleRepository.getEmployeeById(id), HttpStatus.OK);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateUser(@PathVariable Long id, @RequestBody Employee employee){
        if(employeeExampleRepository.updateEmployee(id, employee)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id){
        employeeExampleRepository.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
