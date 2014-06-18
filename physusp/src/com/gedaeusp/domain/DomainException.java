package com.gedaeusp.domain;

@SuppressWarnings("serial")
public class DomainException extends RuntimeException {

	private String location;

	public DomainException(String message) {
		super(message);
	}
	
	public DomainException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DomainException(String message, String location) {
		super(message);
		this.location = location;
	}

	public DomainException(String message, Throwable cause, String location) {
		super(message, cause);
		this.location = location;
	}
	
	public String getLocation() {
		return location;
	}
	
}
