package com.semiclone.springboot;

import com.semiclone.springboot.web.GiftShopController;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GiftShopController giftShopController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(giftShopController).build();
    }


    @DisplayName("결제 테스트")
    @Test
    public void 결제테스트() throws Exception{
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "test";
            }
        };

        mockMvc.perform(post("/popcorn-store/payment")
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"imp_uid\":\"imp_541789576970\"}"))
                .andExpect(status().isOk());

    }

}
