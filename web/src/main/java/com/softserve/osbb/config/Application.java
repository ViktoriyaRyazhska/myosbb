/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * Initializes this Spring Boot application.
 * 
 * Created by nataliia on 24.07.16.
 * @version 1.1
 * @since 15.11.2016 
 */
@SpringBootApplication(scanBasePackages = "com.softserve.osbb")
public class Application extends SpringBootServletInitializer {

    /**
     * Bootstraps Spring Boot application.
     * @param args CLI arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

}
