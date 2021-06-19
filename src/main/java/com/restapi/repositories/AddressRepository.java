package com.restapi.repositories;

import com.restapi.models.Address;
import com.restapi.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, String>
{
    Address findByCode(Integer code);
}

