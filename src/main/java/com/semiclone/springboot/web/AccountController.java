package com.semiclone.springboot.web;

import com.semiclone.springboot.domain.account.Account;
import com.semiclone.springboot.domain.form.SignUpForm;
import com.semiclone.springboot.service.AccountService;
import com.semiclone.springboot.support.SignUpFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final SignUpFormValidator signUpFormValidator;

    @GetMapping("/sign-up")
    public String signup(){
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid SignUpForm signUpForm, Errors errors){

        signUpFormValidator.validate(signUpForm, errors);

        if(errors.hasErrors()){
            return "account/sign-up";
        }

        accountService.processNewAccount(signUpForm);
        return "redirect:/";

    }



}
