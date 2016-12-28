package com.softserve.osbb.service.exceptions;

public class GoogleDriveException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String message;
    
    public GoogleDriveException(String message) {
        this.message = message;
    }
    
    public String GetMessage() {
        return message;
    }

}
