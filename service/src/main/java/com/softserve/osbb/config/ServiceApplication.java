/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextListener;

import com.softserve.osbb.PersistenceConfiguration;

@SpringBootApplication(excludeName = "com.softserve.osbb.service")
@Import(PersistenceConfiguration.class)
public class ServiceApplication {

    /**
     * Enables adjusting request and session scopes per each service if needed.
     * @return servlet listener that exposes the request to the current thread
     */
    @Bean public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[]{ServiceApplication.class, TransactionManager.class}, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
