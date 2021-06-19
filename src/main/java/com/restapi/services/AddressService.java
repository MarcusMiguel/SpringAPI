package com.restapi.services;

import com.restapi.models.Address;
import com.restapi.models.Shop;
import com.restapi.repositories.AddressRepository;
import com.restapi.repositories.CartRepository;
import com.restapi.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
        @Autowired
        private AddressRepository addressRepository;

        public Iterable<Address> findAll(){
            return addressRepository.findAll( );
        }

        public void insert(Address address){
            addressRepository.save( address );
            System.out.println("INSERTED: " + address);
        }

        public void update(Address address){
            addressRepository.save( address );
            System.out.println("ALTERED: " + address);
        }

        public void delete(Integer id){
            Address address = addressRepository.findByCode( id );
            addressRepository.delete( address );
            System.out.println("DELETED: " + address);
        }
}
