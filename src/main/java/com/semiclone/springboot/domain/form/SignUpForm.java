package com.semiclone.springboot.domain.form;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class SignUpForm {

    @NotBlank
    @Length(min=3, max = 20)
    private String accountId;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotBlank
    private String retypePassword;
}
