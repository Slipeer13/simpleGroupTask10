package com.example.simplegrouptask10.service;

import com.example.simplegrouptask10.dao.CartRepository;
import com.example.simplegrouptask10.entity.Cart;
import com.example.simplegrouptask10.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    private CartRepository cartRepository;
    private ProductService productService;

    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Product addProductToCart(Long productId, Long cartId) {
        Product product = productService.findByIdProduct(productId);
        Cart cart = findByIdCart(cartId);
        cart.getProductList().add(product);
        cartRepository.save(cart);
        return product;
    }

    @Override
    public Product deleteProductFromCart(Long productId, Long cartId) {
        Product product = productService.findByIdProduct(productId);
        Cart cart = findByIdCart(cartId);
        List<Product> productList = cart.getProductList();
        if(productList.contains(product)) {
                productList.remove(product);
                cartRepository.save(cart);
        }
        else {
            throw new EntityNotFoundException("There is no product to cart");
        }
        return product;
    }

    @Override
    //todo См. метод findByIdProduct
    public Cart findByIdCart(long id) {
        Cart cart;
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if(optionalCart.isPresent()) {
            cart = optionalCart.get();
        }
        else {
            throw new EntityNotFoundException("There is no cart");
        }
        return cart;
    }

    @Override
    public List<Product> findAllProductsFromCart(Long cartId) {
        Cart cart = findByIdCart(cartId);
        return cart.getProductList();
    }

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    //todo Можно переписать в 1 строку.
    public Cart createCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);
        return cart;
    }
}
