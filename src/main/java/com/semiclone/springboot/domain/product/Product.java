package com.semiclone.springboot.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long productNo;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private String size;
    @Column(nullable = false)
    private String product_image;
    @Column (nullable = false)
    private int category;
    @Column(nullable = false)
    private String kind;
    @Column(nullable = false)
    private Long sales; // 판매량

}
