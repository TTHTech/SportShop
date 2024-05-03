package com.example.sportshop.service;

import com.example.sportshop.model.Product;
import com.example.sportshop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public List<Product> getAllProduct() {
        return this.productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return this.productRepository.findProductById(id);
    }

    @Transactional
    public void deleteProduct(Long id) {
        this.productRepository.deleteProductById(id);
    }

}
