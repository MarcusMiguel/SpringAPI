package com.restapi.repositories;

import com.restapi.models.ApplicationUser;
import com.restapi.models.Cart;
import com.restapi.models.UserModel;
import lombok.Synchronized;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String>
{
    Cart findByUserModel(UserModel userModel);

    Cart findByCode(Integer code);
}
