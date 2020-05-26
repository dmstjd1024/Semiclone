package com.semiclone.springboot;

import com.semiclone.springboot.domain.account.Account;
import com.semiclone.springboot.domain.account.AccountRepository;
import com.semiclone.springboot.web.AccountController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc()
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountController accountController;

    @Autowired
    private AccountRepository accountRepository;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @DisplayName("회원 가입 화면 보이는지 테스트")
    @Test
    public void signUpForm() throws Exception {
        mockMvc.perform(get("/sign-up"))
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("회원 가입 처리 - 입력값 오류")
    @Test
    public void signUpSubmit_with_wrong_input() throws Exception {
        mockMvc.perform(post("/sign-up")
                .param("accountId", "test")
                .param("name", "전은성")
                .param("password", "111")
                .param("email", "xe00123@gmail.com")
                .param("phoneNumber", "010-1234-5678")
                .param("role", "USER")
                .param("retypePassword", "111"))
                .andExpect(status().isOk())
                .andDo(print())
        ;
    }

    @DisplayName("회원 가입 처리 - 입력값 정상")
    @Test
    public void signUpSubmit_with_correct_input() throws Exception {
        mockMvc.perform(post("/sign-up")
                .param("accountId", "test2")
                .param("name", "전은성")
                .param("password", "1234567")
                .param("email", "xe001234@gmail.com")
                .param("phoneNumber", "010-1234-5678")
                .param("role", "USER")
                .param("retypePassword", "1234567"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
        ;


        Account account = accountRepository.findByAccountId("test2");
        assertNotNull(account);
        assertNotEquals(account.getPassword(), "1234567");

    }

    @After
    public void after() {
        accountRepository.delete(accountRepository.findByAccountId("test2"));
    }

/*    @DisplayName("로그인 성공 여부 확인")
    @Test
    public void login_correct_input() throws Exception {
        mockMvc.perform(post("/login"))
    }*/

}