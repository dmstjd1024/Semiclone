package com.semiclone.springboot.domain.account;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserAccount extends User {

    private Account account;

    public UserAccount(Account account){
        super(account.getAccountId(), account.getPassword(), List.of(new SimpleGrantedAuthority( UserRole.USER.getTitle() )));
        this.account = account;
    }

}
