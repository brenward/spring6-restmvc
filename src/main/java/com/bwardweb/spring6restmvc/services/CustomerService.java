package com.bwardweb.spring6restmvc.services;

import com.bwardweb.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    Customer getById(UUID id);
    List<Customer> listCustomers();

    Customer createCustomer(Customer customer);

    void updateById(UUID customerId, Customer customer);

    void deleteById(UUID customerId);

    void patchById(UUID customerId, Customer customer);
}
