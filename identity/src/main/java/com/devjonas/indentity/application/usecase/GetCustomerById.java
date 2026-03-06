package com.devjonas.indentity.application.usecase;

import com.devjonas.indentity.domain.entities.Customer;
import com.devjonas.indentity.domain.entities.User;
import com.devjonas.indentity.infra.exception.UserNotFoundException;
import com.devjonas.indentity.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetCustomerById {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer execute(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}
