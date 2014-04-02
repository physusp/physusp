package com.gedaeusp.models;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("consumption")
public class EnergyConsumption implements Serializable {

	@XStreamAlias("aerobic")
	private double aerobic;
	
	@XStreamAlias("anaerobicLactic")
	private double anaerobicLactic;
	
	@XStreamAlias("anaerobicAlactic")
	private double anaerobicAlactic;
	
	public double getAerobic() {
		return aerobic;
	}
	public void setAerobic(double aerobic) {
		this.aerobic = aerobic;
	}
	public double getAnaerobicLactic() {
		return anaerobicLactic;
	}
	public void setAnaerobicLactic(double anaerobicLactic) {
		this.anaerobicLactic = anaerobicLactic;
	}
	public double getAnaerobicAlactic() {
		return anaerobicAlactic;
	}
	public void setAnaerobicAlactic(double anaerobicAlactic) {
		this.anaerobicAlactic = anaerobicAlactic;
	}
	
	
	
}
