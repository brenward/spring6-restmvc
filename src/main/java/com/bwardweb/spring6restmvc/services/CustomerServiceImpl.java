package com.bwardweb.spring6restmvc.services;

import com.bwardweb.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    private Map<UUID, Customer> customerMap;

    public CustomerServiceImpl(){
        this.customerMap = new HashMap<>();

        Customer cust1 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("John")
                .version("1")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer cust2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Dave")
                .version("1")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer cust3 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Gary")
                .version("1")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(cust1.getId(),cust1);
        customerMap.put(cust2.getId(),cust2);
        customerMap.put(cust3.getId(),cust3);
    }

    @Override
    public Customer getById(UUID id) {
        return customerMap.get((id));
    }

    @Override
    public List<Customer> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer savedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .customerName(customer.getCustomerName())
                .version("1")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(savedCustomer.getId(), savedCustomer);

        return savedCustomer;
    }

    @Override
    public void updateById(UUID customerId, Customer customer) {
        Customer existing = customerMap.get(customerId);
        existing.setCustomerName(customer.getCustomerName());
        customerMap.put(existing.getId(),existing);
    }

    @Override
    public void deleteById(UUID customerId) {
        customerMap.remove(customerId);
    }

    @Override
    public void patchById(UUID customerId, Customer customer) {
        Customer existing = customerMap.get(customerId);
        if(StringUtils.hasText(customer.getCustomerName())){
            existing.setCustomerName(customer.getCustomerName());
        }

        customerMap.put(existing.getId(),existing);
    }
}
