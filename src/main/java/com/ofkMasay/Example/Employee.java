package com.ofkMasay.Example;

import java.sql.Timestamp;

public class Employee {

    private Long id;
    private String name;
    private Integer age;
    private String title;
    private Double salary;
    private Timestamp dateOfRegistry;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Timestamp getDateOfRegistry() {
        return dateOfRegistry;
    }

    public void setDateOfRegistry(Timestamp dateOfRegistry) {
        this.dateOfRegistry = dateOfRegistry;
    }
}
