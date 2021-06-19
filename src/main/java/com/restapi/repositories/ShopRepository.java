package com.restapi.repositories;

import com.restapi.models.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop,String> {
    Shop findByCode(Integer code);
}
