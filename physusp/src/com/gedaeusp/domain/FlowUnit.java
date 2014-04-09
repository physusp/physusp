package com.gedaeusp.domain;

public enum FlowUnit implements Unit {

	mlPerSecond(1000.0),
	lPerSecond(1.0),
	mlPerMinute(60000.0),
	lPerMinute(60.0);
	
	
	private double value;
	
	FlowUnit(double value){
		this.setValue(value);
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
