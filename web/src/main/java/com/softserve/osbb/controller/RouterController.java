/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by nazar.dovhyy on 24.08.2016.
 */
@Controller
public class RouterController {

    @RequestMapping({"/", "/admin","/admin/**", "/login", "/home/**", "/forgotPass"})
    public String index() {
        return "forward:/index.html";
    }

}
