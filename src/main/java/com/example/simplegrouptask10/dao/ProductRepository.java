package com.example.simplegrouptask10.dao;

import com.example.simplegrouptask10.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByTitleAndPrice(String title, int price);

}
