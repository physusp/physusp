package com.gedaeusp.domain;

public class ExceptionInfo {

	private String message;
	private String name;
	private String location;
	
	public ExceptionInfo(DomainException ex) {
		this.name = ex.getClass().getName();
		this.message = ex.getMessage();
		this.location = ex.getLocation();
	}
	
	public String getMessage() {
		return message;
	}
	public String getName() {
		return name;
	}
	
	public String getLocation() {
		return location;
	}
	
}
