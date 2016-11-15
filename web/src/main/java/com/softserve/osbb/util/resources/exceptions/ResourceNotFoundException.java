/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.util.resources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by nazar.dovhyy on 19.07.2016.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Resource not found")
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
}
