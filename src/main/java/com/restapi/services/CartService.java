package com.restapi.services;

import com.restapi.models.*;
import com.restapi.repositories.CartRepository;
import com.restapi.repositories.PriceRepository;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

        @Autowired
        private ProductService productService;

        @Autowired
        private StoreService storeService;

        @Autowired
        private ShopService shopService;

        @Autowired
        private CartRepository cartRepository;

        @Autowired
        private UserStoreService userStoreService;

        @Autowired
        private CustomUserDetailsService customUserDetailsService;

        public List<Cart> findAll(){
            return cartRepository.findAll( );
        }

        public Cart findByUserModel(UserModel userModel) {
            return cartRepository.findByUserModel( userModel );
        }

        public Cart findByCode(int code){
            return cartRepository.findByCode(code);
        }

        public void insert(Cart cart){
            cartRepository.save( cart );
            System.out.println("INSERTED: " + cart);
        }

        public void update(Cart cart){
            cartRepository.save( cart );
            System.out.println("ALTERED: " + cart);
        }

        @Synchronized
        @Transactional
        public  void  insertProduct(UserModel userModel, Store product, int quantity) {
            Cart cart = cartRepository.findByUserModel(userModel);
            List<UserStore> products = cart.getUserStores();
            try{
                //try to get the product in the cart
                List<UserStore> aux = products.stream().filter(x ->
                        (x.getProductCode()== (product.getProductCode().getCode())
                                && (x.getShopCode()==(product.getAffiliateCode().getCode())))).collect(Collectors.toList());
                UserStore productPresent = null;
                if (!aux.isEmpty()){productPresent = aux.get(0);};
                //If the product is present in the userCart already
                if ( productPresent != null){
                    //if the quantity in the store is enough
                    if (quantity <= product.getQuantity()) {
                        //update the quantity in the userstore
                        productPresent.setQuantity(productPresent.getQuantity() + quantity);
                        userStoreService.update(productPresent);

                        //update the userstore in the cart
                        List<UserStore> newuserStores = (products.stream().filter(x ->
                                (x.getProductCode()!= (product.getProductCode().getCode())
                                        || (x.getShopCode()!=(product.getAffiliateCode().getCode())))).collect(Collectors.toList()));
                        newuserStores.add(productPresent);
                        cart.setUserStores(newuserStores);
                        cart.setTotalCost((int) (cart.getTotalCost()+quantity*product.getProductCode().getPrice().getValue()));
                        cartRepository.save(cart);

                        //remove the quantity from the store
                        storeService.removeProducts(quantity, product);
                    }
                    else { throw new Exception("Quantity exceeds the number of items in the store."); }
                }
                //if the product is not present in the cart
                else if (quantity <= product.getQuantity()){
                    //Create new userstore
                    UserStore store = new UserStore(null, quantity, cart,product.getProductCode().getCode(), product.getAffiliateCode().getCode(), userModel.getUsername());
                    userStoreService.insert(store);
                    //Add a new userstore to the cart
                    products.add(store);
                    cart.setUserStores(products);
                    cart.setTotalCost((int) (cart.getTotalCost()+quantity*product.getProductCode().getPrice().getValue()));
                    cartRepository.save(cart);
                    //Add cart to userstore
                    store.setCart(cart);
                    userStoreService.update(store);
                    //update quantity on the shop
                    storeService.removeProducts(quantity, product);
                }
                else {
                    throw new Exception("Quantity exceeds the number of items in the store.");
                }
                UserModel user = customUserDetailsService.findUserModelByUserName(userModel.getUsername());
                user.setCart(cart);
                customUserDetailsService.register(user);

            }catch (Exception e){
                System.out.println(e);
            }
        }

        @Synchronized
        @Transactional
        public  void removeProduct(UserModel userModel, UserStore product) {
            Cart cart = cartRepository.findByUserModel( userModel );
            List<UserStore> products = cart.getUserStores();

            Store s = storeService.findById(product.getShopCode(), product.getProductCode() );

            if (s != null){ //shop has some of the product in the store
                s.setQuantity(s.getQuantity() + product.getQuantity());
                storeService.update(s);
            }
            else{ //shop do not have product(just add new store)
                Store cartStore = new Store(null, product.getQuantity(),productService.findById(product.getProductCode()), shopService.findById(product.getShopCode()) );
                storeService.insert(cartStore);
            }
            //remove product from cart and userStore
            cart.setUserStores(cart.getUserStores().stream().filter
                    (x -> (x.getProductCode()!= (product.getProductCode())
                    || (x.getShopCode()!=(product.getShopCode())))).collect(Collectors.toList()));
            cart.setTotalCost(cart.getTotalCost() - (int) (productService.findById(product.getProductCode()).getPrice().getValue()*product.getQuantity()));
            cartRepository.save(cart);
            userStoreService.delete(userModel.getUsername(), product.getProductCode(),product.getShopCode());
        }
    }
