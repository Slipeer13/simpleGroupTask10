package com.example.simplegrouptask10.dao;

import com.example.simplegrouptask10.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
