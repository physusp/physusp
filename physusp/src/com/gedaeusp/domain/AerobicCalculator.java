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
		double value = integralValue - (restConsumption.getValue(FlowUnit.lPerSecond)*timeRange);
		return new UnitValue<VolumeUnit>(value,VolumeUnit.l);
	}
	
	public UnitValue<EnergyUnit> calculateEnergyConsumption(List<UnitValue<FlowUnit>> consumptionDuringExercise,
			UnitValue<FlowUnit> restConsumption, List<Integer> time, List<Integer> restTime, UnitValue<WeightUnit> weight) {
		UnitValue<VolumeUnit> oxygenConsumption = calculateOxygenConsumptionDuringExercise(consumptionDuringExercise,
			restConsumption, time);
		UnitValue<EnergyUnit> result;
		double energy = (oxygenConsumption.getValue(VolumeUnit.l)/weight.getValue(WeightUnit.Kg))*5;
		result = new UnitValue<EnergyUnit>(energy,EnergyUnit.Kcal);
		return result;
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
