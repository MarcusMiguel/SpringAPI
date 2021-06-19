package com.restapi.repositories;

import com.restapi.models.UserModel;
import com.restapi.models.UserStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStoreRepository extends JpaRepository<UserStore, Long> {

    UserStore findByUsernameAndProductCodeAndShopCode(String username, int productCode, int shopCode);

    void deleteByUsernameAndProductCodeAndShopCode(String username, int productCode, int shopCode);

}
