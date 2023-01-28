package com.api.meusgastos.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@ConfigurationProperties(prefix = "spring.mail")
public class ConfigJavaMail {
    
   @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }
}
