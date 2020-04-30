package com.semiclone.springboot.web;

import com.semiclone.springboot.domain.product.Product;
import com.semiclone.springboot.domain.product.ProductRepository;
import com.semiclone.springboot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/GiftShop")
@RequiredArgsConstructor
public class GiftShopController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @GetMapping("") // 기프트샵 메인
    private String index(Model model){
        model.addAttribute( "products", productRepository.findAll(new Sort(Sort.Direction.DESC, "sales")) );
        return "giftshop";
    }

    @GetMapping("{categoryIdx}") // 기프트샵 카테고리 선택 시
    private String groupByCategory(Model model, @PathVariable("categoryIdx") int categoryIdx){

        List<Product> products = productRepository.findByCategory(categoryIdx);

        model.addAttribute( "products", products);

        return "giftshop";
    }

    @PostMapping("/create") //admin용
    private String productCreate(Product product){

        productService.createProduct(product);

        return "redirect:/giftshop";
    }

    @GetMapping("/product-detail/{productNo}") //상품 클릭 시
    private String productDetail(Model model, @PathVariable Long productNo){

        model.addAttribute("product", productRepository.findByProductNo(productNo) );

        return "giftshop/product-Detail";

    }

/*
    @GetMapping("/purchase-confirm") //결제하기 클릭 시 
    private String payment(Model model, ){
    }
*/
}
