package com.gedaeusp.domain;

public class EnergyValue {

	public static final double Kcal = 4.2;
	public static final double KJ = 1;
	private double value;
	private double unit;

	public EnergyValue(double value, double unit) {
		this.value = value;
		this.unit = unit;
	}

	public double getValue(double expectedUnit) {
		return value * expectedUnit / unit;
	}


}
