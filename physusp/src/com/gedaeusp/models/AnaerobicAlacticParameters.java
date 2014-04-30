package com.gedaeusp.models;

public class AnaerobicAlacticParameters {

	private double timeDelayPost;
	private String oxygenConsumptionPostExercise;
	private int exponentialType;
	
	public String getOxygenConsumptionPostExercise() {
		return oxygenConsumptionPostExercise;
	}
	public void setOxygenConsumptionPostExercise(String oxygenConsumptionPost) {
		this.oxygenConsumptionPostExercise = oxygenConsumptionPost;
	}
	public double getTimeDelayPost() {
		return timeDelayPost;
	}
	public void setTimeDelayPost(double timeDelayPost) {
		this.timeDelayPost = timeDelayPost;
	}
	
	public int getExponentialType() {
		return exponentialType;
	}
	public void setExponentialType(int exponentialType) {
		this.exponentialType = exponentialType;
	}
	
}
