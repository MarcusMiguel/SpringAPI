package com.restapi.services;

import com.restapi.models.Price;
import com.restapi.models.Product;
import com.restapi.repositories.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

    @Autowired
    private ProductService productService;

    @Autowired
    private PriceRepository priceRepository;

    public Iterable<Price> findAll(){
        return priceRepository.findAll( );
    }

    public Price findById(Integer productCode) {
        Product product = productService.findById( productCode );
        return priceRepository.findByProductPrice (product );
    }

    public void insert(Price price){
        priceRepository.save( price );
        System.out.println("INSERTED: " + price);
    }

    public void update(Price price){
        priceRepository.save( price );
        System.out.println("ALTERED: " + price);
    }
}