package com.softserve.osbb.service.exceptions;

public class InvalidEmailException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
    private String message;
    
	 public InvalidEmailException(String message) {
	        this.message = message;
	    }
	    
	    public String GetMessage() {
	        return message;
	    }
		
}
