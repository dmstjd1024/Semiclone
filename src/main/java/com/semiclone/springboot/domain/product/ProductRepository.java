package com.semiclone.springboot.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(int categoryIdx);

    Product findByProductNo(Long productNo);

}