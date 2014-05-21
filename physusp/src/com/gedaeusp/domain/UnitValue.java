package com.gedaeusp.domain;

public class UnitValue<T extends Unit> {
	
	private double value;
	private T unit;

	public UnitValue(double value, T unit) {
		this.value = value;
		this.unit = unit;
	}

	public double getValue(T expectedUnit) {
		return value * expectedUnit.getValue() / unit.getValue();
	}
	
	public double getValue(){
		return value;
	}
	
	public T getUnit(){
		return unit;
	}

	public double getValue(Object expectedUnit) {
		return getValue((T)expectedUnit);
	}
}
