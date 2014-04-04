package com.gedaeusp.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gedaeusp.domain.AerobicCalculator;

public class AerobicTest {

	private static double EPSILON = 0.000000000000000000000000001;

	@Test
	public void integrateTest() {
		List<UnitValue<VolumeUnit>> comsumption = new ArrayList<UnitValue<VolumeUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		comsumption.add(new UnitValue<VolumeUnit>((double) 400,VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 800,VolumeUnit.l));
		time.add((int) 10);
		time.add((int) 12);
		assertEquals(AerobicCalculator.integrate(comsumption, time).getValue(VolumeUnit.l), 1200,
				EPSILON);
	}

	@Test
	public void calculateRestAerobicComsumption() {
		List<UnitValue<VolumeUnit>> comsumption = new ArrayList<UnitValue<VolumeUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		comsumption.add(new UnitValue<VolumeUnit>((double) 400,VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 200,VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 300,VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 500,VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 100,VolumeUnit.l));
		time.add((int) 10);
		time.add((int) 12);
		time.add((int) 13);
		time.add((int) 14);
		time.add((int) 19);
		assertEquals(AerobicCalculator.restComsumption(comsumption, time).getValue(VolumeUnit.l), 305.55555555555554,
				EPSILON);
	}
	
	@Test
	public void AerobicComsumptionTest() {
		List<UnitValue<VolumeUnit>> comsumption = new ArrayList<UnitValue<VolumeUnit>>();
		List<UnitValue<VolumeUnit>> restComsumption = new ArrayList<UnitValue<VolumeUnit>>();
		List<Integer> time = new ArrayList<Integer>();
		List<Integer> restTime = new ArrayList<Integer>();
		comsumption.add(new UnitValue<VolumeUnit>((double) 400,VolumeUnit.l));
		comsumption.add(new UnitValue<VolumeUnit>((double) 800,VolumeUnit.l));
		restComsumption.add(new UnitValue<VolumeUnit>((double) 200,VolumeUnit.l));
		restComsumption.add(new UnitValue<VolumeUnit>((double) 400,VolumeUnit.l));
		time.add((int) 10);
		time.add((int) 14);
		restTime.add((int) 1);
		restTime.add((int) 10);
		assertEquals(AerobicCalculator.calculateAerobicComsumption(comsumption,
				restComsumption, time, restTime).getValue(VolumeUnit.l), 1200, EPSILON);
	}
}
