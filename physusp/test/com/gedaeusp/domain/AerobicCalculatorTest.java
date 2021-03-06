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

import com.gedaeusp.domain.AerobicCalculator;

public class AerobicCalculatorTest {

	private static double EPSILON = 0.01;

	@Test
	public void aerobicComsumptionTest() {
		List<UnitValue<FlowUnit>> consumption = new ArrayList<UnitValue<FlowUnit>>();
		List<UnitValue<FlowUnit>> restConsumption = new ArrayList<UnitValue<FlowUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		List<Integer> restTime = new ArrayList<Integer>();
		consumption.add(new UnitValue<FlowUnit>((double) 400, FlowUnit.lPerSecond));
		consumption.add(new UnitValue<FlowUnit>((double) 800, FlowUnit.lPerSecond));
		restConsumption.add(new UnitValue<FlowUnit>((double) 200,
				FlowUnit.lPerSecond));
		restConsumption.add(new UnitValue<FlowUnit>((double) 400,
				FlowUnit.lPerSecond));
		time.add((int) 10);
		time.add((int) 14);
		restTime.add((int) 1);
		restTime.add((int) 10);
		
		RestOxygenConsumptionCalculator restConsumptionCalculator = new RestOxygenConsumptionCalculatorFromSeries(restConsumption, restTime);
		
		AerobicCalculator aerobicCalculator = new AerobicCalculator();
		UnitValue<VolumeUnit> oxygenConsumption = aerobicCalculator.calculateOxygenConsumptionDuringExercise(consumption, restConsumptionCalculator.getAverageRestConsumption(), time);
		assertEquals(
				oxygenConsumption.getValue(VolumeUnit.l),
				1200, EPSILON);
	}

	@Test
	public void aerobicEnergyComsumptionTest() {
		List<UnitValue<FlowUnit>> consumption = new ArrayList<UnitValue<FlowUnit>>();
		List<UnitValue<FlowUnit>> restConsumption = new ArrayList<UnitValue<FlowUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		List<Integer> restTime = new ArrayList<Integer>();
		consumption.add(new UnitValue<FlowUnit>((double) 2992.44,
				FlowUnit.lPerMinute));
		consumption.add(new UnitValue<FlowUnit>((double) 2672.82,
				FlowUnit.lPerMinute));
		consumption.add(new UnitValue<FlowUnit>((double) 2610.53,
				FlowUnit.lPerMinute));
		consumption.add(new UnitValue<FlowUnit>((double) 2521.70,
				FlowUnit.lPerMinute));
		consumption.add(new UnitValue<FlowUnit>((double) 2313.76,
				FlowUnit.lPerMinute));
		time.add((int) 1);
		time.add((int) 2);
		time.add((int) 3);
		time.add((int) 4);
		time.add((int) 5);
		restConsumption.add(new UnitValue<FlowUnit>((double) 866.23,
				FlowUnit.lPerMinute));
		restConsumption.add(new UnitValue<FlowUnit>((double) 392.82,
				FlowUnit.lPerMinute));
		restConsumption.add(new UnitValue<FlowUnit>((double) 562.68,
				FlowUnit.lPerMinute));
		restConsumption.add(new UnitValue<FlowUnit>((double) 737.75,
				FlowUnit.lPerMinute));
		restConsumption.add(new UnitValue<FlowUnit>((double) 534.29,
				FlowUnit.lPerMinute));
		restTime.add((int) 228);
		restTime.add((int) 232);
		restTime.add((int) 237);
		restTime.add((int) 241);
		restTime.add((int) 246);

		RestOxygenConsumptionCalculator restConsumptionCalculator = new RestOxygenConsumptionCalculatorFromSeries(restConsumption, restTime);
		
		AerobicCalculator aerobicCalculator = new AerobicCalculator();
		UnitValue<EnergyUnit> energyConsumption = aerobicCalculator.calculateEnergyConsumption(
				consumption, restConsumptionCalculator.getAverageRestConsumption(), time);
		assertEquals(energyConsumption.getValue(EnergyUnit.Kcal), 673.5900925925926, EPSILON);
	}
	
	@Test
	public void aerobicEnergyComsumptionWithFixedRestConsumptionTest() {
		List<UnitValue<FlowUnit>> consumption = new ArrayList<UnitValue<FlowUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		consumption.add(new UnitValue<FlowUnit>((double) 2992.44,
				FlowUnit.lPerMinute));
		consumption.add(new UnitValue<FlowUnit>((double) 2672.82,
				FlowUnit.lPerMinute));
		consumption.add(new UnitValue<FlowUnit>((double) 2610.53,
				FlowUnit.lPerMinute));
		consumption.add(new UnitValue<FlowUnit>((double) 2521.70,
				FlowUnit.lPerMinute));
		consumption.add(new UnitValue<FlowUnit>((double) 2313.76,
				FlowUnit.lPerMinute));
		time.add((int) 1);
		time.add((int) 2);
		time.add((int) 3);
		time.add((int) 4);
		time.add((int) 5);

		UnitValue<FlowUnit> restConsumption = new UnitValue<FlowUnit>(593.76722222222222, FlowUnit.lPerMinute);
		
		AerobicCalculator aerobicCalculator = new AerobicCalculator();
		UnitValue<EnergyUnit> energyConsumption = aerobicCalculator.calculateEnergyConsumption(
				consumption, restConsumption, time);
		assertEquals(energyConsumption.getValue(EnergyUnit.Kcal), 673.5900925925926, EPSILON);
	}

}
