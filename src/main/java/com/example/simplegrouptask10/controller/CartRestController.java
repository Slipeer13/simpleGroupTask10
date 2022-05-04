package com.example.simplegrouptask10.controller;

import com.example.simplegrouptask10.dto.ProductDTO;
import com.example.simplegrouptask10.entity.Cart;
import com.example.simplegrouptask10.entity.Product;
import com.example.simplegrouptask10.exceptionHandling.ProductIncorrectData;
import com.example.simplegrouptask10.mapper.ProductListMapper;
import com.example.simplegrouptask10.mapper.ProductMapper;
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
public class CartRestController {
    private CartService cartService;
    private ProductMapper productMapper;
    private ProductListMapper productListMapper;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }
    @Autowired
    public void setProductListMapper(ProductListMapper productListMapper) {
        this.productListMapper = productListMapper;
    }

    @PostMapping("/cart")
    public Cart createCart() {
        return cartService.createCart();
    }

    //todo Можно возвращать в одну строку результат.
    @GetMapping("/cart")
    public List<Cart> showAllCarts() {
        List<Cart> allCarts = cartService.findAll();
        return allCarts;
    }

    //todo Убери логику в сервис.
    // Если попытаться посмотреть продукты в несуществующей корзине,
    // то контроллер бросит 500 ошибку, а сервис бросит NPE. Наверное, это не ожидаемое поведение.
    // Зачем вводим DTO? Может это и обосновано, но в текущей реализации дто полностью повторяет экземпляр сущности.
    // Я думаю, ДТО нужно, чтобы не показывать какие-то не нужные аттрибуты на выходе из контроллера,
    // или чтобы получать на входе только нужные аттрибуты.
    // Если тут используем ДТО, то, наверное, в ней лишнее поле id? Либо я не понял задумки, зачем нужно ДТО.
    @GetMapping("/cart/{cartId}")
    public List<ProductDTO> showProductFromCart(@PathVariable Long cartId) {
        List<Product> allProducts = cartService.findAllProductsFromCart(cartId);
        //todo Можно возвращать в одну строку.
        List<ProductDTO> productDTOList = productListMapper.productToProductDto(allProducts);

        return productDTOList;
    }

    //todo Тоже не понятно, зачем Dto, если она полностью повторяет сущность. Почему не получать сразу сущность?
    // Вообще не очень понятна необходимость для получения в эндпоинт передавать джейсон продукта полностью.
    // Логичнее добавлять в корзину продукт по его id. Или это требование задания в курсе, который ты проходишь?
    @PutMapping("/cart/{cartId}")
    public ProductDTO addProductToCart(@RequestBody ProductDTO productDTO, @PathVariable Long cartId) {
        Product product = productMapper.INSTANCE.productDTOToProduct(productDTO);
        Product productFromDB =  cartService.addProductToCart(product, cartId);
        if (productFromDB == null) {
            throw new EntityNotFoundException("There is no product");
        }
        return productDTO;
    }

    //todo Мне кажется, что название метода не корректное. Может лучше deleteProductFromCart?
    // Я бы тоже удалял продукт по id.
    @DeleteMapping("/cart/{cartId}")
    public String deleteProductToCart(@RequestBody ProductDTO productDTO, @PathVariable Long cartId) {
        Product product = productMapper.INSTANCE.productDTOToProduct(productDTO);
        Product productFromCart = cartService.deleteProductFromCart(product, cartId);
        if (productFromCart == null) {
            throw new EntityNotFoundException("There is no product from cart");
        }
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
