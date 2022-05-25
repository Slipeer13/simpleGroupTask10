package com.example.simplegrouptask10.service;


import com.example.simplegrouptask10.entity.Product;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface CartService {

    Product addProductToCart(Long productId);

    Product deleteProductFromCart(Long productId);

    Map<Product, Integer> findAllProductsFromCart();

}
