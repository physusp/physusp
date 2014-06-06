package com.gedaeusp.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExceptionInfoTest {
	
	@Test	
	public void exceptionInfoTest() {
		ExceptionInfo exceptionInfo = new ExceptionInfo(new Throwable("Oi Alfredo!"));
		assertEquals("java.lang.Throwable", exceptionInfo.getName());
		assertEquals("Oi Alfredo!", exceptionInfo.getMessage());	
	}	
}
