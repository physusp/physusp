package com.gedaeusp.models;

public class AnaerobicAlacticParameters {

	private double timeDelay;
	private String oxygenConsumptionPostExercise;
	private int exponentialType;
	private boolean useTimeDelay;
	
	public boolean isUseTimeDelay() {
		return useTimeDelay;
	}
	public void setUseTimeDelay(boolean useTimeDelay) {
		this.useTimeDelay = useTimeDelay;
	}	
	public String getOxygenConsumptionPostExercise() {
		return oxygenConsumptionPostExercise;
	}
	public void setOxygenConsumptionPostExercise(String oxygenConsumptionPost) {
		this.oxygenConsumptionPostExercise = oxygenConsumptionPost;
	}
	public double getTimeDelay() {
		return timeDelay;
	}
	public void setTimeDelay(double timeDelayPost) {
		this.timeDelay = timeDelayPost;
	}
	
	public int getExponentialType() {
		return exponentialType;
	}
	public void setExponentialType(int exponentialType) {
		this.exponentialType = exponentialType;
	}
	
}
