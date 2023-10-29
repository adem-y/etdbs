package com.ademy.etdbs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Entity
@Table(name = "EMPLOYEE_INFO")
public class Employee {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "jobTitle")
    private String jobTitle;

    @Column(name = "department")
    private String department;

    @Column(name = "businessUnit")
    private String businessUnit;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private String age;

    @Column(name = "hireDate")
    private Date hireDate;

    @Column(name = "annualSalary")
    private String annualSalary;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "active")
    private String active;
}
