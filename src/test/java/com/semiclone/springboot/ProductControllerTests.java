package com.semiclone.springboot;


import com.semiclone.springboot.web.GiftShopController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GiftShopController giftShopController;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(giftShopController).build();
    }

    @Test
    public void product() throws Exception{

        mockMvc.perform( get("/popcorn-store") )
                .andDo(print())
                .andExpect( status().isOk());
    }

    @Test
    public void product_category() throws Exception{

        mockMvc.perform( get("/popcorn-store")
                .param("categoryIdx", "3"))
                .andDo(print())
                .andExpect( status().isOk());
    }

    @Test
    public void create_product() throws Exception{

        mockMvc.perform(post("/popcorn-store/create")
                .param("productNo", "1234")
                .param("name", "테스트상품")
                .param("price", "3000")
                .param("size", "Large")
                .param("product_image", "src/ddd/ddd")
                .param("category", "1")
                .param("kind", "종류1")
                .param("sales", "0")
        )
                .andExpect(status().is3xxRedirection());
    }

}
