package com.restapi.services;

import com.restapi.repositories.AddressRepository;
import com.restapi.models.Address;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddressService {
    private final AddressRepository addressRepository;

    public Iterable<Address> findAll(){
        return addressRepository.findAll( );
    }

    public void insert(Address address){
        addressRepository.save( address );
        log.info("INSERTED: " + address);
    }

    public void update(Address address){
        addressRepository.save( address );
        log.info("ALTERED: " + address);
    }

    public void delete(Integer id){
        Address address = addressRepository.findByCode( id );
        addressRepository.delete( address );
        log.info("DELETED: " + address);
    }
}
