package com.bwardweb.spring6restmvc.bootstrap;

import com.bwardweb.spring6restmvc.entities.Beer;
import com.bwardweb.spring6restmvc.entities.Customer;
import com.bwardweb.spring6restmvc.mappers.BeerMapper;
import com.bwardweb.spring6restmvc.mappers.CustomerMapper;
import com.bwardweb.spring6restmvc.model.BeerDTO;
import com.bwardweb.spring6restmvc.model.BeerStyle;
import com.bwardweb.spring6restmvc.model.CustomerDTO;
import com.bwardweb.spring6restmvc.repostitories.BeerRepository;
import com.bwardweb.spring6restmvc.repostitories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class BootStrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    public BootStrapData(BeerRepository beerRepository, CustomerRepository customerRepository) {
        this.beerRepository = beerRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
    }

    private void loadBeerData(){
        if(beerRepository.count() == 0){
            Beer beer1 = Beer.builder()
                    .beerName("Coors")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("123456")
                    .price(new BigDecimal("6.10"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Guiness")
                    .beerStyle(BeerStyle.STOUT)
                    .upc("486151")
                    .price(new BigDecimal("6.50"))
                    .quantityOnHand(56)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Harp")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("5673493")
                    .price(new BigDecimal("4.50"))
                    .quantityOnHand(556)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }
    }

    private void loadCustomerData(){
        if(customerRepository.count() == 0){
            Customer cust1 = Customer.builder()
                    .customerName("John")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer cust2 = Customer.builder()
                    .customerName("Dave")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer cust3 = Customer.builder()
                    .customerName("Gary")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            customerRepository.save(cust1);
            customerRepository.save(cust2);
            customerRepository.save(cust3);
        }
    }

}
