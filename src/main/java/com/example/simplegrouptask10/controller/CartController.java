package com.example.simplegrouptask10.controller;

import com.example.simplegrouptask10.entity.Product;
import com.example.simplegrouptask10.exceptionHandling.ProductIncorrectData;
import com.example.simplegrouptask10.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Map;


@RestController
@RequestMapping("/app")
public class CartController {
    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public Map<Product, Integer> findAllProductsFromCart() {
        return cartService.findAllProductsFromCart();
    }

    @PutMapping("/cart")
    public Product addProductToCart(@RequestBody Long productId) {
        return cartService.addProductToCart(productId);
    }

    @DeleteMapping("/cart")
    public String deleteProductFromCart(@RequestBody Long productId) {
        Product productFromCart = cartService.deleteProductFromCart(productId);
        return "Product  "  + productFromCart.getTitle() + " was deleted from cart";
    }

    @ExceptionHandler
    public ResponseEntity<ProductIncorrectData> handleException(EntityNotFoundException exception) {
        ProductIncorrectData productIncorrectData =  new ProductIncorrectData(exception.getMessage());
        return new ResponseEntity<>(productIncorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ProductIncorrectData> handleException(Exception exception) {
        ProductIncorrectData productIncorrectData =  new ProductIncorrectData(exception.getMessage());
        return new ResponseEntity<>(productIncorrectData, HttpStatus.BAD_REQUEST);
    }
}
