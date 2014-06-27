package com.gedaeusp.domain;

import org.apache.commons.math3.util.FastMath;

public class SquaredErrorsCalculator {
	
	public static double calculate(double[] expectedValues, double[] observedValues) {
		double sum = 0;
		
		for (int i = 0; i < expectedValues.length; i++)
			sum += FastMath.pow(expectedValues[i] - observedValues[i], 2);
		return sum;
	}
	
}
