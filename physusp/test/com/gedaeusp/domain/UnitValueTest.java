package com.gedaeusp.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UnitValueTest {
	@Test	
	public void convert100KgTo_g()
	{
		UnitValue weight = new UnitValue(100.0, "Kg");
		double weightIn_g = weight.getValue("g");
		assertEquals(100000.0, weightIn_g, 0.000000001);
	}
}
