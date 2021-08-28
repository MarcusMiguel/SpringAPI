package com.restapi.controllers;

import com.restapi.models.Price;
import com.restapi.services.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prices")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PriceController {

    private final PriceService priceService;

    @GetMapping
    public ResponseEntity<List<Price>> list(){
        List<Price> pricesList =  priceService.findAll();
        return new ResponseEntity<List<Price>>(pricesList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody Price price){
        priceService.insert(price);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Price price){
        priceService.update(price);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody int id){
        priceService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}