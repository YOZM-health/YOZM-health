package com.example.yozmhealth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@Configuration
@EnableWebSecurity
public class securityConfig {

    //비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration)throws Exception{
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public HttpFirewall defaultFireWell(){
        return new DefaultHttpFirewall();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .httpFirewall(defaultFireWell())
                .ignoring()
                .requestMatchers("/js/**","/webjars/**", "/dist/**", "/plugins/**",
                        "/css/**","/favicon.ico","/h2-console/**","/**");
    }

    //시큐리티 체인 설정 (권한 및 OAuth2 설정)
    @Bean
    public SecurityFilterChain configure (HttpSecurity security) throws Exception{
        security.formLogin(form -> form.disable());
        return security.build();
    }
}
