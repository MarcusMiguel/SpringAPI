package com.restapi.services;

import com.restapi.models.Product;
import com.restapi.models.Shop;
import com.restapi.models.Store;
import com.restapi.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StoreRepository storeRepository;

    public Iterable<Store> findAll(){
        return storeRepository.findAll( );
    }

    public Store findById(Integer affiliateCode, Integer productCode) {
        Shop shop = shopService.findById( affiliateCode );
        Product product = productService.findById( productCode );
        return storeRepository.findByProductCodeAndAffiliateCode( product, shop );
    }

    public void insert(Store store){
        storeRepository.save( store );
        System.out.println("INSERTED: " + store);
    }

    public void update(Store store){
        storeRepository.save( store );
        System.out.println("ALTERED: " + store);
    }

    public void delete(Integer affiliateCode, Integer productCode){
        Store store = findById( affiliateCode, productCode );
        storeRepository.delete( store );
        System.out.println("DELETED: " + store);
    }

    public void removeProducts(int quantity, Store product){
        int newQuantity = product.getQuantity() - quantity;
        if (newQuantity > 0) {
            product.setQuantity(newQuantity);
            update(product);
        }
        else {
            delete(product.getAffiliateCode().getCode(), product.getProductCode().getCode());
        }
    }

}
