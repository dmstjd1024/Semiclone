package com.semiclone.springboot.web;

import com.semiclone.springboot.domain.cart.Cart;
import com.semiclone.springboot.domain.cart.CartRepository;
import com.semiclone.springboot.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/popcorn-store/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartRepository cartRepository;
    private final CartService cartService;

    @PostMapping("/insert") // 장바구니에 상품 추가
    public String insertCart(@ModelAttribute Cart cart, Principal principal) {


        if (principal.getName() == null)
            return "/login";

        cart.setUserId(principal.getName());

        List<Cart> myCartList = cartRepository.findAllByUserId(principal.getName());

        for (int i = 0; i < myCartList.size(); i++) {
            if (cart.getProductId().equals(myCartList.get(i).getProductId())) {
                cart.setCartId( myCartList.get(i).getCartId() );
                cart.setAmount( cart.getAmount() + myCartList.get(i).getAmount() );
                break;
            }
        }

        cartRepository.save(cart);

        return "redirect:/popcorn-store/cart/list";

    }

    @GetMapping("/list")
    public String list(Model model, Principal principal) {

        if (principal.getName() == null) {
            return "/login";
        } else {

            List<Cart> cartList = cartService.cartList(principal.getName());

            model.addAttribute("cartList", cartList);
            return "popcorn-store/list";
        }

    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long cartId) {
        cartService.deleteProduct(cartId);
        return "redirect:/popcorn-store/cart/list";
    }

    @DeleteMapping("/delete-all") // 장바구니 비우기
    public String deleteAll(@RequestParam Long cartId) {
        cartService.deleteCart(cartId);
        return "redirect:/popcorn-store/cart/list";
    }

    @PostMapping("/update")
    public String update(@RequestParam int[] amount, @RequestParam String[] productId, @RequestParam Long[] cartId, Principal principal) {

        if (principal.getName() == null) {
            return "/login";
        } else {

            for (int i = 0; i < productId.length; i++) {
                Cart cart = Cart.builder()
                        .cartId(cartId[i])
                        .userId(principal.getName())
                        .amount(amount[i])
                        .productId(productId[i])
                        .build();
                cartService.modifyCart(cart);
            }
        }


        return "redirect:/popcorn-store/cart/list";
    }


}


