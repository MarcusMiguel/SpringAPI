package com.restapi.repositories;

import com.restapi.models.Price;
import com.restapi.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, String>
{
    Price findByProductPrice(Product productPrice);
}
