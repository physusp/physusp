package com.gedaeusp.domain;

public enum EnergyUnit implements Unit
{
	Kcal(4.2),
	KJ(1);
	
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
