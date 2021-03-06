package com.restapi.controllers;

import com.restapi.models.*;
import com.restapi.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.util.*;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainController
{
    private final StoreService storeService;
    private final ShopService shopService;
    private final ProductService productService;
    private final PriceService priceService;
    private final CartService cartService;
    private final AddressService addressService;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserStoreService userStoreService;

    @RequestMapping("/")
    public ModelAndView MainPage() {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("shopservice", shopService);
        mv.addObject("cartservice", cartService);
        mv.addObject("usermodel",  customUserDetailsService.findUserModelByUserName(   SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
        return mv;
    }

    @PostConstruct
    private void start() {
        customUserDetailsService.startUsers();

        Shop shop = new Shop(1, "shop1", false, null);
        shopService.insert(shop);

        Product product = new Product(1, "Description_product1", new Date(System.currentTimeMillis() + 80000000), "123456789", false, null);
        productService.insert(product);

        Price price = new Price(1, 10L, new Date(), new Date(), false, product);
        priceService.insert(price);

        Store store = new Store(1, 1, product, shop);
        storeService.insert(store);

        List<Store> s = new ArrayList<Store>();
        s.add(store);
        shop.setStore(s);
        shopService.update(shop);

        product.setPrice(price);
        productService.update(product);

        Shop shop2 = new Shop(2, "shop2", false, null);
        shopService.insert(shop2);

        Product product2 = new Product(2, "Description_product2", new Date(System.currentTimeMillis() + 80000000), "123456789", false, null);
        productService.insert(product2);

        Price price2 = new Price(2, 100L, new Date(), new Date(), false, product2);
        priceService.insert(price2);

        Store store2 = new Store(2, 10, product2, shop2);
        storeService.insert(store2);

        List<Store> s2 = new ArrayList<Store>();
        s2.add(store2);
        shop2.setStore(s2);
        shopService.update(shop2);

        product2.setPrice(price2);
        productService.update(product2);

        Store store3 = new Store(3, 15, product2, shop);
        storeService.insert(store3);
        List<Store> s3  = shop.getStore();
        s3.add(store3);
        shop.setStore(s3);
        shopService.update(shop);

        //add cart to user admin
        UserModel user = customUserDetailsService.findUserModelByUserName("admin");
        List<UserStore> cartStores = new ArrayList<>();
        Cart cart = new Cart(1, user, null, cartStores, 0);
        cartService.insert(cart);
        Address address = new Address(1,"city", "street","4645789", 1245,  cart);
        addressService.insert(address);
        cart.setAddress(address);
        cartService.update(cart);
        user.setCart(cart);
        customUserDetailsService.register(user);
        cartService.update(cart);
        try {
            cartService.insertProduct(user, store, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //add cart to user consumer
        UserModel user2 = customUserDetailsService.findUserModelByUserName("consumer");
        List<UserStore> cartStores2 = new ArrayList<>();
        Cart cart2 = new Cart(2, user2, null, cartStores2, 0);
        cartService.insert(cart2);
        Address address2 = new Address(2,"city2", "street2","4645789", 1245,  cart2);
        addressService.insert(address2);
        cart2.setAddress(address2);
        cartService.update(cart2);
        user2.setCart(cart2);
        customUserDetailsService.register(user2);
        cartService.update(cart2);
    }

}
