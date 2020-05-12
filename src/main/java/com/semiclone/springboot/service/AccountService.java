package com.semiclone.springboot.service;

import com.semiclone.springboot.domain.account.Account;
import com.semiclone.springboot.domain.account.AccountRepository;
import com.semiclone.springboot.domain.account.UserAccount;
import com.semiclone.springboot.domain.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Account processNewAccount(SignUpForm signUpForm){

        return saveNewAccount(signUpForm);
    }

    private Account saveNewAccount(@Valid SignUpForm signUpForm) {
        Account account = Account.builder()
                .accountId(signUpForm.getAccountId())
                .name(signUpForm.getName())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .email(signUpForm.getEmail())
                .phoneNumber(signUpForm.getPhoneNumber())
                .point(0)
                .userRole("USER")
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

    public void login(Account account) { // password의 plaintext를 사용 안하기 위해서 직접 토큰값을 security로 전달
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                account.getAccountId(),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority(account.getUserRole())));
        SecurityContextHolder.getContext().setAuthentication(token);

    }

    public void editMyInfo(Account account){
        accountRepository.save(account);
    }
}
