package com.gedaeusp.models;

public class Parameters {

	private boolean calculateAerobic;
	private boolean calculateAnaerobicLactic;
	private boolean calculateAnaerobicAlactic;
	
	public boolean getCalculateAnaerobicAlactic() {
		return calculateAnaerobicAlactic;
	}
	public void setCalculateAnaerobicAlactic(boolean calculateAnaerobicAlactic) {
		this.calculateAnaerobicAlactic = calculateAnaerobicAlactic;
	}
	public boolean getCalculateAnaerobicLactic() {
		return calculateAnaerobicLactic;
	}
	public void setCalculateAnaerobicLactic(boolean calculateAnaerobicLactic) {
		this.calculateAnaerobicLactic = calculateAnaerobicLactic;
	}
	public boolean getCalculateAerobic() {
		return calculateAerobic;
	}
	public void setCalculateAerobic(boolean calculateAerobic) {
		this.calculateAerobic = calculateAerobic;
	}
}
