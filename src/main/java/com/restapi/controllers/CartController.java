package com.restapi.controllers;

import com.restapi.models.*;
import com.restapi.services.*;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserStoreService userStoreService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private StoreService storeService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/findByUser")
    public Cart findCartByUser(@RequestBody UserModel user){
        return cartService.findByUserModel(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Iterable<Cart> findAll (){
        return cartService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path="/insert")
    public void insertProduct(@RequestBody  UserModel userModel, @RequestBody Store store, Integer quantity){
        cartService.insertProduct(userModel, store, quantity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/remove")
    public void removeProduct(@RequestBody  UserModel userModel, @RequestBody UserStore store){
        cartService.removeProduct(userModel, store);
    }

    @Synchronized
    @PostMapping(path = "/removeUI/{shopid}/{productid}")
    public void removeProductUI(@PathVariable int shopid ,@PathVariable  int productid){
        UserModel user = customUserDetailsService.findUserModelByUserName(  SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        UserStore store =   userStoreService.findByUsernameAndProductIdAndShopId(user.getUsername(), productid, shopid);
        cartService.removeProduct(user, store);
    }

    @Synchronized
    @PostMapping(path="/insertUI/{shopid}/{productid}/{quantity}")
    public void insertUI(@PathVariable int shopid, @PathVariable int productid, @PathVariable int quantity){

        cartService.insertProduct(
                customUserDetailsService.findUserModelByUserName(  SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()),
                storeService.findById(shopid, productid),
                quantity);
    }

}

