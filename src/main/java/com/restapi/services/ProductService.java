package com.restapi.services;

import com.restapi.repositories.ProductRepository;
import com.restapi.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(Integer id) {
        return productRepository.findByCode( id );
    }

    public void insert( Product product){
        productRepository.save(product);
    }

    public void update( Product product){
        productRepository.save(product);
    }

    public void delete(Integer id){
        Product product = productRepository.findByCode( id );
        productRepository.delete( product );
    }
}
