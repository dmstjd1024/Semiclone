package com.semiclone.springboot.web;

import com.semiclone.springboot.domain.account.Account;
import com.semiclone.springboot.domain.account.AccountRepository;
import com.semiclone.springboot.domain.form.SignUpForm;
import com.semiclone.springboot.service.AccountService;
import com.semiclone.springboot.support.SignUpFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final SignUpFormValidator signUpFormValidator;

    @GetMapping("/sign-up")
    public String signUp(){
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid SignUpForm signUpForm, Errors errors){

        signUpFormValidator.validate(signUpForm, errors);

        if(errors.hasErrors()){
            return "account/sign-up";
        }

        Account account = accountService.processNewAccount(signUpForm);
        accountService.login(account);
        return "redirect:/";

    }

    @GetMapping("/mycgv")
    public String myPage(Model model, Principal principal){

            Account account = accountRepository.findByAccountId(principal.getName());

            model.addAttribute("user", account);
            return "user/mypage";

    }



}
