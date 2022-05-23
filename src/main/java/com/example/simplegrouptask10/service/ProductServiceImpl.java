package com.example.simplegrouptask10.service;

import com.example.simplegrouptask10.dao.ProductRepository;
import com.example.simplegrouptask10.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    //todo По порядку:
    //      1. Дополнительная локальная переменная ни к чему. Ты же можешь просто в if возвращать optionalProduct.get()
    //      2. else не принято переносить на новую строку после фигурной скобки. "} else {" более привычная запись.
    //      3. else можно вообще убрать, если делать ретурн из if. throw будет просто после условия.
    //      4. Всю логику метода можно переписать просто в одну строку. Присмотрись к методу orElseThrow() класса Optional.
    //      В этом классе вообще много интересных методов.
    public Product findByIdProduct(long id) {
        Product product;
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            product = optionalProduct.get();
        }
        else {
            throw new EntityNotFoundException(String.format("There is no product with id = %s", id));
        }
        return product;
    }

    @Override
    public void deleteByIdProduct(long id) {
        findByIdProduct(id);
        productRepository.deleteById(id);
    }

    @Override
    //todo Лучше бросить эксепшен, если продукт null в начале метода. Остальную логику тогда можно оставить ниже.
    public Product saveOrUpdateProduct(Product product) {
        if (product != null) {
            Product productFromDB = productRepository.findByTitleAndPrice(product.getTitle(), product.getPrice());
            if (product.equals(productFromDB)) {
                throw new EntityExistsException("there is such a product in database");
            }
            productRepository.save(product);
            //todo Зачем снова получать из БД продукт по названию и прайсу?
            // Он ведь уже есть в данном случае: productFromDB.
            // Можно просто return productRepository.save(product), ведь этот метод возвращает сохраняемую сущность.
            return productRepository.findByTitleAndPrice(product.getTitle(), product.getPrice());
        } else {
            throw new EntityNotFoundException("the product is null");
        }
    }

}
