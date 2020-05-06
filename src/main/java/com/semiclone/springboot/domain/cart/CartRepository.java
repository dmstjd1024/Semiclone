package com.semiclone.springboot.domain.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUserId(String userId);

    Cart findByCartId(Long cartId);

    Long deleteAllByCartId(Long cartId);

}
