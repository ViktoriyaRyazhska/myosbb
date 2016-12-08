package com.softserve.osbb.service.exceptions;

/**
 * 
 * @author Kostyantyn Panchenko
 * @version 1.0
 * @since 07.12.2016
 *
 */
public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private int id;
    
    public EntityNotFoundException(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

}
