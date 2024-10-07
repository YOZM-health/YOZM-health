package com.example.yozmhealth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){    // 비밀번호를 암호화하고 검증하는 설정
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
