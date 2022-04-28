package com.example.simplegrouptask10.mapper;

import com.example.simplegrouptask10.dto.ProductDTO;
import com.example.simplegrouptask10.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class );

    @Mapping(source = "id", target = "id")
    ProductDTO productProductDTO(Product product);

    @Mapping(source = "id", target = "id")
    Product productDTOToProduct(ProductDTO productDTO);
}
