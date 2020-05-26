package com.semiclone.springboot.support;

import com.semiclone.springboot.domain.form.SignUpForm;
import com.semiclone.springboot.domain.account.Account;
import com.semiclone.springboot.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpForm signUpForm = (SignUpForm)target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountId", "NotEmpty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");

        if(signUpForm.getAccountId().length() < 3 || signUpForm.getAccountId().length() > 32)
            errors.rejectValue("accountId", "Size.userForm.username");

        else if(accountRepository.findByAccountId(signUpForm.getAccountId()) != null)
            errors.rejectValue("accountId", "Duplicate.userForm.username");

        else if(accountRepository.findByEmail(signUpForm.getEmail()) != null)
            errors.rejectValue("email", "Duplicate.userFrom.email");


        else if(signUpForm.getPassword().length() < 7 || signUpForm.getPassword().length() > 32)
            errors.rejectValue("password", "Size.userForm.password");

        else if(signUpForm.getRetypePassword() != signUpForm.getPassword())
            errors.rejectValue("retypePassword", "Equal.userForm.password");
    }

}
