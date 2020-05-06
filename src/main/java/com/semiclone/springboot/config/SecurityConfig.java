package com.semiclone.springboot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**", "/sign-up", "/swagger-ui.html").permitAll()
                .antMatchers("/mycgv/**").authenticated()
                .antMatchers("/admin/**, /popcorn-store/create").hasRole("ADMIN")
//                .antMatchers("/").hasRole("USER")
                ;

        http
                .formLogin()
//                .loginPage("/login")
                .defaultSuccessUrl("/");
        http
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")
                .clearAuthentication(true)
                .invalidateHttpSession(true);

        http
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/login")
                .maxSessionsPreventsLogin(true);

        http
                .rememberMe()
                .userDetailsService(userDetailsService)
                .tokenRepository(tokenRepository());
    }


    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect(){
       return new SpringSecurityDialect();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher(){
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }




}
