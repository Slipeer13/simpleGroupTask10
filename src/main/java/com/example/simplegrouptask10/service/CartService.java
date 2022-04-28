package com.example.simplegrouptask10.service;


import com.example.simplegrouptask10.entity.Cart;
import com.example.simplegrouptask10.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    Product addProductToCart(Product product, Long cartId);

    Product deleteProductFromCart(Product product, Long cartId);

    Cart findByIdCart(long id);

    List<Product> findAllProductsFromCart(Long cartId);

    List<Cart> findAll();

    Cart createCart();

}
