package com.gedaeusp.domain;

public class AnaerobicLactic {

	public EnergyValue calculate(LactateValue minLactic, LactateValue maxLactic, WeightValue weight) {
		
		double minLactatemMPerLiter = minLactic.getValue(LactateValue.MiliMolPerLiter);
		double maxLactatemMPerLiter = maxLactic.getValue(LactateValue.MiliMolPerLiter);
		
		double oxygenEquivalent = (maxLactatemMPerLiter - minLactatemMPerLiter)
									* weight.getValue(WeightValue.Kg)
									* Constants.LACTATE_TO_OXYGEN;
		
		double energy = oxygenEquivalent * Constants.OXYGEN_TO_KCAL;
		
		return new EnergyValue(energy, EnergyValue.Kcal);
	}


}
