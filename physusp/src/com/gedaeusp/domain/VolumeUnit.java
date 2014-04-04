package com.gedaeusp.domain;

public enum VolumeUnit implements Unit {

	l(1000.0),
	ml(1.0);
	
	private double value;
	
	VolumeUnit(double value){
		this.setValue(value);
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
