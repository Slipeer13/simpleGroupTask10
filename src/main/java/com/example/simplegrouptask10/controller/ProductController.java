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

    //todo Всё таки метод контроллера для добавления продукта, а не сохранения продукта (не совсем корректное название метода).
    @PostMapping("/products")
    public Product saveProduct(@Valid @RequestBody Product product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new EntityNotFoundException("the product title must be min 2 symbols and price must be positive");
        }
        return productService.saveOrUpdateProduct(product);
    }

    @PutMapping("/products")
    public Product updateProduct(@Valid @RequestBody Product product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new EntityNotFoundException("the product title must be min 2 symbols and price must be positive");
        }
        return productService.saveOrUpdateProduct(product);
    }

    @DeleteMapping("/products")
    public String deleteProduct(@RequestBody Long id) {
        productService.deleteByIdProduct(id);
        return "Product with id " + id + " was deleted";
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
