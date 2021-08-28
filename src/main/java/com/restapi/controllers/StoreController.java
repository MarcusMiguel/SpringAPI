package com.restapi.controllers;

import com.restapi.models.Store;
import com.restapi.services.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<List<Store>> list(){
        List<Store> stores = storeService.findAll();
        return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody Store store){
        storeService.insert(store);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @RequestMapping("{affiliateCode}/{productCode}/{quantity}")
    public ResponseEntity insert(@PathVariable Integer affiliateCode, @PathVariable Integer productCode, @PathVariable Integer quantity){
        Store store = storeService.findById( affiliateCode, productCode );
        storeService.insert(store);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Store store){
        storeService.update(store);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @RequestMapping("{affiliateCode}/{productCode}")
    public ResponseEntity delete(@PathVariable Integer affiliateCode, @PathVariable Integer productCode){
        storeService.delete(affiliateCode, productCode);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
