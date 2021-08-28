package com.restapi.controllers;

import com.restapi.models.Shop;
import com.restapi.services.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/shops")
//@Api(value="API REST Lojas")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShopController {

    private final ShopService shopService;

    //  @ApiOperation(value="Retorna todas as lojas.")
    @GetMapping
    public Iterable<Shop> list(){
        return shopService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@RequestBody Shop shop){
        shopService.insert(shop);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@RequestBody Shop shop){
        shopService.update(shop);
    }

    @DeleteMapping
    @RequestMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Integer id){
        shopService.delete(id);
    }
}