package com.example.simplegrouptask10.service;

import com.example.simplegrouptask10.cart.Cart;
import com.example.simplegrouptask10.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService{

    private ProductService productService;
    private Cart cart;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public Product addProductToCart(Long productId) {
        Product product = productService.findByIdProduct(productId);
        cart.addProductToCart(product);
        return product;
    }

    @Override
    public Product deleteProductFromCart(Long productId) {
        Product product = productService.findByIdProduct(productId);
        Map<Product, Integer> products = cart.getProducts();
        if(products.containsKey(product)) {
            cart.deleteProductFromCart(product);
        }
        else {
            throw new EntityNotFoundException("There is no product to cart");
        }
        return product;
    }

    @Override
    public Map<Product, Integer> findAllProductsFromCart() {
        return cart.getProducts();
    }



}
