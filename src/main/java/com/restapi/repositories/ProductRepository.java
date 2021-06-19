package com.restapi.repositories;

import com.restapi.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductRepository extends JpaRepository<Product, String>
{
    Product findByCode(Integer code);
}