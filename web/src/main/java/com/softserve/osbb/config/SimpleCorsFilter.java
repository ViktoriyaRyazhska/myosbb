/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A filter that performs filtering tasks on either the request to a resource (a servlet or static 
 * content), or on the response from a resource, or both. 
 * 
 * Created by cavayman on 30.08.2016.
 * @author Kostyantyn Panchenko
 * @version 1.2
 * @since 28.12.2016
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {

    private final Logger LOGGER = LoggerFactory.getLogger(SimpleCorsFilter.class);
    /**
     * Default no-args constructor.
     */
    public SimpleCorsFilter() { }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        
        LOGGER.info("Received request from " + request.getRemoteAddr());
        
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization,Content-Type,");
        response.setHeader("Access-Control-Allow-Credentials", "true");        
        
        /*
         * This block is needed to ensure that CORS request for file uploading will be granted access.
         * It checks if request came from a localhost or another origin and set appropriate
         * Access-Control-Allow-Origin header value.
         * If running in development mode from port other that 8080 - change line No 64 to your port.
         * As an option of this block consider setting Access-Control-Allow-Credentials to false.
         */         
        if (request.getRequestURI().contains("/restful/google-drive/upload/")) {           
            if (request.getRemoteAddr().contains("127.0.0.1") 
                    || request.getRemoteAddr().contains("localhost")
                    || request.getRemoteAddr().contains("0:0:0:0:0:0:0:1")) {                    
                response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
                LOGGER.info("Granted access for localhost");
            } else {
                response.setHeader("Access-Control-Allow-Origin", request.getRemoteAddr());
            }
        }
        
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig filterConfig) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
    }
}
