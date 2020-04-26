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
    private int price;
    private String size;
    private String product_image;
    @Column (nullable = false)
    private int category;
    @Column
    private String kind;
    @Column
    private Long sales; // 판매량

}
