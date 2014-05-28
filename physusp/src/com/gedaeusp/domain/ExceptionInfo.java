package com.gedaeusp.domain;

public class ExceptionInfo {

	private String message;
	private String name;
	
	public ExceptionInfo(Exception ex) {
		this.name = ex.getClass().getName();
		this.message = ex.getMessage();
	}
	
	public String getMessage() {
		return message;
	}
	public String getName() {
		return name;
	}
	
}
