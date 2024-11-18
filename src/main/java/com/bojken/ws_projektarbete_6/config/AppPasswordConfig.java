package com.bojken.ws_projektarbete_6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppPasswordConfig {

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {

        // Default value = 10
        return new BCryptPasswordEncoder(15);   // Time based = Higher number, Slower the process
    }
}
