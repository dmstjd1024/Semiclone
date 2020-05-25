package com.semiclone.springboot.config;

import com.semiclone.springboot.support.IamportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Configuration
@PropertySource("classpath:iamport.yml")
public class IamPortConfig {
    
    @Value("${apikey}")
    private String apikey;

    @Value("${apisecret}")
    private String apisecret;

    @Bean
    public IamportClient iamportClient(){
        return new IamportClient(apikey, apisecret);
    }
}//end of class