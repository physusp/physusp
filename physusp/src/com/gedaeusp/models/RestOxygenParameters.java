package com.gedaeusp.models;

public class RestOxygenParameters {

	private String oxygenRestConsumption;
	private String calculateMethod;
	private double fixedValue;
	
	public String getOxygenConsumptionRest() {
		return oxygenRestConsumption;
	}
	public void setOxygenConsumptionRest(String oxygenConsumptionRest) {
		this.oxygenRestConsumption = oxygenConsumptionRest;
	}
	public String getCalculateMethod() {
		return calculateMethod;
	}
	public void setCalculateMethod(String calculateMethod) {
		this.calculateMethod = calculateMethod;
	}
	public double getFixedValue() {
		return fixedValue;
	}
	public void setFixedValue(double fixedValue) {
		this.fixedValue = fixedValue;
	}
	
}
