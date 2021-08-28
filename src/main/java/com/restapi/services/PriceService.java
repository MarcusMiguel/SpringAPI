package com.restapi.services;

import com.restapi.repositories.PriceRepository;
import com.restapi.models.Price;
import com.restapi.models.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PriceService {

    private final ProductService productService;
    private final PriceRepository priceRepository;

    public List<Price> findAll(){
        return priceRepository.findAll( );
    }

    public Price findById(Integer productCode) {
        Product product = productService.findById(productCode);
        return priceRepository.findByProductPrice (product);
    }

    public void insert(Price price){
        priceRepository.save( price );
        log.info("INSERTED: " + price);
    }

    public void update(Price price){
        priceRepository.save( price );
        log.info("ALTERED: " + price);
    }

    public void delete(int id){
        Price price = findById(id);
        priceRepository.delete(price);
        log.info("DELETED: " + price);
    }
}