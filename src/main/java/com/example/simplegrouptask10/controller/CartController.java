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
//todo Зачем Rest в названии класса?
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

    //todo Можно возвращать в одну строку результат.
    @GetMapping("/cart")
    public List<Cart> showAllCarts() {
        return cartService.findAll();
    }

    //todo Убери логику в сервис.
    // Если попытаться посмотреть продукты в несуществующей корзине,
    // то контроллер бросит 500 ошибку, а сервис бросит NPE. Наверное, это не ожидаемое поведение.
    // Зачем вводим DTO? Может это и обосновано, но в текущей реализации дто полностью повторяет экземпляр сущности.
    // Я думаю, ДТО нужно, чтобы не показывать какие-то не нужные аттрибуты на выходе из контроллера,
    // или чтобы получать на входе только нужные аттрибуты.
    // Если тут используем ДТО, то, наверное, в ней лишнее поле id? Либо я не понял задумки, зачем нужно ДТО.
    @GetMapping("/cart/{cartId}")
    public List<Product> showProductFromCart(@PathVariable Long cartId) {
        //todo Можно возвращать в одну строку.
        return cartService.findAllProductsFromCart(cartId);
    }

    //todo Тоже не понятно, зачем Dto, если она полностью повторяет сущность. Почему не получать сразу сущность?
    // Вообще не очень понятна необходимость для получения в эндпоинт передавать джейсон продукта полностью.
    // Логичнее добавлять в корзину продукт по его id. Или это требование задания в курсе, который ты проходишь?
    @PutMapping("/cart/{cartId}")
    public Product addProductToCart(@RequestBody Long productId, @PathVariable Long cartId) {
        return cartService.addProductToCart(productId, cartId);
    }

    //todo Мне кажется, что название метода не корректное. Может лучше deleteProductFromCart?
    // Я бы тоже удалял продукт по id.
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
