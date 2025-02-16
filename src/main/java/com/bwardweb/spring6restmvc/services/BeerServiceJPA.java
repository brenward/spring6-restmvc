package com.bwardweb.spring6restmvc.services;

import com.bwardweb.spring6restmvc.entities.Beer;
import com.bwardweb.spring6restmvc.mappers.BeerMapper;
import com.bwardweb.spring6restmvc.model.BeerDTO;
import com.bwardweb.spring6restmvc.model.BeerStyle;
import com.bwardweb.spring6restmvc.repostitories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService{

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory) {
        List<Beer> beerList;

        if(StringUtils.hasText(beerName) && beerStyle == null){
            beerList = listBeersByName(beerName);
        }else if(!StringUtils.hasText(beerName) && beerStyle != null) {
            beerList = listBeersByStyle(beerStyle);
        }else if(StringUtils.hasText(beerName) && beerStyle != null) {
            beerList = listBeersByNameAndStyle(beerName, beerStyle);
        }else {
            beerList = beerRepository.findAll();
        }

        if(showInventory != null && !showInventory){
            beerList.forEach(beer -> {beer.setQuantityOnHand(null);});
        }

        return beerList.stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }

    List<Beer> listBeersByNameAndStyle(String beerName, BeerStyle beerStyle) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%" + beerName + "%",beerStyle);
    }

    List<Beer> listBeersByName(String beerName){
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%");
    }

    List<Beer> listBeersByStyle(BeerStyle beerStyle){
        return beerRepository.findAllByBeerStyle(beerStyle);
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id).orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return beerMapper.beerToBeerDto(
                beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
                foundBeer.setBeerName(beer.getBeerName());
                foundBeer.setBeerStyle(beer.getBeerStyle());
                foundBeer.setPrice(beer.getPrice());
                foundBeer.setUpc(beer.getUpc());
                atomicReference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(foundBeer))));
            }, () -> {
                atomicReference.set(Optional.empty());
            });
        return atomicReference.get();
    }

    @Override
    public Boolean deleteById(UUID beerId) {
        if(beerRepository.existsById(beerId)){
            beerRepository.deleteById(beerId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            if(StringUtils.hasText(beer.getBeerName())) {
                foundBeer.setBeerName(beer.getBeerName());
            }
            if(beer.getBeerStyle() != null) {
                foundBeer.setBeerStyle(beer.getBeerStyle());
            }
            if(beer.getPrice() != null) {
                foundBeer.setPrice(beer.getPrice());
            }
            if(StringUtils.hasText(beer.getUpc())) {
                foundBeer.setUpc(beer.getUpc());
            }
            atomicReference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }
}
