package com.semiclone.springboot.service;

import com.semiclone.springboot.domain.cart.Cart;
import com.semiclone.springboot.domain.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public List<Cart> cartList(String userId){

        return cartRepository.findAllByUserId(userId);

    }

    public void deleteProduct(Long cartId){ //장바구니 상품 삭제

        cartRepository.delete(cartRepository.findByCartId(cartId));

    }

    public void deleteCart(Long cartId){ //장바구니 비우기

        cartRepository.deleteAllByCartId(cartId);

    }

    public void modifyCart(Cart cart) { //장바구니 수정

        cartRepository.save(cart);

    }


}
