package com.gedaeusp.models;

public class Parameters {

	private double restLactateConcentration;
	private double maxLactateConcentration;
	private double weight;
	private String oxygenConsumptionDuringExercise;
	private String oxygenConsumptionRest;
	private String oxygenConsumptionPostExercise;
	
	
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
	
	
}
