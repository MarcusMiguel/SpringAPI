package com.restapi.services;

import com.restapi.models.Shop;
import com.restapi.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    public List<Shop> findAll(){
        return shopRepository.findAll( );
    }

    public Shop findById(Integer id) {
        return shopRepository.findByCode( id );
    }

    public void insert(Shop shop){
        shopRepository.save( shop );
        System.out.println("INSERTED: " + shop);
    }

    public void update(Shop shop){
        shopRepository.save( shop );
        System.out.println("ALTERED: " + shop);
    }

    public void delete(Integer id){
        Shop shop = shopRepository.findByCode( id );
        shopRepository.delete( shop );
        System.out.println("DELETED: " + shop);
    }
}