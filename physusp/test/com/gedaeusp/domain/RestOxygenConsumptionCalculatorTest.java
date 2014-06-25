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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class RestOxygenConsumptionCalculatorTest {
	
	private static double EPSILON = 0.01;
	
	@Test
	public void getAverageRestConsumptionTest() {
		List<UnitValue<FlowUnit>> restConsumption = new ArrayList<UnitValue<FlowUnit>>();
		List<Integer> restTime = new ArrayList<Integer>();
		restConsumption.add(new UnitValue<FlowUnit>((double) 400, FlowUnit.lPerSecond));
		restConsumption.add(new UnitValue<FlowUnit>((double) 200, FlowUnit.lPerSecond));
		restConsumption.add(new UnitValue<FlowUnit>((double) 300, FlowUnit.lPerSecond));
		restConsumption.add(new UnitValue<FlowUnit>((double) 500, FlowUnit.lPerSecond));
		restConsumption.add(new UnitValue<FlowUnit>((double) 100, FlowUnit.lPerSecond));
		restTime.add((int) 10);
		restTime.add((int) 12);
		restTime.add((int) 13);
		restTime.add((int) 14);
		restTime.add((int) 19);
		
		RestOxygenConsumptionCalculator restConsumptionCalculator = new RestOxygenConsumptionCalculatorFromSeries(restConsumption, restTime);
	
		assertEquals(305.5555555, restConsumptionCalculator.getAverageRestConsumption().getValue(FlowUnit.lPerSecond), EPSILON);
	}

	@Test
	public void calculateRestAerobicComsumptionTest2() {
		List<UnitValue<FlowUnit>> restConsumption = new ArrayList<UnitValue<FlowUnit>>();
		List<Integer> restTime = new ArrayList<Integer>();
		restConsumption
				.add(new UnitValue<FlowUnit>((double) 866.23, FlowUnit.lPerSecond));
		restConsumption
				.add(new UnitValue<FlowUnit>((double) 392.82, FlowUnit.lPerSecond));
		restConsumption
				.add(new UnitValue<FlowUnit>((double) 562.68, FlowUnit.lPerSecond));
		restConsumption
				.add(new UnitValue<FlowUnit>((double) 737.75, FlowUnit.lPerSecond));
		restConsumption
				.add(new UnitValue<FlowUnit>((double) 534.29, FlowUnit.lPerSecond));
		restTime.add((int) 228);
		restTime.add((int) 232);
		restTime.add((int) 237);
		restTime.add((int) 241);
		restTime.add((int) 246);
		
		RestOxygenConsumptionCalculator restConsumptionCalculator = new RestOxygenConsumptionCalculatorFromSeries(restConsumption, restTime);
		
		assertEquals(restConsumptionCalculator.getAverageRestConsumption()
				.getValue(FlowUnit.lPerSecond), 593.76722222222222, EPSILON);
	}

	@Test
	public void testRepeatedValues() {
		List<UnitValue<FlowUnit>> restConsumption = new ArrayList<UnitValue<FlowUnit>>();
		List<Integer> restTime = new ArrayList<Integer>();
		restConsumption.add(new UnitValue<FlowUnit>(100.0, FlowUnit.lPerSecond));
		restConsumption.add(new UnitValue<FlowUnit>(200.0, FlowUnit.lPerSecond));
		restConsumption.add(new UnitValue<FlowUnit>(300.0, FlowUnit.lPerSecond));
		restTime.add(10);
		restTime.add(11);
		restTime.add(11);
		
		RestOxygenConsumptionCalculator restConsumptionCalculator = new RestOxygenConsumptionCalculatorFromSeries(restConsumption, restTime);
	
		assertEquals(150.0, restConsumptionCalculator.getAverageRestConsumption().getValue(FlowUnit.lPerSecond), EPSILON);
	}
}
