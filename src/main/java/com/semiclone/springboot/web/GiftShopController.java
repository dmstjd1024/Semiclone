package com.semiclone.springboot.web;

import com.semiclone.springboot.domain.product.Product;
import com.semiclone.springboot.domain.product.ProductRepository;
import com.semiclone.springboot.service.ProductService;
import com.semiclone.springboot.service.PurchaseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/popcorn-store")
@RequiredArgsConstructor
public class GiftShopController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    private final PurchaseService purchaseService;

    @GetMapping() // 기프트샵 메인
    private String index(Model model) {

        List<Product> productList = productRepository.findAll();

        model.addAttribute("products", productList);

        return "";

    }

    @GetMapping("{categoryIdx}") // 기프트샵 카테고리 선택 시
    private String groupByCategory(Model model, @PathVariable("categoryIdx") int categoryIdx) {

        List<Product> products = productService.findByCategory(categoryIdx);

        model.addAttribute("products", products);

        return "popcorn-store";
    }

    @PostMapping("/create") //admin용
    private String productCreate(Product product) {

        productService.createProduct(product);

        return "redirect:/popcorn-store";
    }

    @GetMapping("/product-detail/{productNo}") //상품 클릭 시
    private String productDetail(Model model, @PathVariable Long productNo) {

        model.addAttribute("product", productRepository.findByProductNo(productNo));

        return "popcorn-store/product-detail";
    }

    @GetMapping("/payment-confirm")
    public String paymentConfirm(Model model) {

        return "";
    }


    @ApiOperation(value = "",
            notes = "Server로 test 보낼 Data {\"imp_uid\":\"imp_541789576970\"}")
    @PostMapping("/payment")
    public String payment(@RequestBody Map<String, Object> purchaseMap) throws Exception {

        purchaseService.giftshopPurchase(purchaseMap.get("imp_uid").toString());

        return "popcorn-store/success";
    }
}
