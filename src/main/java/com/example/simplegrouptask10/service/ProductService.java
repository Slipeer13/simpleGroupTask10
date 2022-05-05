package com.example.simplegrouptask10.service;


import com.example.simplegrouptask10.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();

    Product findByIdProduct(long id);

    void deleteByIdProduct(long id);

    Product saveOrUpdateProduct(Product product);

}
