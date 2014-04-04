package com.gedaeusp.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UnitValueTest {
	@Test	
	public void convert100KgTo_g()
	{
		UnitValue<WeightUnit> weight = new UnitValue<WeightUnit>(100.0, WeightUnit.Kg);
		double weightIn_g = weight.getValue(WeightUnit.g);
		assertEquals(100000.0, weightIn_g, 0.000000001);
	}
}
