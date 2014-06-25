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


import org.junit.Before;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

import com.gedaeusp.models.AnaerobicLacticParameters;

public class EnergyConsumptionResponseBuilderTest {

	double EPS = 0.01;
	EnergyConsumptionResponseBuilder energyConsumptionResponseBuilder;
	
	
    @Before
    public void setUp() {      
    	energyConsumptionResponseBuilder = new EnergyConsumptionResponseBuilder();
    	
    }
	
	@Test
	public void addAnaerobicLacticTest() {
		AnaerobicLacticParameters parameters = new AnaerobicLacticParameters();
		parameters.setPeakLactateConcentration(6.7);
		parameters.setRestLactateConcentration(0.7);
		parameters.setWeight(74);
		UnitValue<EnergyUnit> anaerobicLacticEnergy = energyConsumptionResponseBuilder.addAnaerobicLactic(parameters).getResponse().getAnaerobicLactic();
		assertEquals(27.87, anaerobicLacticEnergy.getValue(EnergyUnit.KJ), EPS);
	}
	
}
