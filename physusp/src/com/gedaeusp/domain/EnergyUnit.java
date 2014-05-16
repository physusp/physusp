package com.gedaeusp.domain;

public enum EnergyUnit implements Unit
{
	Kcal(1.0),
	KJ(4.184),
	LO2(5);
	
	private double value;
	
	EnergyUnit(double value){
		this.setValue(value);
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
