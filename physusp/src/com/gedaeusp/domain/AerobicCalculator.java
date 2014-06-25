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

import java.util.List;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class AerobicCalculator {

	public UnitValue<VolumeUnit> calculateOxygenConsumptionDuringExercise(List<UnitValue<FlowUnit>> consumptionDuringExercise,
			UnitValue<FlowUnit> restConsumption, List<Integer> time) {
		IntegralCalculator<FlowUnit> integralCalculator = new IntegralCalculator<FlowUnit>(time, consumptionDuringExercise);
		double integralValue = integralCalculator.calculate().getValue(FlowUnit.lPerSecond);
		Integer timeRange = Time.timeRange(time);
		
		double meanDuringExerciseOxygenConsumption = integralValue / timeRange;
		
		if(meanDuringExerciseOxygenConsumption < restConsumption.getValue(FlowUnit.lPerSecond))
			throw new DomainException("Oxygen consumption during exercise mean is less than rest consumption.", "aerobic");
		
		double value = integralValue - (restConsumption.getValue(FlowUnit.lPerSecond)*timeRange);
		return new UnitValue<VolumeUnit>(value,VolumeUnit.l);
	}
	
	public UnitValue<EnergyUnit> calculateEnergyConsumption(List<UnitValue<FlowUnit>> consumptionDuringExercise,
			UnitValue<FlowUnit> restConsumption, List<Integer> time) {
		UnitValue<VolumeUnit> oxygenConsumption = calculateOxygenConsumptionDuringExercise(consumptionDuringExercise,
				restConsumption, time);
		UnitValue<EnergyUnit> result;
		double energy = (oxygenConsumption.getValue(VolumeUnit.l))*5;
		result = new UnitValue<EnergyUnit>(energy,EnergyUnit.Kcal);
		return result;
	}
	
}
