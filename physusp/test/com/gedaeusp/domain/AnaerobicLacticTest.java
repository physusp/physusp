package com.gedaeusp.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gedaeusp.domain.AnaerobicLactic;
import com.gedaeusp.domain.Constants;
import com.gedaeusp.domain.UnitValue;
import com.gedaeusp.domain.MolarConcentrationUnit;

public class AnaerobicLacticTest {

	@Test	
	public void addAnaerobicLacticToReturnZero()
	{
		UnitValue<MolarConcentrationUnit> minLactic = new UnitValue<MolarConcentrationUnit>(1.0, MolarConcentrationUnit.MiliMolPerLiter);
		UnitValue<MolarConcentrationUnit> maxLactic = new UnitValue<MolarConcentrationUnit>(1.0, MolarConcentrationUnit.MiliMolPerLiter);
		UnitValue<WeightUnit> weight = new UnitValue<WeightUnit>(100.0, WeightUnit.Kg);
		
		AnaerobicLactic anaerobicLactic = new AnaerobicLactic();
		UnitValue<EnergyUnit> result = anaerobicLactic.calculate(minLactic, maxLactic, weight);
		assertEquals(0.0, result.getValue(EnergyUnit.Kcal), Constants.LACTIC_EPS);
	}
	
	@Test	
	public void addAnaerobicLacticToReturnProduct()
	{
		UnitValue<MolarConcentrationUnit> minLactic = new UnitValue<MolarConcentrationUnit>(0.7, MolarConcentrationUnit.MiliMolPerLiter);
		UnitValue<MolarConcentrationUnit> maxLactic = new UnitValue<MolarConcentrationUnit>(6.7, MolarConcentrationUnit.MiliMolPerLiter);
		UnitValue<WeightUnit> weight = new UnitValue<WeightUnit>(74, WeightUnit.Kg);
		
		AnaerobicLactic anaerobicLactic = new AnaerobicLactic();
		UnitValue<EnergyUnit> result = anaerobicLactic.calculate(minLactic, maxLactic, weight); 
		assertEquals(6.66, result.getValue(EnergyUnit.Kcal), Constants.LACTIC_EPS);
	}
	
	
}
