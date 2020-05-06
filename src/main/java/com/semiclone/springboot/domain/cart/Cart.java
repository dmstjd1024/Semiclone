package com.semiclone.springboot.domain.cart;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int amount;

    @Builder
    public Cart(Long cartId, String userId, String productId, int price, int amount) {
        this.cartId = cartId;
        this.userId = userId;
        this.productId = productId;
        this.price = price;
        this.amount = amount;
    }
}
