package com.bwardweb.spring6restmvc.mappers;

import com.bwardweb.spring6restmvc.entities.Beer;
import com.bwardweb.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);
}
