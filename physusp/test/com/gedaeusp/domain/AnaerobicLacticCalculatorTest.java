/*
Copyright 2014 António Miranda, Caio Valente, Igor Topcin, Jorge Melegati, Thales Paiva, Victor Santos

This file is part of PhysUSP.

PhysUSP is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

PhysUSP is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PhysUSP. If not, see <http://www.gnu.org/licenses/>.
*/

package com.gedaeusp.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gedaeusp.domain.AnaerobicLacticCalculator;
import com.gedaeusp.domain.Constants;
import com.gedaeusp.domain.UnitValue;
import com.gedaeusp.domain.MolarConcentrationUnit;

public class AnaerobicLacticCalculatorTest {

	@Test	
	public void addAnaerobicLacticToReturnZero()
	{
		UnitValue<MolarConcentrationUnit> minLactic = new UnitValue<MolarConcentrationUnit>(1.0, MolarConcentrationUnit.MiliMolPerLiter);
		UnitValue<MolarConcentrationUnit> maxLactic = new UnitValue<MolarConcentrationUnit>(1.0, MolarConcentrationUnit.MiliMolPerLiter);
		UnitValue<WeightUnit> weight = new UnitValue<WeightUnit>(100.0, WeightUnit.Kg);
		
		AnaerobicLacticCalculator anaerobicLactic = new AnaerobicLacticCalculator();
		UnitValue<EnergyUnit> result = anaerobicLactic.calculate(minLactic, maxLactic, weight);
		assertEquals(0.0, result.getValue(EnergyUnit.Kcal), Constants.LACTIC_EPS);
	}
	
	@Test	
	public void addAnaerobicLacticToReturnProduct()
	{
		UnitValue<MolarConcentrationUnit> minLactic = new UnitValue<MolarConcentrationUnit>(0.7, MolarConcentrationUnit.MiliMolPerLiter);
		UnitValue<MolarConcentrationUnit> maxLactic = new UnitValue<MolarConcentrationUnit>(6.7, MolarConcentrationUnit.MiliMolPerLiter);
		UnitValue<WeightUnit> weight = new UnitValue<WeightUnit>(74, WeightUnit.Kg);
		
		AnaerobicLacticCalculator anaerobicLactic = new AnaerobicLacticCalculator();
		UnitValue<EnergyUnit> result = anaerobicLactic.calculate(minLactic, maxLactic, weight); 
		assertEquals(6.66, result.getValue(EnergyUnit.Kcal), Constants.LACTIC_EPS);
	}
	
	
}
