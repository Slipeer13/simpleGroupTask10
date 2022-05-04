package com.example.simplegrouptask10.dto;

import lombok.Data;

//todo Лучше ProductDto. И так по всем классам, методам, где есть DTO.
@Data
public class ProductDTO {

    private Long id;

    private String title;

    private Integer price;

}
