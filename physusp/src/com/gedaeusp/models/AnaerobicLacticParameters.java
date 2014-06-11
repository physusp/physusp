package com.gedaeusp.models;

public class AnaerobicLacticParameters {

	private double restLactateConcentration;
	private double peakLactateConcentration;
	private double weight;
	
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getPeakLactateConcentration() {
		return peakLactateConcentration;
	}
	public void setPeakLactateConcentration(double peakLactateConcentration) {
		this.peakLactateConcentration = peakLactateConcentration;
	}
	public double getRestLactateConcentration() {
		return restLactateConcentration;
	}
	public void setRestLactateConcentration(double restLactateConcentration) {
		this.restLactateConcentration = restLactateConcentration;
	}
	
}
