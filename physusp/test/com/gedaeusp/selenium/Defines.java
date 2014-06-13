package com.gedaeusp.selenium;

public class Defines {
	
	public Defines(){}
	
	public static String geDomain()
	{
		String localDomain = "http://localhost:8080";
		@SuppressWarnings("unused")
		String serverDomain = "http://localhost:9090";
		return localDomain;
	}
	
}
