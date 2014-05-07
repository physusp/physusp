package com.gedaeusp.domain;

public class RestOxygenConsumptionCalculatorFixed implements RestOxygenConsumptionCalculator {

	private final UnitValue<FlowUnit> averageRestConsumption;
	
	public RestOxygenConsumptionCalculatorFixed(UnitValue<FlowUnit> averageRestConsumption) {
		this.averageRestConsumption = averageRestConsumption;
	}
	
	public UnitValue<FlowUnit> getAverageRestConsumption() {
		return averageRestConsumption;
	}
	
}
