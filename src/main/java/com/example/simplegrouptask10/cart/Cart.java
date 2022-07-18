package com.example.simplegrouptask10.cart;

import com.example.simplegrouptask10.entity.Product;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;

@Component
@Data
//todo 1. Если провести аналогию с Product, то корзина это хранилище данных, если можно так сказать. (dto)
// В этом классе не должно быть бизнес-логики. Класс имеет структуру для хранения данных и должен предоставлять
// общие методы для доступа к данным и их модификации (геттеры, сеттеры, конструкторы).
// И тут тоже нужно подумать, все ли они нужны.
// Полный доступ даёт возможность случайно просто снести все хранимые данные, безопасно ли это? И нужно ли?
// Для размещения методов логики должен быть отдельный класс (dao). Например, для доступа к продукту есть ProductRepository.
// 2. Зачем в БД остались отношения корзины и связи между продуктом и корзиной?
public class Cart {
    private HashMap<Product, Integer> products = new HashMap<>();

    //todo Можно переписать в 1 строку добавление продукта. Обрати внимание на метод хэшмапы getOrDefault.
    // Либо, можно ещё проще сделать одним методом HashMap. Предлагаю тебе поисследовать класс HashMap.
    // И зачем метод возвращает продукт? Он ведь продукт никак не меняет.
    // В том месте, где ты вызываешь этот метод и так уже есть этот продукт, который ты передаёшь в параметрах метода.
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
