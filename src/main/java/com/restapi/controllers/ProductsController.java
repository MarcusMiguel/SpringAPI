package com.restapi.controllers;

import com.restapi.models.Product;
import com.restapi.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductsController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> list(){
        List<Product> products = productService.findAll();
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody Product product){
        productService.insert(product);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Product product){
        productService.update(product);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @RequestMapping("{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        productService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
