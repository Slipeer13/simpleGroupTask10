package com.example.simplegrouptask10.service;

import com.example.simplegrouptask10.dao.CartRepository;
import com.example.simplegrouptask10.entity.Cart;
import com.example.simplegrouptask10.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceimpl implements CartService{

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
    public Product addProductToCart(Product product, Long cartId) {
        Product productFromDB = null;
        //todo Зачем вытаскивать все существующие продукты?
        // Хорошо, когда продуктов в БД не много. Но если их там несколько миллионов?
        // Ты сформируешь список из всех этих продуктов и будешь проверять, есть ли в этом списке искомый?
        List<Product> productList = productService.findAllProducts();
        if(productList.contains(product)) {
            Cart cart = findByIdCart(cartId);
            cart.getProductList().add(product);
            cartRepository.save(cart);
            productFromDB = product;
        }
        return productFromDB;
    }

    @Override
    public Product deleteProductFromCart(Product product, Long cartId) {
        Product productFromCart = null;
        Cart cart = findByIdCart(cartId);
        //todo Если корзины с заданным id нет, то упадет NPE
        List<Product> productList = cart.getProductList();
        if(productList.contains(product)) {
            productList.remove(product);
            cartRepository.save(cart);
            productFromCart = product;
        }
        return productFromCart;
    }

    @Override
    public Cart findByIdCart(long id) {
        Cart cart = null;
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if(optionalCart.isPresent()) {
            cart = optionalCart.get();
        }
        return cart;

    }

    @Override
    public List<Product> findAllProductsFromCart(Long cartId) {
        Cart cart = findByIdCart(cartId);
        //todo Если корзины с заданным id нет, то упадет NPE
        return cart.getProductList();
    }

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public Cart createCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);
        return cart;
    }
}
