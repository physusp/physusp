package com.gedaeusp.models;

public class Parameters {

	private double restLactateConcentration;
	private double maxLactateConcentration;
	private double weight;
	private String oxygenConsumptionDuringExercise;
	private double timeDelayPost;
	private String oxygenConsumptionRest;
	private String oxygenConsumptionPostExercise;
	private int exponentialType;
	
	
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getMaxLactateConcentration() {
		return maxLactateConcentration;
	}
	public void setMaxLactateConcentration(double maxLactateConcentration) {
		this.maxLactateConcentration = maxLactateConcentration;
	}
	public double getRestLactateConcentration() {
		return restLactateConcentration;
	}
	public void setRestLactateConcentration(double restLactateConcentration) {
		this.restLactateConcentration = restLactateConcentration;
	}
	public String getOxygenConsumptionDuringExercise() {
		return oxygenConsumptionDuringExercise;
	}
	public void setOxygenConsumptionDuringExercise(String oxygenConsumption) {
		this.oxygenConsumptionDuringExercise = oxygenConsumption;
	}
	public String getOxygenConsumptionRest() {
		return oxygenConsumptionRest;
	}
	public void setOxygenConsumptionRest(String oxygenConsumptionRest) {
		this.oxygenConsumptionRest = oxygenConsumptionRest;
	}
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
