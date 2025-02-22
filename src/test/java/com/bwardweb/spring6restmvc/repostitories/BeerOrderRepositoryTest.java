package com.bwardweb.spring6restmvc.repostitories;

import com.bwardweb.spring6restmvc.entities.Beer;
import com.bwardweb.spring6restmvc.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerOrderRepositoryTest {
    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    Customer customer;
    Beer beer;

    @BeforeEach
    void setup(){
        customer = customerRepository.findAll().get(0);
        beer = beerRepository.findAll().get(0);
    }

    @Test
    void testBeerOrders(){
        System.out.println(beerOrderRepository.count());
        System.out.println(customerRepository.count());
        System.out.println(beerRepository.count());
        System.out.println(customer.getCustomerName());
        System.out.println(beer.getBeerName());
    }
}