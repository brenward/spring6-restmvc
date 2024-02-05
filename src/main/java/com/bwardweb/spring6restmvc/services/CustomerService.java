package com.bwardweb.spring6restmvc.services;

import com.bwardweb.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    Optional<CustomerDTO> getById(UUID id);
    List<CustomerDTO> listCustomers();

    CustomerDTO createCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateById(UUID customerId, CustomerDTO customer);

    void deleteById(UUID customerId);

    void patchById(UUID customerId, CustomerDTO customer);
}
