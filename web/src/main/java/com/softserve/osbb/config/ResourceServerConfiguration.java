/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * Created by cavayman on 30.08.2016. 
 * @version 1.1
 * @since 15.11.2016
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("restservice");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/oauth/token").permitAll()
                .antMatchers("/restful/address/**").permitAll()
                .antMatchers("/restful/apartment/**").permitAll()
                .antMatchers("/restful/attachment").permitAll()
                .antMatchers("/restful/house/**").permitAll()
                .antMatchers("/restful/house/all/**").permitAll()
                .antMatchers("/restful/osbb").permitAll()
                .antMatchers("/restful/report/**").permitAll()
                .antMatchers("/restful/report**").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/registration/osbb").permitAll()
                .antMatchers("/validEmail").permitAll()
                .antMatchers("/forgotEmail").permitAll()                                
                .antMatchers(HttpMethod.POST,"/restful/user/**").permitAll()
                .antMatchers("/restful/**").authenticated();
    }
}
