package com.gedaeusp.domain;

import java.util.List;

public class IntegralCalculator<T extends Unit> {
	
	private List<Integer> domainPoints; 
	private List<UnitValue<T>> imagePoints;
	
	public IntegralCalculator(List<Integer> domainPoints, List<UnitValue<T>> imagePoints) {
		this.domainPoints = domainPoints;
		this.imagePoints = imagePoints;
	}
	
	public UnitValue<T> calculate() {
		T unit = imagePoints.get(0).getUnit();
		double value = 0;
		for (int i = 0; i < domainPoints.size() - 1; i++)
			value += (domainPoints.get(i + 1) - domainPoints.get(i)) *
					 (imagePoints.get(i).getValue(unit) + imagePoints.get(i + 1).getValue(unit)) / 2.0;		
		return new UnitValue<T>(value, unit);
	}
	
}
