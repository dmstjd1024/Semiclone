package com.semiclone.springboot.service;

import com.semiclone.springboot.domain.account.UserAccount;
import com.semiclone.springboot.domain.form.SignUpForm;
import com.semiclone.springboot.domain.account.Account;
import com.semiclone.springboot.domain.account.AccountRepository;
import com.semiclone.springboot.domain.account.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void processNewAccount(SignUpForm signUpForm){
        Account newAccount = saveNewAccount(signUpForm);
    }

    private Account saveNewAccount(@Valid SignUpForm signUpForm) {
        Account account = Account.builder()
                .accountId(signUpForm.getAccountId())
                .name(signUpForm.getName())
                .residentMember(signUpForm.getResidentMember())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .email(signUpForm.getEmail())
                .phoneNumber(signUpForm.getPhoneNumber())
                .nickname(signUpForm.getNickname())
                .point(0)
                .role(UserRole.USER)
                .build();

        return accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountRepository.findByAccountId(username);
        if(account == null){
            throw new UsernameNotFoundException(username);
        }

        return new UserAccount(account);
    }

}
