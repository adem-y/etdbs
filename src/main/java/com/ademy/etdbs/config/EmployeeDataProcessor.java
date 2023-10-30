package com.ademy.etdbs.config;

import com.ademy.etdbs.entity.Employee;
import org.springframework.batch.item.ItemProcessor;

public class EmployeeDataProcessor implements ItemProcessor<Employee, Employee> {
    @Override
    public Employee process(Employee item) throws Exception {
        // TODO: add logic
        return item;
    }
}
