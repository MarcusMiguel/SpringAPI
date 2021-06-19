package com.restapi.controllers;

import com.restapi.models.Store;
import com.restapi.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
@PreAuthorize("hasRole('ADMIN')")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping
    public Iterable<Store> list(){
        return storeService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@RequestBody Store store){
        storeService.insert(store);
    }

    @PostMapping
    @RequestMapping("{affiliateCode}/{productCode}/{quantity}")
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@PathVariable Integer affiliateCode, @PathVariable Integer productCode, @PathVariable Integer quantity){
        Store store = storeService.findById( affiliateCode, productCode );
        storeService.insert(store);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@RequestBody Store store){
        storeService.update(store);
    }

    @DeleteMapping
    @RequestMapping("{affiliateCode}/{productCode}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Integer affiliateCode, @PathVariable Integer productCode){
        storeService.delete(affiliateCode, productCode);
    }
}
