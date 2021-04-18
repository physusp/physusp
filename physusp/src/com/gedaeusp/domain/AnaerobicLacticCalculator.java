/*
Copyright 2014 António Miranda, Caio Valente, Igor Topcin, Jorge Melegati, Thales Paiva, Victor Santos

This file is part of PhysUSP.

PhysUSP is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

PhysUSP is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PhysUSP. If not, see <http://www.gnu.org/licenses/>.
*/

package com.gedaeusp.domain;

public class AnaerobicLacticCalculator {

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
