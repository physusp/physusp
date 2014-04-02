package com.gedaeusp.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.caelum.vraptor.restfulie.headers.RestDefaults;

import com.gedaeusp.domain.AerobicCalculator;

public class AerobicTest {

	private static double EPSILON = 0.000000000000000000000000001;

	@Test
	public void integrateTest() {
		List<Double> comsumption = new ArrayList<Double>();
		List<Integer> time = new ArrayList<Integer>();
		comsumption.add((double) 400);
		comsumption.add((double) 800);
		time.add((int) 10);
		time.add((int) 12);
		assertEquals(AerobicCalculator.integrate(comsumption, time), 1200,
				EPSILON);
	}

	@Test
	public void calculateRestAerobicComsumption() {
		List<Double> comsumption = new ArrayList<Double>();
		List<Integer> time = new ArrayList<Integer>();
		comsumption.add((double) 400);
		comsumption.add((double) 200);
		comsumption.add((double) 300);
		comsumption.add((double) 500);
		comsumption.add((double) 100);
		time.add((int) 10);
		time.add((int) 12);
		time.add((int) 13);
		time.add((int) 14);
		time.add((int) 19);
		assertEquals(AerobicCalculator.restComsumption(comsumption, time), 305.55555555555554,
				EPSILON);
	}
	
	@Test
	public void AerobicComsumptionTest() {
		List<Double> comsumption = new ArrayList<Double>();
		List<Double> restComsumption = new ArrayList<Double>();
		List<Integer> time = new ArrayList<Integer>();
		List<Integer> restTime = new ArrayList<Integer>();
		comsumption.add((double) 400);
		comsumption.add((double) 800);
		restComsumption.add((double) 200);
		restComsumption.add((double) 400);
		time.add((int) 10);
		time.add((int) 14);
		restTime.add((int) 1);
		restTime.add((int) 10);
		assertEquals(AerobicCalculator.calculateAerobicComsumption(comsumption,
				restComsumption, time, restTime), 1200, EPSILON);
	}
}
