package com.bwardweb.spring6restmvc.controllers;

import com.bwardweb.spring6restmvc.model.Beer;
import com.bwardweb.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
    private final BeerService beerService;

    @PostMapping
    public ResponseEntity handlePost(@RequestBody Beer beer) {
        Beer savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers(){
        return beerService.listBeers();
    }

    @RequestMapping(value = "/{beerId}", method = RequestMethod.GET)
    public String getBeerById(@PathVariable("beerId") UUID beerid){
        log.debug("Get beer by id in controller was called for id: " + beerid);

        return beerService.getBeerById(beerid).toString();
    }

    @PutMapping("{beerId}")
    public ResponseEntity updateById(@PathVariable UUID beerId, @RequestBody Beer beer){

        beerService.updateBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{beerId}")
    public ResponseEntity deleteById(@PathVariable UUID beerId){
        beerService.deleteById(beerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{beerId}")
    public ResponseEntity updateBeerPatchById(@PathVariable UUID beerId, @RequestBody Beer beer){

        beerService.patchBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
}
