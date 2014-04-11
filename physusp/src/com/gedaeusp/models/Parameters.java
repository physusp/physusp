package com.gedaeusp.models;

public class Parameters {

	private double restLactateConcentration;
	private double maxLactateConcentration;
	private double weight;
	private String oxygenConsumption;
	private String oxygenConsumptionRest;
	private String oxygenConsumptionPost;
	
	
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
	public String getOxygenConsumption() {
		return oxygenConsumption;
	}
	public void setOxygenConsumption(String oxygenConsumption) {
		this.oxygenConsumption = oxygenConsumption;
	}
	public String getOxygenConsumptionRest() {
		return oxygenConsumptionRest;
	}
	public void setOxygenConsumptionRest(String oxygenConsumptionRest) {
		this.oxygenConsumptionRest = oxygenConsumptionRest;
	}
	public String getOxygenConsumptionPost() {
		return oxygenConsumptionPost;
	}
	public void setOxygenConsumptionPost(String oxygenConsumptionPost) {
		this.oxygenConsumptionPost = oxygenConsumptionPost;
	}
	
	
}
