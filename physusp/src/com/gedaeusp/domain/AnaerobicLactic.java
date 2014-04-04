package com.gedaeusp.domain;

public class AnaerobicLactic {

	public EnergyValue calculate(MolarConcentrationValue minLactic, MolarConcentrationValue maxLactic, WeightValue weight) {
		
		double minLactatemMPerLiter = minLactic.getValue(MolarConcentrationValue.MiliMolPerLiter);
		double maxLactatemMPerLiter = maxLactic.getValue(MolarConcentrationValue.MiliMolPerLiter);
		
		double oxygenEquivalent = (maxLactatemMPerLiter - minLactatemMPerLiter)
									* weight.getValue(WeightValue.Kg)
									* Constants.LACTATE_TO_OXYGEN;
		
		double energy = oxygenEquivalent * Constants.OXYGEN_TO_KCAL;
		
		return new EnergyValue(energy, EnergyValue.Kcal);
	}


}
