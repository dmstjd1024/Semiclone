package com.semiclone.springboot;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TicketRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("빠른예매 최초 랜더링 테스트")
    @Test
    public void screensTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ticket/screens"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("빠른예매 선택 7가지 경우의 수 테스트")
    @Test
    public void screensInfoTest() throws Exception {
        String url = null;

        url = "/ticket/screens/info?movieId=5&cinemaId=&date=&group=";
        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk());

        url = "/ticket/screens/info?movieId=&cinemaId=60&date=&group=";
        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk());

        url = "/ticket/screens/info?movieId=&cinemaId=&date=20200502&group=";
        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk());
                
        url = "/ticket/screens/info?movieId=5&cinemaId=60&date=&group=";
        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk());

        url = "/ticket/screens/info?movieId=5&cinemaId=&date=20200502&group=";
        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk());

        url = "/ticket/screens/info?movieId=&cinemaId=60&date=20200502&group=";
        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk());

        url = "/ticket/screens/info?movieId=5&cinemaId=60&date=20200502&group=";
        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
    }//end of screensInfoTest

    @Test
    public void seatsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ticket/seats?timeTableId=5"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }//end of seatsTest

    @Test
    public void ticketStateTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/ticket/ticketstate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"state\":0, \"tickets\":[1]}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.patch("/ticket/ticketstate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"state\":1, \"tickets\":[1]}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }//end of ticketStateTest

    @Test
    public void userServiceTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ticket/user/service?accountid=admin"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }//end of userServiceTest
    
    @Test
    public void paymentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/ticket/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"tickets\":[1], \"imp_uid\":\"imp_541789576970\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }//end of paymentTest

    @Test
    public void cinemasTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/showtimes/timetables/cinemas"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
    }//end of cinemasTest

    @Test
    public void moviesTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/showtimes/timetables/movies"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }//end of moviesTest

    @Test
    public void timeTablesTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/showtimes/timetables?cinemaId=5&date=20200502"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }//end of timeTablesTest

    @Test
    public void timeTablesDataTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/showtimes/timetables/data?movieId=5&cinemaArea=서울&date=20200502"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }//end of timeTablesDataTest

}