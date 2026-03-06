package com.devjonas.indentity.application.usecase;

import com.devjonas.indentity.domain.entities.Customer;
import com.devjonas.indentity.domain.entities.Employee;
import com.devjonas.indentity.infra.exception.UserNotFoundException;
import com.devjonas.indentity.repository.CustomerRepository;
import com.devjonas.indentity.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetEmployeeById {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee execute(UUID id) {
        return employeeRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}
