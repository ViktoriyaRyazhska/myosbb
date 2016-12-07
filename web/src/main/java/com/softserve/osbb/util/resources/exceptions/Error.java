package com.softserve.osbb.util.resources.exceptions;

/**
 * 
 * @author Kostyantyn Panchenko
 * @version 1.0
 * @since 07.12.2016
 *
 */
public class Error {
    private int code;
    private String message;

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
