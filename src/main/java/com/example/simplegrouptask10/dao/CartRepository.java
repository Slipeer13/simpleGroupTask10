package com.example.simplegrouptask10.dao;

import java.util.Map;

public interface CartRepository {

    void deleteProductFromCart(Long productId);

    void addProductToCart(Long productId);

    Map<Long, Integer> getProducts();
}
