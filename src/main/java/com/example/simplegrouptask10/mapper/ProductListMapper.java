package com.example.simplegrouptask10.mapper;

import com.example.simplegrouptask10.dto.ProductDTO;
import com.example.simplegrouptask10.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface ProductListMapper {

    List<ProductDTO> productToProductDto(List<Product> productList);

    List<Product> productDTOToProduct(List<ProductDTO> productDTO);
}
