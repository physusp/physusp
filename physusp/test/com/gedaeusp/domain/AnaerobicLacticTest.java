package com.gedaeusp.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gedaeusp.domain.AnaerobicLactic;
import com.gedaeusp.domain.Constants;
import com.gedaeusp.domain.EnergyValue;
import com.gedaeusp.domain.MolarConcentrationValue;
import com.gedaeusp.domain.WeightValue;

public class AnaerobicLacticTest {

	@Test	
	public void addAnaerobicLacticToReturnZero()
	{
		MolarConcentrationValue minLactic = new MolarConcentrationValue(1.0, MolarConcentrationValue.MiliMolPerLiter);
		MolarConcentrationValue maxLactic = new MolarConcentrationValue(1.0, MolarConcentrationValue.MiliMolPerLiter);
		WeightValue weight = new WeightValue(100.0, WeightValue.Kg);
		
		AnaerobicLactic anaerobicLactic = new AnaerobicLactic();
		EnergyValue result = anaerobicLactic.calculate(minLactic, maxLactic, weight);
		assertEquals(0.0, result.getValue(EnergyValue.Kcal), Constants.LACTIC_EPS);
	}
	
	@Test	
	public void addAnaerobicLacticToReturnProduct()
	{
		MolarConcentrationValue minLactic = new MolarConcentrationValue(0.7, MolarConcentrationValue.MiliMolPerLiter);
		MolarConcentrationValue maxLactic = new MolarConcentrationValue(6.7, MolarConcentrationValue.MiliMolPerLiter);
		WeightValue weight = new WeightValue(74, WeightValue.Kg);
		
		AnaerobicLactic anaerobicLactic = new AnaerobicLactic();
		EnergyValue result = anaerobicLactic.calculate(minLactic, maxLactic, weight); 
		assertEquals(6.66, result.getValue(EnergyValue.Kcal), Constants.LACTIC_EPS);
	}
	
	
}
