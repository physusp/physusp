package com.gedaeusp.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gedaeusp.domain.AerobicCalculator;

public class AerobicTest {

	private static double EPSILON = 0.000000000001;

	@Test
	public void integrateTest() {
		List<UnitValue<VolumeUnit>> comsumption = new ArrayList<UnitValue<VolumeUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		comsumption.add(new UnitValue<VolumeUnit>((double) 400, VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 800, VolumeUnit.l));
		time.add((int) 10);
		time.add((int) 12);
		assertEquals(
				AerobicCalculator.integrate(comsumption, time).getValue(
						VolumeUnit.l), 1200, EPSILON);

	}

	@Test
	public void integrateTest2() {
		List<UnitValue<VolumeUnit>> comsumption = new ArrayList<UnitValue<VolumeUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		comsumption.add(new UnitValue<VolumeUnit>((double) 2992.44,
				VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 2672.82,
				VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 2610.53,
				VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 2521.70,
				VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 2313.76,
				VolumeUnit.l));
		time.add((int) 1);
		time.add((int) 2);
		time.add((int) 3);
		time.add((int) 4);
		time.add((int) 5);
		assertEquals(
				AerobicCalculator.integrate(comsumption, time).getValue(
						VolumeUnit.l), 10458.15, EPSILON);
	}

	@Test
	public void calculateRestAerobicComsumptionTest() {
		List<UnitValue<VolumeUnit>> comsumption = new ArrayList<UnitValue<VolumeUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		comsumption.add(new UnitValue<VolumeUnit>((double) 400, VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 200, VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 300, VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 500, VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 100, VolumeUnit.l));
		time.add((int) 10);
		time.add((int) 12);
		time.add((int) 13);
		time.add((int) 14);
		time.add((int) 19);
		assertEquals(AerobicCalculator.restComsumption(comsumption, time)
				.getValue(VolumeUnit.l), 305.55555555555554, EPSILON);
	}

	@Test
	public void calculateRestAerobicComsumptionTest2() {
		List<UnitValue<VolumeUnit>> comsumption = new ArrayList<UnitValue<VolumeUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		comsumption
				.add(new UnitValue<VolumeUnit>((double) 866.23, VolumeUnit.l));
		comsumption
				.add(new UnitValue<VolumeUnit>((double) 392.82, VolumeUnit.l));
		comsumption
				.add(new UnitValue<VolumeUnit>((double) 592.68, VolumeUnit.l));
		comsumption
				.add(new UnitValue<VolumeUnit>((double) 737.75, VolumeUnit.l));
		comsumption
				.add(new UnitValue<VolumeUnit>((double) 534.29, VolumeUnit.l));
		time.add((int) 228);
		time.add((int) 232);
		time.add((int) 237);
		time.add((int) 241);
		time.add((int) 246);
		assertEquals(AerobicCalculator.restComsumption(comsumption, time)
				.getValue(VolumeUnit.l), 601.267222222222222, EPSILON);
	}

	@Test
	public void AerobicComsumptionTest() {
		List<UnitValue<VolumeUnit>> comsumption = new ArrayList<UnitValue<VolumeUnit>>();
		List<UnitValue<VolumeUnit>> restComsumption = new ArrayList<UnitValue<VolumeUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		List<Integer> restTime = new ArrayList<Integer>();
		comsumption.add(new UnitValue<VolumeUnit>((double) 400, VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 800, VolumeUnit.l));
		restComsumption.add(new UnitValue<VolumeUnit>((double) 200,
				VolumeUnit.l));
		restComsumption.add(new UnitValue<VolumeUnit>((double) 400,
				VolumeUnit.l));
		time.add((int) 10);
		time.add((int) 14);
		restTime.add((int) 1);
		restTime.add((int) 10);
		assertEquals(
				AerobicCalculator.calculateAerobicComsumption(comsumption,
						restComsumption, time, restTime).getValue(VolumeUnit.l),
				1200, EPSILON);
	}
/*
	@Test
	public void AerobicEnergyComsumptionTest() {
		List<UnitValue<VolumeUnit>> comsumption = new ArrayList<UnitValue<VolumeUnit>>();
		List<UnitValue<VolumeUnit>> restComsumption = new ArrayList<UnitValue<VolumeUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		List<Integer> restTime = new ArrayList<Integer>();
		UnitValue<VolumeUnit> oxygenComsumption;
		double energy;
		comsumption.add(new UnitValue<VolumeUnit>((double) 2992.44,
				VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 2672.82,
				VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 2610.53,
				VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 2521.70,
				VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 2313.76,
				VolumeUnit.l));
		time.add((int) 1);
		time.add((int) 2);
		time.add((int) 3);
		time.add((int) 4);
		time.add((int) 5);
		restComsumption.add(new UnitValue<VolumeUnit>((double) 866.23,
				VolumeUnit.l));
		restComsumption.add(new UnitValue<VolumeUnit>((double) 392.82,
				VolumeUnit.l));
		restComsumption.add(new UnitValue<VolumeUnit>((double) 592.68,
				VolumeUnit.l));
		restComsumption.add(new UnitValue<VolumeUnit>((double) 737.75,
				VolumeUnit.l));
		restComsumption.add(new UnitValue<VolumeUnit>((double) 534.29,
				VolumeUnit.l));
		restTime.add((int) 228);
		restTime.add((int) 232);
		restTime.add((int) 237);
		restTime.add((int) 241);
		restTime.add((int) 246);

		double OxygenRest = AerobicCalculator.restComsumption(restComsumption,
				restTime).getValue(VolumeUnit.l);
		double OxygenActivity = AerobicCalculator.integrate(comsumption, time)
				.getValue(VolumeUnit.l);

		oxygenComsumption = AerobicCalculator.calculateAerobicComsumption(
				comsumption, restComsumption, time, restTime);
		energy = AerobicCalculator.AerobicEnergyComsumption(oxygenComsumption)
				.getValue(EnergyUnit.Kcal);
		assertEquals(energy, 1200, EPSILON);
	}
*/
}
