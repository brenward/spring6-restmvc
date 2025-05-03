package com.bwardweb.spring6restmvc.mappers;

import com.bwardweb.spring6restmvc.entities.Customer;
import com.bwardweb.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);
}
