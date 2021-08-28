package com.restapi.services;

import com.restapi.repositories.StoreRepository;
import com.restapi.models.Product;
import com.restapi.models.Shop;
import com.restapi.models.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StoreService {

    private final ShopService shopService;
    private final ProductService productService;
    private final StoreRepository storeRepository;

    public List<Store> findAll(){
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
