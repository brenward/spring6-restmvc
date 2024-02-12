package com.bwardweb.spring6restmvc.repostitories;

import com.bwardweb.spring6restmvc.entities.Beer;
import com.bwardweb.spring6restmvc.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer(){
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("My Beer")
                .beerStyle(BeerStyle.LAGER)
                .upc("random")
                .price(new BigDecimal("10.00"))
                .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

    @Test
    void testSaveBeerNameTooLong(){

        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("My Beer 012345678901234567890123456789012345678901234567890123456789")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("random")
                    .price(new BigDecimal("10.00"))
                    .build());

            beerRepository.flush();

            assertThat(savedBeer).isNotNull();
            assertThat(savedBeer.getId()).isNotNull();
        });

    }
}