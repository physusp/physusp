package com.gedaeusp;

import java.util.List;

public class AerobicCalculator {

	public static double integrate(List<Double> comsumption, List<Long> time) {
		double integralValue = 0;
		for(int i = 0; i < comsumption.size()-1; i++)
		{
			integralValue += (time.get(i+1)-time.get(i))*(comsumption.get(i)+comsumption.get(i+1))/2.0;
		}
		return integralValue;
	}

	
}
