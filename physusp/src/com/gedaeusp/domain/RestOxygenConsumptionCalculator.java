package com.gedaeusp.domain;

public interface RestOxygenConsumptionCalculator {

	public abstract UnitValue<FlowUnit> getAverageRestConsumption();

}