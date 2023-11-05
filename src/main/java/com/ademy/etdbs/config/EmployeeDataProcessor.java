package com.ademy.etdbs.config;

import com.ademy.etdbs.entity.Employee;
import org.springframework.batch.item.ItemProcessor;

public class EmployeeDataProcessor implements ItemProcessor<Employee, Employee> {
    @Override
    public Employee process(Employee item) throws Exception {
        if (item.getBusinessUnit().equals("Manufacturing")
                || item.getBusinessUnit().equals("Corporate")){
            item.setActive("A");
        }
        return item;
    }
}
