package com.restapi.controllers;

import com.restapi.models.*;
import com.restapi.repositories.CartRepository;
import com.restapi.repositories.UserRepository;
import com.restapi.services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static com.restapi.security.ApplicationUserRole.CONSUMER;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;
import static uk.org.lidalia.slf4jtest.LoggingEvent.error;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    TestLogger logger = TestLoggerFactory.getTestLogger(CartService.class);

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private AddressService addressService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserStoreService userStoreService;

    @Mock
    private StoreService storeService;

    @Mock
    private ProductService productService;

    @Mock
    private ShopService shopService;

    private Shop shop;

    private Product product;

    private Price price;

    private Store productInStore;

    private UserModel userModel;

    private Cart cart;

    @AfterEach
    public void clearLoggers() {
        TestLoggerFactory.clear();
    }

    @BeforeEach
    void setUp() {
        shop = new Shop(1, "shop1", false, null);

        product = new Product(1, "Description_product1", new Date(System.currentTimeMillis() + 80000000), "123456789", false, null);

        price = new Price(1, 10L, new Date(), new Date(), false, product);
        product.setPrice(price);

        productInStore = new Store(1, 15, product, shop);

        List<UserStore> cartStores = new ArrayList<>();
        cart = new Cart(1, null, null, cartStores, 0);

        userModel = new UserModel("testuser", "testuser",
                true, true,
                true, true, "12345",
                CONSUMER, cart
        );
        cart.setUserModel(userModel);

        when(cartRepository.findByUserModel(userModel)).thenReturn(userModel.getCart());
    }

    @DisplayName("Validates the insertion of products in the Cart.")
    @ParameterizedTest
    @ValueSource(ints = {1, 5})
    void whenInsertProductUIIsCalledWithValidParametersThenTheProductIsInsertedInTheCart(int quantity) throws Exception {
        List<UserStore> result = cartService.insertProduct(userModel, productInStore, quantity).getUserStores().stream().filter(x ->
                (x.getProductCode() == (product.getCode())
                        && (x.getShopCode() == (shop.getCode())))).collect(Collectors.toList());

        assertThat(result, hasSize(greaterThan(0)));
        assertThat(result.get(0).getQuantity(), is(equalTo(quantity)));
    }

    @DisplayName("Validates the removal of products from the Cart.")
    @Test
    void whenRemoveProductUIIsCalledThenTheProductIsRemovedFromTheCart() throws Exception {
        Cart cartWithStores = cart;
        List<UserStore> cartStores = new ArrayList<>();
        UserStore userStoreToRemove = new UserStore(15, product.getCode(), shop.getCode(), userModel.getUsername());
        cartStores.add(userStoreToRemove);
        cartWithStores.setUserStores(cartStores);

        when(productService.findById(userStoreToRemove.getProductCode())).thenReturn(product);

        Cart result = cartService.removeProduct(userModel, userStoreToRemove);

        assertThat(result.getUserStores(), not(hasItem(userStoreToRemove)));
    }

    @DisplayName("Validates the logging of error when invalid quantity is given.")
    @ParameterizedTest
    @ValueSource(ints = {150, 500})
    void whenInsertProductUIIsCalledWithQuantityGreaterThanStoryQuantityThenErrorLoggingOccurs(int quantity)
    {
        //given

        //when
        cartService.insertProduct(userModel, productInStore, quantity);

        //then
        assertThat(logger.getLoggingEvents(), is(asList(error("Quantity exceeds the number of items in the store."))));

    }


}
