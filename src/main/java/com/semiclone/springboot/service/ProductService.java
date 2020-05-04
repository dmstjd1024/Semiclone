package com.semiclone.springboot.service;

import com.semiclone.springboot.domain.product.Product;
import com.semiclone.springboot.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(Product product) { // admin용
        productRepository.save(product);
    }

    public List<Product> findByCategory(int category) {

        List<Product> productList;

        if (category == 0)
            productList = productRepository.findAll();
        else
            productList = productRepository.findByCategory(category);

        return productList;
    }
}