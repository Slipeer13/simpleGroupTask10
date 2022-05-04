package com.example.simplegrouptask10.controller;

import com.example.simplegrouptask10.entity.Product;
import com.example.simplegrouptask10.exceptionHandling.ProductIncorrectData;
import com.example.simplegrouptask10.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/app")
//todo Зачем в названии класса Rest? И так понятно, что это рест контроллер.
public class ProductRestController {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    //todo Можно в одну строку возвращать результат.
    @GetMapping("/products")
    public List<Product> showAllProducts() {
        List<Product> allProducts = productService.findAllProducts();
        return allProducts;
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable(name="id") Long id) {
        Product product = productService.findByIdProduct(id);
        if(product == null) {
            //todo Можно использовать String.format()
            throw new EntityNotFoundException("There is no product with id = " + id);
        }
        return product;
    }

    //todo Эндпоинт будет добавлять продукты с одинаковым title и price в базу бесконечно.
    // Наверное, так не должно быть.
    @PostMapping("/products")
    public Product saveOrUpdateProduct(@RequestBody Product product) {
        productService.saveOrUpdateProduct(product);
        return product;
    }

    //todo Логику поиска и удаления продукта убрать в сервис. Зачем она в контроллере?
    @DeleteMapping("/products/{id}")
    public String deleteProduct(@PathVariable(name="id") Long id) {
        Product product = productService.findByIdProduct(id);
        if(product == null) {
            throw new EntityNotFoundException("There is no product with id = " + id);
        }
        productService.deleteByIdProduct(id);
        return "Product with id " + id + "was deleted";
    }

    @ExceptionHandler
    public ResponseEntity<ProductIncorrectData> handleException(EntityNotFoundException exception) {
        ProductIncorrectData productIncorrectData =  new ProductIncorrectData(exception.getMessage());
        return new ResponseEntity<>(productIncorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ProductIncorrectData> handleException(Exception exception) {
        ProductIncorrectData productIncorrectData =  new ProductIncorrectData(exception.getMessage());
        return new ResponseEntity<>(productIncorrectData, HttpStatus.BAD_REQUEST);
    }

}
