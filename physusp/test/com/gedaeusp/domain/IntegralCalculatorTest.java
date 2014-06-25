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
