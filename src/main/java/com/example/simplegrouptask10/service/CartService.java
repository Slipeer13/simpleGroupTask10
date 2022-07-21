package com.example.simplegrouptask10.service;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public interface CartService {

    void addProductToCart(Long productId);

    void deleteProductFromCart(Long productId);

    Map<Long, Integer> findAllProductsFromCart();

}
