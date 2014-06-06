package com.gedaeusp.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class IntegralCalculatorTest {
	
	@Test
	public void calculateTest() {
		List<UnitValue<FlowUnit>> comsumption = new ArrayList<UnitValue<FlowUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		comsumption.add(new UnitValue<FlowUnit>((double) 400, FlowUnit.lPerSecond));
		comsumption.add(new UnitValue<FlowUnit>((double) 800, FlowUnit.lPerSecond));
		time.add((int) 10);
		time.add((int) 12);
		IntegralCalculator<FlowUnit> integralCalculator = new IntegralCalculator<FlowUnit>(time, comsumption);
		assertEquals(1200, integralCalculator.calculate().getValue(FlowUnit.lPerSecond), 0.01);
	}
	
	@Test
	public void testIntegralWithMorePoints() {
		List<UnitValue<FlowUnit>> comsumption = new ArrayList<UnitValue<FlowUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		comsumption.add(new UnitValue<FlowUnit>(0.0, FlowUnit.lPerSecond));
		comsumption.add(new UnitValue<FlowUnit>(100.0, FlowUnit.lPerSecond));
		comsumption.add(new UnitValue<FlowUnit>(200.0, FlowUnit.lPerSecond));
		comsumption.add(new UnitValue<FlowUnit>(300.0, FlowUnit.lPerSecond));
		comsumption.add(new UnitValue<FlowUnit>(300.0, FlowUnit.lPerSecond));
		time.add(0);
		time.add(1);
		time.add(2);
		time.add(3);
		time.add(5);
		IntegralCalculator<FlowUnit> integralCalculator = new IntegralCalculator<FlowUnit>(time, comsumption);
		assertEquals(1050, integralCalculator.calculate().getValue(FlowUnit.lPerSecond), 0.01);
	}	
	
}
