package com.devjonas.indentity.application.usecase;

import com.devjonas.indentity.domain.entities.Customer;
import com.devjonas.indentity.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListCustomers {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> execute() {
        return customerRepository.findAll();
    }
}
