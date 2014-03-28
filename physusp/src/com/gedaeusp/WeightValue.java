package com.gedaeusp;

public class WeightValue {

	public static final double Kg = 1;
	
	private double value;
	private double unit;

	public WeightValue(double value, double unit) {
		this.value = value;
		this.unit = unit;
	}

	public double getValue(double expectedUnit) {
		return value * expectedUnit / unit;
	}

}
