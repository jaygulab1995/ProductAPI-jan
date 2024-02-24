package com.jbk.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ProductAlreadyExistsException.class)
	public ResponseEntity<String> productAlreadyExistsException(ProductAlreadyExistsException ex) {
		
		String message = ex.getMessage();
		
		return new ResponseEntity<String>(message, HttpStatus.OK);
		
	}
	
}
