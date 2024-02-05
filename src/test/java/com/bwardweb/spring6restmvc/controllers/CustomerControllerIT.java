package com.bwardweb.spring6restmvc.controllers;

import com.bwardweb.spring6restmvc.entities.Customer;
import com.bwardweb.spring6restmvc.exception.NotFoundException;
import com.bwardweb.spring6restmvc.mappers.CustomerMapper;
import com.bwardweb.spring6restmvc.model.BeerDTO;
import com.bwardweb.spring6restmvc.model.CustomerDTO;
import com.bwardweb.spring6restmvc.repostitories.BeerRepository;
import com.bwardweb.spring6restmvc.repostitories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {
    @Autowired
    CustomerController controller;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testListCustomers(){
        List<CustomerDTO> dtos = controller.getCustomers();

        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList(){
        customerRepository.deleteAll();;
        List<CustomerDTO> dtos = controller.getCustomers();

        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void getCustomerById(){
        Customer cust = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = controller.getCustomeryId(cust.getId());

        assertThat(customerDTO).isNotNull();
    }

    @Test
    void getCustomerNotFound(){
        assertThrows(NotFoundException.class,() -> {
            controller.getCustomeryId(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewCustomer(){
        CustomerDTO customerDTO = CustomerDTO.builder().customerName("New Customer").build();
        ResponseEntity responseEntity = controller.handlePost(customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedCustomerId = UUID.fromString(locationUUID[4]);

        Customer customer = customerRepository.findById(savedCustomerId).get();
        assertThat(customer).isNotNull();

    }

    @Test
    void testUpdateExistingCustomer(){
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);
        final String updatedName = "UPDATED";
        customerDTO.setCustomerName(updatedName);

        ResponseEntity responseEntity = controller.updateById(customer.getId(),customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getCustomerName()).isEqualTo(updatedName);
    }

    @Test
    void testUpdateCustomerNotFound(){
        assertThrows(NotFoundException.class, () -> {
           controller.updateById(UUID.randomUUID(),CustomerDTO.builder().build());
        });
    }

}