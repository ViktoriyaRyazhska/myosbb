package com.softserve.osbb.config;

import com.softserve.osbb.PersistenceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Import(PersistenceConfiguration.class)
@ComponentScan(basePackages = "com.softserve.osbb.service")
public class ServiceApplication {
    public static void main(String[] args) {

        SpringApplication.run(new Class<?>[]{ServiceApplication.class, TransactionManager.class}, args);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}