package com.devjonas.indentity.application.usecase;

import com.devjonas.indentity.application.dto.RegisterEmployeeDTO;
import com.devjonas.indentity.domain.entities.Employee;
import com.devjonas.indentity.domain.entities.User;
import com.devjonas.indentity.domain.factory.FactoryUser;
import com.devjonas.indentity.infra.exception.UserAlreadyExistsException;
import com.devjonas.indentity.repository.CustomerRepository;
import com.devjonas.indentity.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterNewEmployee {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee execute(RegisterEmployeeDTO request) {

        Optional<Employee> emailExists = employeeRepository.findByEmail(request.email());

        if (emailExists.isPresent()) {
            throw new UserAlreadyExistsException("Employee already exists.");
        }

        FactoryUser factory = new FactoryUser();
        Employee employee = factory.createEmployee(request);

        employeeRepository.save(employee);
        return employee;
    }

}
