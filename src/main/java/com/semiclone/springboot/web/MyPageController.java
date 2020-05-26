package com.semiclone.springboot.web;

import com.semiclone.springboot.domain.account.Account;
import com.semiclone.springboot.domain.account.AccountRepository;
import com.semiclone.springboot.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;


@RequestMapping("mycgv")
@RequiredArgsConstructor
@Controller
public class MyPageController {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    
    private final TicketRestController ticketRestController;



    @GetMapping()
    public String myPage(Model model, Principal principal) {

        Account account = accountRepository.findByAccountId(principal.getName());

        model.addAttribute("user", account);

        return "user/mypage";
    }

    @GetMapping("/edit-myinfo")
    public String editMyInfo(Model model, Principal principal){

        Account account = accountRepository.findByAccountId(principal.getName());

        model.addAttribute("user", account);

        return "user/mypage";

    }

    @PostMapping("/edit-myinfo")
    public String updateMyInfo(@RequestParam Account account){

        accountService.editMyInfo(account);

        return "redirect:user/mypage";

    }





}
