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
		parameters.setMaxLactateConcentration(6.7);
		parameters.setRestLactateConcentration(0.7);
		parameters.setWeight(74);
		UnitValue<EnergyUnit> anaerobicLacticEnergy = energyConsumptionResponseBuilder.addAnaerobicLactic(parameters).getResponse().getAnaerobicLactic();
		assertEquals(27.87, anaerobicLacticEnergy.getValue(EnergyUnit.KJ), EPS);
	}
	
}
