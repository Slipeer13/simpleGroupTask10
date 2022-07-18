package com.example.simplegrouptask10.service;

import com.example.simplegrouptask10.dao.ProductRepository;
import com.example.simplegrouptask10.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

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
    public Product findByIdProduct(long id) {
        return productRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(String.format("There is no product with id = %s", id)));
    }

    @Override
    public void deleteByIdProduct(long id) {
        findByIdProduct(id);
        productRepository.deleteById(id);
    }

    @Override
    public Product saveOrUpdateProduct(Product product) {
        if (product == null) {
            throw new EntityNotFoundException("the product is null");
        }
        //todo Если задать уникальность записей на уровне базы, то эту логику можно будет тут не писать.
        // Хибер сам будет бросать эксепшен при попытке записи некорректных данных.
        // Запрос на получение продукта из БД будет не нужен. Предлагаю поэкспериментировать.
        Product productFromDB = productRepository.findByTitleAndPrice(product.getTitle(), product.getPrice());
        if (product.equals(productFromDB)) {
            throw new EntityExistsException("there is such a product in database");
        }
        return productRepository.save(product);
    }

}
