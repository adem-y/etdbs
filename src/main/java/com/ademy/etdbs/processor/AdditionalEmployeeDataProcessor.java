package com.ademy.etdbs.processor;

import com.ademy.etdbs.entity.Employee;
import org.springframework.batch.item.ItemProcessor;

public class AdditionalEmployeeDataProcessor implements ItemProcessor<Employee, Employee> {

    @Override
    public Employee process(Employee item) throws Exception {

        String annualSalary = item.getAnnualSalary().replaceAll("[$.]", "");

        if (item.getDepartment().equals("Finance")){
            Integer annualSalaryWithRise = Integer.parseInt(annualSalary) * 2;
            item.setAnnualSalary(String.valueOf(annualSalaryWithRise));
        }
        else {
            item.setAnnualSalary(annualSalary);
        }

        return item;
    }

}
