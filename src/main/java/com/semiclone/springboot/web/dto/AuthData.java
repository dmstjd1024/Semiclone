package com.semiclone.springboot.web.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AuthData {

    private String imp_key;
    private String imp_secret;

    public AuthData(String imp_key, String imp_secret){
        this.imp_key = imp_key;
        this.imp_secret = imp_secret;
    }

}