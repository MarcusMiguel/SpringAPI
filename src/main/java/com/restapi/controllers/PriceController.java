package com.restapi.controllers;

import com.restapi.models.Price;
import com.restapi.services.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prices")
@PreAuthorize("hasRole('ADMIN')")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @GetMapping
    public Iterable<Price> list(){
        return priceService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@RequestBody Price price){
        priceService.insert(price);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@RequestBody Price price){
        priceService.update(price);
    }

}