package com.gedaeusp.domain;

public enum MolarConcentrationUnit implements Unit {

	MiliMolPerLiter(1.0);

	private double value;
	
	MolarConcentrationUnit(double value){
		this.setValue(value);
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
