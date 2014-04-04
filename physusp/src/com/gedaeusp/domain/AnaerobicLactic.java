package com.gedaeusp.domain;

public class AnaerobicLactic {

	public UnitValue calculate(MolarConcentrationValue minLactic, MolarConcentrationValue maxLactic, UnitValue weight) {
		
		double minLactatemMPerLiter = minLactic.getValue(MolarConcentrationValue.MiliMolPerLiter);
		double maxLactatemMPerLiter = maxLactic.getValue(MolarConcentrationValue.MiliMolPerLiter);
		
		double oxygenEquivalent = (maxLactatemMPerLiter - minLactatemMPerLiter)
									* weight.getValue("Kg")
									* Constants.LACTATE_TO_OXYGEN;
		
		double energy = oxygenEquivalent * Constants.OXYGEN_TO_KCAL;
		
		return new UnitValue(energy, "Kcal");
	}


}
