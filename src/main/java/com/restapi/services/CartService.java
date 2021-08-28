package com.restapi.services;

import com.restapi.repositories.CartRepository;
import com.restapi.models.Cart;
import com.restapi.models.Store;
import com.restapi.models.UserModel;
import com.restapi.models.UserStore;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CartService {

    private final ProductService productService;
    private final StoreService storeService;
    private final ShopService shopService;
    private final CartRepository cartRepository;
    private final UserStoreService userStoreService;
    private final CustomUserDetailsService customUserDetailsService;

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
        log.info("INSERTED: " + cart);
    }

    public void update(Cart cart){
        cartRepository.save( cart );
        log.info("ALTERED: " + cart);
    }

    @Synchronized
    @Transactional
    public  Cart  insertProduct(UserModel userModel, Store product, int quantity){

        Cart cart = cartRepository.findByUserModel(userModel);

        List<UserStore> products = cart.getUserStores();

        List<UserStore> aux = products.stream().filter(x ->
                (x.getProductCode()== (product.getProductCode().getCode())
                        && (x.getShopCode()==(product.getAffiliateCode().getCode())))).collect(Collectors.toList());
        UserStore productPresent = null;
        if (!aux.isEmpty()){productPresent = aux.get(0);};
        if ( productPresent != null){
            if (quantity <= product.getQuantity()) {
                productPresent.setQuantity(productPresent.getQuantity() + quantity);
                userStoreService.update(productPresent);

                List<UserStore> newuserStores = (products.stream().filter(x ->
                        (x.getProductCode()!= (product.getProductCode().getCode())
                                || (x.getShopCode()!=(product.getAffiliateCode().getCode())))).collect(Collectors.toList()));
                newuserStores.add(productPresent);
                cart.setUserStores(newuserStores);
                cart.setTotalCost((int) (cart.getTotalCost()+quantity*product.getProductCode().getPrice().getValue()));
                cartRepository.save(cart);
                storeService.removeProducts(quantity, product);
            }
            else {
                log.error("Quantity exceeds the number of items in the store.");
                return null;
            }
        }
        else if (quantity <= product.getQuantity()){
            UserStore store = new UserStore(null, quantity, cart,product.getProductCode().getCode(), product.getAffiliateCode().getCode(), userModel.getUsername());
            userStoreService.insert(store);
            products.add(store);
            cart.setUserStores(products);
            cart.setTotalCost((int) (cart.getTotalCost()+quantity*product.getProductCode().getPrice().getValue()));
            cartRepository.save(cart);
            store.setCart(cart);
            userStoreService.update(store);
            storeService.removeProducts(quantity, product);
        }
        else {
            log.error("Quantity exceeds the number of items in the store.");
            return null;
        }
        userModel.setCart(cart);
        customUserDetailsService.register(userModel);
        return cart;
    }

    @Synchronized
    @Transactional
    public Cart removeProduct(UserModel userModel, UserStore product) {

        Cart cart = cartRepository.findByUserModel( userModel );

        List<UserStore> newCartUserStores = cart.getUserStores().stream().filter
                (x -> (x.getProductCode()!= (product.getProductCode())
                        || (x.getShopCode()!=(product.getShopCode())))).collect(Collectors.toList());
        if ( newCartUserStores.size() == cart.getUserStores().size())
        {
            return null;
        }

        Store s = storeService.findById(product.getShopCode(), product.getProductCode() );
        if (s != null)
        {
            s.setQuantity(s.getQuantity() + product.getQuantity());
            storeService.update(s);
        }
        else
        {
            Store cartStore = new Store(null, product.getQuantity(),productService.findById(product.getProductCode()), shopService.findById(product.getShopCode()) );
            storeService.insert(cartStore);
        }

        cart.setUserStores(newCartUserStores);
        cart.setTotalCost(cart.getTotalCost() - (int) (productService.findById(product.getProductCode()).getPrice().getValue()*product.getQuantity()));
        cartRepository.save(cart);
        userStoreService.delete(userModel.getUsername(), product.getProductCode(),product.getShopCode());

        return cart;
    }
}
