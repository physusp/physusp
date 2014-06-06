package com.gedaeusp.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RestOxygenConsumptionCalculatorFixedTest {
	
	double EPS = 0.001;
	
	@Test
	public void restOxygenConsumptionCalculatorFixedTest() {
		UnitValue<FlowUnit> averageRestConsumption = new UnitValue<FlowUnit>(12, FlowUnit.lPerMinute); 
		RestOxygenConsumptionCalculatorFixed restOxygenConsumptionCalculatorFixed =
				new RestOxygenConsumptionCalculatorFixed(averageRestConsumption);
		assertEquals(averageRestConsumption.getValue(FlowUnit.lPerMinute),
				restOxygenConsumptionCalculatorFixed.getAverageRestConsumption().getValue(FlowUnit.lPerMinute),
				EPS);
	}
	
	
}
