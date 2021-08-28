package com.restapi.controllers;

import com.restapi.services.CartService;
import com.restapi.services.CustomUserDetailsService;
import com.restapi.services.UserStoreService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.restapi.models.Cart;
import com.restapi.models.Store;
import com.restapi.models.UserModel;
import com.restapi.models.UserStore;
import com.restapi.services.StoreService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CartController {

    private final CartService cartService;
    private final UserStoreService userStoreService;
    private final CustomUserDetailsService customUserDetailsService;
    private final StoreService storeService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/findByUser")
    public ResponseEntity findCartByUser(@RequestBody UserModel user){
        Cart result = cartService.findByUserModel(user);

        if (result != null)
        {
            return new ResponseEntity<Cart>(result, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity("Failed to find Cart.", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Cart>> findAll (){
        List<Cart> result = cartService.findAll();

        if (result != null)
        {
            return new ResponseEntity<List<Cart>>(result, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity( "Carts not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path="/insert")
    public ResponseEntity insertProduct(@RequestBody  UserModel userModel, @RequestBody Store store, Integer quantity){
        cartService.insertProduct(userModel, store, quantity);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/remove")
    public ResponseEntity removeProduct(@RequestBody  UserModel userModel, @RequestBody UserStore store){
        cartService.removeProduct(userModel, store);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Synchronized
    @PostMapping(path = "/insert/{shopid}/{productid}/{quantity}")
    public ResponseEntity insertProduct(@PathVariable int shopid, @PathVariable int productid, @PathVariable int quantity){

        Cart result = cartService.insertProduct(
                customUserDetailsService.findUserModelByUserName(  SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()),
                storeService.findById(shopid, productid),
                quantity);

        if (result != null)
        {
            return new ResponseEntity(HttpStatus.OK);
        }

        else
        {
            return new ResponseEntity("Insertion failed.", HttpStatus.BAD_REQUEST);
        }
    }

    @Synchronized
    @PostMapping(path = "/remove/{shopid}/{productid}")
    public ResponseEntity removeProduct(@PathVariable int shopid ,@PathVariable  int productid){
        UserModel user = customUserDetailsService.findUserModelByUserName(  SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        UserStore store = userStoreService.findByUsernameAndProductIdAndShopId(user.getUsername(), productid, shopid);
        Cart result = cartService.removeProduct(user, store);

        if (result != null)
        {
            return new ResponseEntity(HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity("Failed to remove product from Cart.", HttpStatus.BAD_REQUEST);
        }
    }

}

