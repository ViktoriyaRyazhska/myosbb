/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.util.resources.exceptions;

/**
 * Exception class. To be thrown when entity is not found.
 * 
 * Created by Anastasiia Fedorak on 13.07.2016.
 * @version 1.1
 * @since 15.11.2016 
 */
public class EntityNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Default no-args constructor.
     */
    public EntityNotFoundException() { }
    
    /**
     * Constructor with String-based parameter.
     * @param message exception's message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
