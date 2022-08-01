package com.example.simplegrouptask10.dao;

import com.example.simplegrouptask10.cart.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class CartRepositoryImpl implements CartRepository{
    private Map<Long, Integer> products;

    @Autowired
    public void setProducts(Cart cart) {
        this.products = cart.getProducts();
    }

    @Override
    public Map<Long, Integer> getProducts() {
        return products;
    }

    //todo Можно переписать в 1 строку добавление продукта. Обрати внимание на метод хэшмапы getOrDefault.
    // Либо, можно ещё проще сделать одним методом HashMap. Предлагаю тебе поисследовать класс HashMap.
    // И зачем метод возвращает продукт? Он ведь продукт никак не меняет.
    // В том месте, где ты вызываешь этот метод и так уже есть этот продукт, который ты передаёшь в параметрах метода.
    @Override
    public void addProductToCart(Long productId) {
        products.merge(productId,1, Integer::sum);
    }

    @Override
    public void deleteProductFromCart(Long product) {
        if(products.get(product).equals(1)) {
            products.remove(product);
        } else {
            products.put(product, products.get(product) - 1);
        }
    }
}
