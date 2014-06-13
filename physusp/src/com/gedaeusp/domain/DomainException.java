package com.gedaeusp.domain;

@SuppressWarnings("serial")
public class DomainException extends RuntimeException {

	public DomainException(String message) {
		super(message);
	}
	
	public DomainException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
