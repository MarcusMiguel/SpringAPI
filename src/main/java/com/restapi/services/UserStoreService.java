package com.restapi.services;

import com.restapi.models.*;
import com.restapi.repositories.AddressRepository;
import com.restapi.repositories.UserStoreRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStoreService {

    @Autowired
    private UserStoreRepository userStoreRepository;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;

    public Iterable<UserStore> findAll(){
        return userStoreRepository.findAll( );
    }

    public UserStore findByUsernameAndProductIdAndShopId(String username, int productCode, int shopCode){
        return userStoreRepository.findByUsernameAndProductCodeAndShopCode(username, productCode, shopCode);
    }

    public void insert(UserStore userStore){
        userStoreRepository.save( userStore );
        System.out.println("INSERTED: " + userStore);
    }

    public void update(UserStore userStore){
        userStoreRepository.save( userStore );
        System.out.println("ALTERED: " + userStore);
    }

    public void delete(String username, int productId, int shopId){
        UserStore userStore = userStoreRepository.findByUsernameAndProductCodeAndShopCode( username, productId, shopId  );
        userStoreRepository.deleteByUsernameAndProductCodeAndShopCode( username, productId, shopId  );
        System.out.println("DELETED: " + userStore);
    }


}
