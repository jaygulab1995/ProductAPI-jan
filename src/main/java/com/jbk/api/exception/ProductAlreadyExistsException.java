package com.jbk.api.exception;

public class ProductAlreadyExistsException extends RuntimeException{
	
	public ProductAlreadyExistsException(String msg) {
		super(msg);
	}

}
