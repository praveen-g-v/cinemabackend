package com.nehruCollege.cinema.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ServiceException extends Exception{

	
	private static final long serialVersionUID = 1L;
	private final HttpStatus errorCode;
    private final List<String> details;
    
	
	public ServiceException(String s) {
		super(s);
		this.errorCode = HttpStatus.BAD_REQUEST;
		this.details = new ArrayList<>();
		
	}
	
	public ServiceException(String message, HttpStatus errorCode) {
	    super(message);
	    this.errorCode = errorCode;
	    this.details = new ArrayList<>();
	}
	
	public ServiceException(String message, HttpStatus errorCode, List<String> details) {
	    super(message);
	    this.errorCode = errorCode;
	    this.details = details;
	}
	
	// Getters
	public HttpStatus getErrorCode() {
	    return errorCode;
	}
	
	public List<String> getDetails() {
	    return details;
	}
	
}
