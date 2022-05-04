package com.example.simplegrouptask10.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //todo Не рекомендуется в имени использовать уточнение структуры (например List).
    // Лучше назвать просто products. "Чистый код", Роберт Мартин, 1 глава. :)
    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "cart_product", joinColumns = @JoinColumn(name = "cart_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> productList;

}
