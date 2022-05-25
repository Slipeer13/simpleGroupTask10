package com.example.simplegrouptask10.cart;

import com.example.simplegrouptask10.entity.Product;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;

@Component
@Data
public class Cart {
    private HashMap<Product, Integer> products = new HashMap<>();

    public Product addProductToCart(Product product) {
        if(products.containsKey(product)) {
            products.put(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
        return product;
    }

    public void deleteProductFromCart(Product product) {
        if(!products.containsKey(product)) {
            throw new EntityNotFoundException("this product is not in the cart");
        }
        if(products.get(product).equals(1)) {
            products.remove(product);
        } else {
            products.put(product, products.get(product) - 1);
        }
    }
}
