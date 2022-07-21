package com.example.simplegrouptask10.controller;

import com.example.simplegrouptask10.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
public class CartController {
    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public Map<Long, Integer> findAllProductsFromCart() {
        return cartService.findAllProductsFromCart();
    }

    //todo Разве лонги десериализуются таким образом? У тебя точно методы этого контроллера работают?
    // У меня при попытке отправить в теле запроса
    // {
    //    "productId": 2
    // }
    // приложение бросает ошибку.
    // Чтобы передать в теле запроса только айдишник, у тебя в приложении должен быть определён класс,
    // в атрибутах которого определён айдишник. И тебе нужно в теле запрос передавать джейсон этого класса.
    @PutMapping("/cart")
    public void addProductToCart(@RequestBody Long productId) {
        cartService.addProductToCart(productId);
    }

    @DeleteMapping("/cart")
    public void deleteProductFromCart(@RequestBody Long productId) {
        cartService.deleteProductFromCart(productId);
    }

}
