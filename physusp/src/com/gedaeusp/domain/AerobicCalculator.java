package com.gedaeusp.domain;

import java.util.List;

public class AerobicCalculator {

	public static double integrate(List<Double> comsumption, List<Integer> time) {
		double integralValue = 0;
		for (int i = 0; i < comsumption.size() - 1; i++) {
			integralValue += (time.get(i + 1) - time.get(i))
					* (comsumption.get(i) + comsumption.get(i + 1)) / 2.0;
		}
		return integralValue;
	}
	

	public static double restComsumption(List<Double> comsumption,
			List<Integer> time) {
		double result = 0;
		double comsumptionValue = 0;
		int totalTime = 0;
		for (int i = 0; i < comsumption.size() - 1; i++) {
			totalTime += time.get(i + 1) - time.get(i);
			comsumptionValue += (time.get(i + 1) - time.get(i))
					* (comsumption.get(i) + comsumption.get(i + 1)) / 2.0;
		}
		result = comsumptionValue / totalTime;
		return result;
	}

	public static double calculateAerobicComsumption(List<Double> comsumption,
			List<Double> restComsumption, List<Integer> time, List<Integer> restTime) {
		double integralValue = integrate(comsumption,time);
		double restComsumptionAverage = restComsumption(restComsumption, restTime);
		Integer timeRange = Time.timeRange(time);
		double value = integralValue-(restComsumptionAverage*timeRange);
		return value;
	}
	
	public static double AerobicEnergyComsumption(double oxygenComsumption, double mass)
	{
		double energy;
		energy = (oxygenComsumption/mass*1000)*20.9;
		return energy;
	}

}
