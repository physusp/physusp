package com.gedaeusp;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AerobicTest {

	private static double EPSILON = 0.000000000000000000000000001;

	@Test
	public void timeConverterTest() throws ParseException {
		assertEquals(TimeConverter.convertDateToSeconds("02:01:24"), 7284);
	}

	@Test
	public void aerobicCalculatorTest() {
		assert (false);
	}

	@Test
	public void calculateAerobicConsumptionTest() {
		List<Double> Comsumption = new ArrayList<Double>();
		List<Long> Time = new ArrayList<Long>();
		Comsumption.add((double) 400);
		Comsumption.add((double) 800);
		Time.add((long) 10);
		Time.add((long) 12);
		assertEquals(AerobicCalculator.integrate(Comsumption, Time), 1200,
				EPSILON);
	}

}
