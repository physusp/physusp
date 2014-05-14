package com.gedaeusp.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RSquaredCalculatorTest {
	static double EPS = 0.001;
	@Test
	public void testCalculate() {
		RSquaredCalculator rSquaredCalculator = new RSquaredCalculator();
		
		double x[] = new double[]{1, 2, 3, 4, 5};
		double yObserved[] = new double[]{1.9, 3.7, 5.8, 8.0, 9.6};
		double yExpected[] = new double[yObserved.length];
		for (int i = 0; i < x.length; i++)
			yExpected[i] = -0.11 + 1.97 * x[i]; 
		assertEquals(0.997, rSquaredCalculator.calculate(yObserved, yExpected), EPS);
	}
	
	@Test
	public void testCalculateMean() {
		RSquaredCalculator rSquaredCalculator = new RSquaredCalculator();
		
		double x[] = new double[]{1.2, 22.4, 5.2, 24.2, 5.3};
		assertEquals(11.66, rSquaredCalculator.calculateMean(x), EPS);
	}
	
}
