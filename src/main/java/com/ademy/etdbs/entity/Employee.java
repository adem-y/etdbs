package com.ademy.etdbs.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE_INFO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @Column(name = "id")
    private String id;

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
    private String hireDate;

    @Column(name = "annualSalary")
    private String annualSalary;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "active")
    private String active;
}
