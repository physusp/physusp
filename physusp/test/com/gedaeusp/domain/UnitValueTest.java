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
	
	@Test	
	public void convert100lTo_ml()
	{
		UnitValue<VolumeUnit> volume = new UnitValue<VolumeUnit>(100.0, VolumeUnit.l);
		double volumeIn_ml = volume.getValue(VolumeUnit.ml);
		assertEquals(100000.0, volumeIn_ml, 0.000000001);
	}
	
	@Test	
	public void convert100mlTo_l()
	{
		UnitValue<VolumeUnit> volume = new UnitValue<VolumeUnit>(100.0, VolumeUnit.ml);
		double volumeIn_l = volume.getValue(VolumeUnit.l);
		assertEquals(0.1, volumeIn_l, 0.000000001);
	}
}
