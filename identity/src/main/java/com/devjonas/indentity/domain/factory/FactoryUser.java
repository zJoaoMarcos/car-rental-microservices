package com.devjonas.indentity.domain.factory;

import com.devjonas.indentity.application.dto.RegisterCustomerDTO;
import com.devjonas.indentity.application.dto.RegisterEmployeeDTO;
import com.devjonas.indentity.domain.entities.Customer;
import com.devjonas.indentity.domain.entities.Employee;
import com.devjonas.indentity.domain.enums.CustomerStatus;
import com.devjonas.indentity.domain.enums.EmployeeStatus;

import java.time.LocalDate;

public class FactoryUser {

    public Customer createCustomer(RegisterCustomerDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setCpf(dto.cpf());
        customer.setBirthDate(dto.birthDate());
        customer.setAddress(dto.address());
        customer.setDriveLicense(dto.driveLicense());
        customer.setStatus(CustomerStatus.ACTIVE);
        return customer;
    }

    public Employee createEmployee(RegisterEmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setName(dto.name());
        employee.setEmail(dto.email());
        employee.setCpf(dto.cpf());
        employee.setPosition(dto.position());
        employee.setHireDate(LocalDate.now());
        employee.setStatus(EmployeeStatus.ACTIVE);
        return employee;
    }
}

