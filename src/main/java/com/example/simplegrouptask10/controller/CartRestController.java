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

    @GetMapping("/cart")
    public List<Cart> showAllCarts() {
        List<Cart> allCarts = cartService.findAll();
        return allCarts;
    }

    @GetMapping("/cart/{cartId}")
    public List<ProductDTO> showProductFromCart(@PathVariable Long cartId) {
        List<Product> allProducts = cartService.findAllProductsFromCart(cartId);
        List<ProductDTO> productDTOList = productListMapper.productToProductDto(allProducts);

        return productDTOList;
    }

    @PutMapping("/cart/{cartId}")
    public ProductDTO addProductToCart(@RequestBody ProductDTO productDTO, @PathVariable Long cartId) {
        Product product = productMapper.INSTANCE.productDTOToProduct(productDTO);
        Product productFromDB =  cartService.addProductToCart(product, cartId);
        if (productFromDB == null) {
            throw new EntityNotFoundException("There is no product");
        }
        return productDTO;
    }

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
