package com.gedaeusp;

import static org.junit.Assert.*;

import org.junit.Test;

public class AnaerobicLacticTest {

	@Test	
	public void addAnaerobicLacticToReturnZero()
	{
		LactateValue minLactic = new LactateValue(1.0, LactateValue.MiliMolPerLiter);
		LactateValue maxLactic = new LactateValue(1.0, LactateValue.MiliMolPerLiter);
		WeightValue weight = new WeightValue(100.0, WeightValue.Kg);
		
		AnaerobicLactic anaerobicLactic = new AnaerobicLactic();
		EnergyValue result = anaerobicLactic.calculate(minLactic, maxLactic, weight);
		assertEquals(0.0, result.getValue(EnergyValue.Kcal), Constants.LACTIC_EPS);
	}
	
	@Test	
	public void addAnaerobicLacticToReturnProduct()
	{
		LactateValue minLactic = new LactateValue(0.7, LactateValue.MiliMolPerLiter);
		LactateValue maxLactic = new LactateValue(6.7, LactateValue.MiliMolPerLiter);
		WeightValue weight = new WeightValue(74, WeightValue.Kg);
		
		AnaerobicLactic anaerobicLactic = new AnaerobicLactic();
		EnergyValue result = anaerobicLactic.calculate(minLactic, maxLactic, weight); 
		assertEquals(6.66, result.getValue(EnergyValue.Kcal), Constants.LACTIC_EPS);
	}
	
	
}
