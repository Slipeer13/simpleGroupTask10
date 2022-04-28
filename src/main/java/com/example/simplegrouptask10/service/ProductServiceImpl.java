package com.example.simplegrouptask10.service;

import com.example.simplegrouptask10.dao.ProductRepository;
import com.example.simplegrouptask10.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Product findByIdProduct(long id) {
        Product product = null;
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            product = optionalProduct.get();
        }
        return product;
    }

    @Override
    public void deleteByIdProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void saveOrUpdateProduct(Product product) {
        productRepository.save(product);
    }


}
