package com.example.simplegrouptask10.service;

import com.example.simplegrouptask10.dao.ProductRepository;
import com.example.simplegrouptask10.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findByIdProduct(long id) {
        Product product;
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            product = optionalProduct.get();
        }
        else {
            //todo Можно использовать String.format()
            throw new EntityNotFoundException(String.format("There is no product with id = %s", id));
        }
        return product;
    }

    @Override
    public void deleteByIdProduct(long id) {
        findByIdProduct(id);
        productRepository.deleteById(id);
    }

    @Override
    public Product saveOrUpdateProduct(Product product) {
        if (product != null) {
            Product productFromDB = productRepository.findByTitleAndPrice(product.getTitle(), product.getPrice());
            if (product.equals(productFromDB)) {
                throw new EntityExistsException("there is such a product in database");
            }
            productRepository.save(product);
            return productRepository.findByTitleAndPrice(product.getTitle(), product.getPrice());
        } else {
            throw new EntityNotFoundException("the product is null");
        }
    }

}
