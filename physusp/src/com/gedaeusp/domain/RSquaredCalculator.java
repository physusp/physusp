package com.gedaeusp.domain;

public class RSquaredCalculator {
	
	public double calculate(double[] observedValues, double[] expectedValues) {
		double observedSquareSum = 0;
		double expectedSquareSum = 0;
		
		double observedMean = calculateMean(observedValues);
		for (int i = 0; i < observedValues.length; i++)
			observedSquareSum += Math.pow((observedValues[i] - observedMean), 2);
		for (int i = 0; i < expectedValues.length; i++)
			expectedSquareSum += Math.pow((expectedValues[i] - observedMean), 2);
		
		return expectedSquareSum/observedSquareSum;
	}
	
	public double calculateMean(double[] values) {
		double mean = 0;
		
		for (double value : values)
			mean += value;
		mean /= values.length;
		
		return mean;
	}
}
