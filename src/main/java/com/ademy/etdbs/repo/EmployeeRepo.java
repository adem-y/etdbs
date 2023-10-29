package com.ademy.etdbs.repo;

import com.ademy.etdbs.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
}
