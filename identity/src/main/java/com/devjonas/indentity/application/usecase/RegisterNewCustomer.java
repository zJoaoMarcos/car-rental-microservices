package com.devjonas.indentity.application.usecase;

import com.devjonas.indentity.application.dto.RegisterCustomerDTO;
import com.devjonas.indentity.domain.entities.Customer;
import com.devjonas.indentity.domain.entities.User;
import com.devjonas.indentity.domain.factory.FactoryUser;
import com.devjonas.indentity.infra.exception.UserAlreadyExistsException;
import com.devjonas.indentity.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterNewCustomer {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer execute(RegisterCustomerDTO request) {
        Optional<Customer> emailExists = customerRepository.findByEmail(request.email());

        if (emailExists.isPresent()) {
            throw new UserAlreadyExistsException("Employee already exists.");
        }

        FactoryUser factory = new FactoryUser();
        Customer customer = factory.createCustomer(request);

        customerRepository.save(customer);
        return customer;
    }
}
