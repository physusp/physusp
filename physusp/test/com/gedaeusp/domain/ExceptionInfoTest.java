package com.gedaeusp.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExceptionInfoTest {
	
	@Test	
	public void exceptionInfoTest() {
		ExceptionInfo exceptionInfo = new ExceptionInfo(new DomainException("Oi Alfredo!"));
		assertEquals("com.gedaeusp.domain.DomainException", exceptionInfo.getName());
		assertEquals("Oi Alfredo!", exceptionInfo.getMessage());	
	}	
}
