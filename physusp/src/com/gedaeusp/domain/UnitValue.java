package com.gedaeusp.domain;

import java.util.Hashtable;



public class UnitValue {

	private double value;
	private String unit;
	
	private final Hashtable<String, Double> units = new Hashtable<String, Double>();

	

	public UnitValue(double value, String unit) {
		this.value = value;
		this.unit = unit;
	    units.put("Kg", (Double) 1000.0);
	    units.put("g", (Double) 1.0);
	    units.put("l", (Double) 1000.0);
	    units.put("ml", (Double) 1.0);
	    units.put("Kcal", (Double) 1.0);
	    units.put("KJ", (Double) 20.9);

	}

	public double getValue(String expectedUnit) {
		return value * units.get(unit) / units.get(expectedUnit);
	}


}
