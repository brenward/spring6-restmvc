package com.bwardweb.spring6restmvc.repostitories;

import com.bwardweb.spring6restmvc.bootstrap.BootStrapData;
import com.bwardweb.spring6restmvc.entities.Beer;
import com.bwardweb.spring6restmvc.model.BeerStyle;
import com.bwardweb.spring6restmvc.services.BeerCsvService;
import com.bwardweb.spring6restmvc.services.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BootStrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testListBeerByName(){
        List<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%");

        assertThat(list.size()).isEqualTo(336);
    }

    @Test
    void testListBeerByNameAndStyle(){
        List<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%IPA%", BeerStyle.IPA);

        assertThat(list.size()).isEqualTo(310);
    }

    @Test
    void testListBeerByStyle(){
        List<Beer> list = beerRepository.findAllByBeerStyle(BeerStyle.IPA);

        assertThat(list.size()).isEqualTo(547);
    }

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