package com.semiclone.springboot;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void product() throws Exception{

        mockMvc.perform( get("/popcorn-store") )
                .andExpect( status().isOk());
    }

    @Test
    public void create_product() throws Exception{

        mockMvc.perform(post("/popcorn-store/create")
                .param("productNo", "1234")
                .param("name", "테스트상품")
                .param("price", String.valueOf(3000))
                .param("size", "Large")
                .param("category", String.valueOf(1))
                .param("kind", "종류")
                .param("sales", String.valueOf(0))
        )
                .andExpect(view().name("redirect:/popcorn-store"))
                .andExpect(status().is3xxRedirection());
    }

}
