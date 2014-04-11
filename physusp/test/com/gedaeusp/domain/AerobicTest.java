package com.gedaeusp.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gedaeusp.domain.AerobicCalculator;

public class AerobicTest {

	private static double EPSILON = 0.000000000001;



	@Test
	public void calculateRestAerobicComsumptionTest() {
		List<UnitValue<FlowUnit>> comsumption = new ArrayList<UnitValue<FlowUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		comsumption.add(new UnitValue<FlowUnit>((double) 400, FlowUnit.lPerSecond));
		comsumption.add(new UnitValue<FlowUnit>((double) 200, FlowUnit.lPerSecond));
		comsumption.add(new UnitValue<FlowUnit>((double) 300, FlowUnit.lPerSecond));
		comsumption.add(new UnitValue<FlowUnit>((double) 500, FlowUnit.lPerSecond));
		comsumption.add(new UnitValue<FlowUnit>((double) 100, FlowUnit.lPerSecond));
		time.add((int) 10);
		time.add((int) 12);
		time.add((int) 13);
		time.add((int) 14);
		time.add((int) 19);
		assertEquals(AerobicCalculator.averageRestComsumption(comsumption, time)
				.getValue(FlowUnit.lPerSecond), 305.55555555555554, EPSILON);
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
		assertEquals(AerobicCalculator.averageRestComsumption(comsumption, time)
				.getValue(FlowUnit.lPerSecond), 593.76722222222222, EPSILON);
	}

	@Test
	public void AerobicComsumptionTest() {
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
		assertEquals(
				AerobicCalculator.calculateAerobicComsumption(comsumption,
						restComsumption, time, restTime).getValue(VolumeUnit.l),
				1200, EPSILON);
	}

	@Test
	public void AerobicEnergyComsumptionTest() {
		List<UnitValue<FlowUnit>> comsumption = new ArrayList<UnitValue<FlowUnit>>();
		List<UnitValue<FlowUnit>> restComsumption = new ArrayList<UnitValue<FlowUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		List<Integer> restTime = new ArrayList<Integer>();
		UnitValue<VolumeUnit> oxygenComsumption;
		double energy;
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

		oxygenComsumption = AerobicCalculator.calculateAerobicComsumption(
				comsumption, restComsumption, time, restTime);
		energy = AerobicCalculator.AerobicEnergyComsumption(oxygenComsumption)
				.getValue(EnergyUnit.Kcal);
		assertEquals(energy, 673.5900925925926, EPSILON);
	}

}
