package com.gedaeusp.domain;

public class AnaerobicLactic {

	public UnitValue<EnergyUnit> calculate(UnitValue<MolarConcentrationUnit> minLactic, UnitValue<MolarConcentrationUnit> maxLactic, UnitValue<WeightUnit> weight) {
		
		double minLactatemMPerLiter = minLactic.getValue(MolarConcentrationUnit.MiliMolPerLiter);
		double maxLactatemMPerLiter = maxLactic.getValue(MolarConcentrationUnit.MiliMolPerLiter);
		
		double oxygenEquivalent = (maxLactatemMPerLiter - minLactatemMPerLiter)
									* weight.getValue(WeightUnit.Kg)
									* Constants.LACTATE_TO_OXYGEN;
		
		double energy = oxygenEquivalent * Constants.OXYGEN_TO_KCAL;
		
		return new UnitValue<EnergyUnit>(energy, EnergyUnit.Kcal);
	}


}
