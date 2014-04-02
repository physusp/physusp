package com.gedaeusp.domain;

public class LactateValue {

	public static final double MiliMolPerLiter = 1;
	private double value;
	private double unit;
	
	
	public LactateValue(double value, double unit) {
		this.value = value;
		this.unit = unit;
	}


	public double getValue(double expectedUnit) {
		return value * expectedUnit / unit;
	}

	

}
