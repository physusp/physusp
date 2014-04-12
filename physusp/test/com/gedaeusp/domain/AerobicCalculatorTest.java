package com.gedaeusp.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gedaeusp.domain.AerobicCalculator;

public class AerobicTest {

	private static double EPSILON = 0.01;



	@Test
	public void getAverageRestConsumptionTest() {
		List<UnitValue<FlowUnit>> restConsumption = new ArrayList<UnitValue<FlowUnit>>();
		List<UnitValue<FlowUnit>> consumptionDuringExercise= new ArrayList<UnitValue<FlowUnit>>();
		List<Integer> restTime = new ArrayList<Integer>();
		List<Integer> timeDuringExercise = new ArrayList<Integer>();
		
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
		
		for (int i = 0; i < restTime.size(); i++) {
			consumptionDuringExercise.add(new UnitValue<FlowUnit>((double) i, FlowUnit.lPerMinute));
			timeDuringExercise.add(i);
		}				
				
		AerobicCalculator aerobicCalculator = new AerobicCalculator();
		aerobicCalculator.calculateEnergyConsumption(consumptionDuringExercise, restConsumption, timeDuringExercise, restTime);
		assertEquals(305.5555555, aerobicCalculator.getAverageRestConsumption().getValue(FlowUnit.lPerSecond), EPSILON);
	}

	@Test
	public void calculateRestAerobicComsumptionTest2() {
		List<UnitValue<FlowUnit>> comsumption = new ArrayList<UnitValue<FlowUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		comsumption
				.add(new UnitValue<FlowUnit>((double) 866.23, FlowUnit.lPerSecond));
		comsumption
				.add(new UnitValue<FlowUnit>((double) 392.82, FlowUnit.lPerSecond));
		comsumption
				.add(new UnitValue<FlowUnit>((double) 562.68, FlowUnit.lPerSecond));
		comsumption
				.add(new UnitValue<FlowUnit>((double) 737.75, FlowUnit.lPerSecond));
		comsumption
				.add(new UnitValue<FlowUnit>((double) 534.29, FlowUnit.lPerSecond));
		time.add((int) 228);
		time.add((int) 232);
		time.add((int) 237);
		time.add((int) 241);
		time.add((int) 246);
		
		List<UnitValue<FlowUnit>> consumptionDuringExercise= new ArrayList<UnitValue<FlowUnit>>();
		List<Integer> timeDuringExercise = new ArrayList<Integer>();
		for (int i = 0; i < time.size(); i++) {
			consumptionDuringExercise.add(new UnitValue<FlowUnit>((double) i, FlowUnit.lPerMinute));
			timeDuringExercise.add(i);
		}
		
		AerobicCalculator aerobicCalculator = new AerobicCalculator();
		aerobicCalculator.calculateEnergyConsumption(consumptionDuringExercise, comsumption, timeDuringExercise, time);
		assertEquals(aerobicCalculator.getAverageRestConsumption()
				.getValue(FlowUnit.lPerSecond), 593.76722222222222, EPSILON);
	}

	@Test
	public void aerobicComsumptionTest() {
		List<UnitValue<FlowUnit>> comsumption = new ArrayList<UnitValue<FlowUnit>>();
		List<UnitValue<FlowUnit>> restComsumption = new ArrayList<UnitValue<FlowUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		List<Integer> restTime = new ArrayList<Integer>();
		comsumption.add(new UnitValue<FlowUnit>((double) 400, FlowUnit.lPerSecond));
		comsumption.add(new UnitValue<FlowUnit>((double) 800, FlowUnit.lPerSecond));
		restComsumption.add(new UnitValue<FlowUnit>((double) 200,
				FlowUnit.lPerSecond));
		restComsumption.add(new UnitValue<FlowUnit>((double) 400,
				FlowUnit.lPerSecond));
		time.add((int) 10);
		time.add((int) 14);
		restTime.add((int) 1);
		restTime.add((int) 10);
		
		AerobicCalculator aerobicCalculator = new AerobicCalculator();
		UnitValue<VolumeUnit> oxygenConsumption = aerobicCalculator.calculateOxygenConsumptionDuringExercise(comsumption, restComsumption, time, restTime);
		assertEquals(
				oxygenConsumption.getValue(VolumeUnit.l),
				1200, EPSILON);
	}

	@Test
	public void aerobicEnergyComsumptionTest() {
		List<UnitValue<FlowUnit>> comsumption = new ArrayList<UnitValue<FlowUnit>>();
		List<UnitValue<FlowUnit>> restComsumption = new ArrayList<UnitValue<FlowUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		List<Integer> restTime = new ArrayList<Integer>();
		comsumption.add(new UnitValue<FlowUnit>((double) 2992.44,
				FlowUnit.lPerMinute));
		comsumption.add(new UnitValue<FlowUnit>((double) 2672.82,
				FlowUnit.lPerMinute));
		comsumption.add(new UnitValue<FlowUnit>((double) 2610.53,
				FlowUnit.lPerMinute));
		comsumption.add(new UnitValue<FlowUnit>((double) 2521.70,
				FlowUnit.lPerMinute));
		comsumption.add(new UnitValue<FlowUnit>((double) 2313.76,
				FlowUnit.lPerMinute));
		time.add((int) 1);
		time.add((int) 2);
		time.add((int) 3);
		time.add((int) 4);
		time.add((int) 5);
		restComsumption.add(new UnitValue<FlowUnit>((double) 866.23,
				FlowUnit.lPerMinute));
		restComsumption.add(new UnitValue<FlowUnit>((double) 392.82,
				FlowUnit.lPerMinute));
		restComsumption.add(new UnitValue<FlowUnit>((double) 562.68,
				FlowUnit.lPerMinute));
		restComsumption.add(new UnitValue<FlowUnit>((double) 737.75,
				FlowUnit.lPerMinute));
		restComsumption.add(new UnitValue<FlowUnit>((double) 534.29,
				FlowUnit.lPerMinute));
		restTime.add((int) 228);
		restTime.add((int) 232);
		restTime.add((int) 237);
		restTime.add((int) 241);
		restTime.add((int) 246);

		AerobicCalculator aerobicCalculator = new AerobicCalculator();
		UnitValue<EnergyUnit> energyConsumption = aerobicCalculator.calculateEnergyConsumption(
				comsumption, restComsumption, time, restTime);
		assertEquals(energyConsumption.getValue(EnergyUnit.Kcal), 673.5900925925926, EPSILON);
	}

}
