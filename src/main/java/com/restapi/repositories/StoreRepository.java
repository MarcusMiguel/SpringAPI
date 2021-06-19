package com.restapi.repositories;

import com.restapi.models.Product;
import com.restapi.models.Shop;
import com.restapi.models.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
    Store findByProductCodeAndAffiliateCode(Product ProductCode,Shop affiliateCode);
}
