package com.devjonas.indentity.application.usecase;

import com.devjonas.indentity.domain.entities.Employee;
import com.devjonas.indentity.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListEmployees {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> execute() {
        return employeeRepository.findAll();
    }
}
