package com.example.simplegrouptask10.service;

import com.example.simplegrouptask10.dao.CartRepository;
import com.example.simplegrouptask10.dao.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService{
    private ProductRepository productRepository;
    private CartRepository cartRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public void addProductToCart(Long productId) {
        boolean exist = productRepository.existsById(productId);
        if(exist) {
            cartRepository.addProductToCart(productId);
        } else {
            throw new EntityNotFoundException("there is no product with this id in the database");
        }
    }

    @Override
    public void deleteProductFromCart(Long productId) {
        if(cartRepository.getProducts().containsKey(productId)) {
            cartRepository.deleteProductFromCart(productId);
        }
        else {
            throw new EntityNotFoundException("There is no product to cart");
        }
    }

    @Override
    public Map<Long, Integer> findAllProductsFromCart() {
        return cartRepository.getProducts();
    }



}
