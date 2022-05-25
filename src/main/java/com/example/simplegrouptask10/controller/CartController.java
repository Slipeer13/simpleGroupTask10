package com.example.simplegrouptask10.controller;

import com.example.simplegrouptask10.entity.Cart;
import com.example.simplegrouptask10.entity.Product;
import com.example.simplegrouptask10.exceptionHandling.ProductIncorrectData;
import com.example.simplegrouptask10.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@RestController
@RequestMapping("/app")
public class CartController {
    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart")
    public Cart createCart() {
        return cartService.createCart();
    }

    @GetMapping("/cart")
    public List<Cart> showAllCarts() {
        return cartService.findAll();
    }

    @GetMapping("/cart/{cartId}")
    public List<Product> showProductFromCart(@PathVariable Long cartId) {
        return cartService.findAllProductsFromCart(cartId);
    }

    @PutMapping("/cart/{cartId}")
    public Product addProductToCart(@RequestBody Long productId, @PathVariable Long cartId) {
        return cartService.addProductToCart(productId, cartId);
    }

    @DeleteMapping("/cart/{cartId}")
    public String deleteProductFromCart(@RequestBody Long productId, @PathVariable Long cartId) {
        Product productFromCart = cartService.deleteProductFromCart(productId, cartId);
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
