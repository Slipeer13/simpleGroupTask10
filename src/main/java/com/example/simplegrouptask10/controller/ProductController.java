package com.example.simplegrouptask10.controller;

import com.example.simplegrouptask10.entity.Product;
import com.example.simplegrouptask10.exceptionHandling.ProductIncorrectData;
import com.example.simplegrouptask10.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/app")
public class ProductController {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> showAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable(name="id") Long id) {
        return productService.findByIdProduct(id);
    }

    //todo Если следовать принципам rest, то POST - create, PUT - update. Их не принято объединять в один метод.
    // Для чего нужен конкретно этот метод не понятно. Судя по названию должен создавать новый продукт или обновлять существующий.
    // Если погружаться в логику сервиса, то метод только создаёт новые записи.
    @PostMapping("/products")
    public Product saveOrUpdateProduct(@Valid @RequestBody Product product, BindingResult bindingResult) {
        //todo Вот тут не понял. Если ошибок валидации нет, то мы бросаем эксепшен?
        if(!bindingResult.hasErrors()) {
            throw new EntityNotFoundException("the product title must be min 2 symbols and price must be positive");
        }
        return productService.saveOrUpdateProduct(product);
    }

    @DeleteMapping("/products/{id}")
    public String deleteProduct(@PathVariable(name="id") Long id) {
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
