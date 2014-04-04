package com.gedaeusp.domain;

public enum WeightUnit implements Unit {

	Kg(1.0),
	g(1000.0);
	
	private double value;
	
	WeightUnit(double value){
		this.setValue(value);
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
