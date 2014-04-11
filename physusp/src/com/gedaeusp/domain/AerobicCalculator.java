package com.gedaeusp.domain;

import java.util.List;

public class AerobicCalculator {
	
	private UnitValue<FlowUnit> averageRestConsumption;
	
	public UnitValue<FlowUnit> getAverageRestConsumption() {
		return averageRestConsumption;
	}
	
	private  UnitValue<VolumeUnit> integrate(List<UnitValue<FlowUnit>> comsumption, List<Integer> time) {
		double integralValue = 0;
		for (int i = 0; i < comsumption.size() - 1; i++) {
			integralValue += (time.get(i + 1) - time.get(i))
					* (comsumption.get(i).getValue(FlowUnit.lPerSecond) + comsumption.get(i + 1).getValue(FlowUnit.lPerSecond)) / 2.0;
		}
		return new UnitValue<VolumeUnit>(integralValue,VolumeUnit.l);
	}

	private void calculateAverageRestComsumption(List<UnitValue<FlowUnit>> consumption,
			List<Integer> time) {
		IntegralCalculator<FlowUnit> integralCalculator = new IntegralCalculator<FlowUnit>(time, consumption);
		double integralValue = integralCalculator.calculate().getValue(FlowUnit.lPerSecond);
		double totalTime = time.get(time.size() - 1) - time.get(0);
		double result = integralValue / totalTime;
		averageRestConsumption = new UnitValue<FlowUnit>(result,FlowUnit.lPerSecond);
	}

	public UnitValue<VolumeUnit> calculateOxygenConsumptionDuringExercise(List<UnitValue<FlowUnit>> consumptionDuringExercise,
			List<UnitValue<FlowUnit>> restConsumption, List<Integer> time, List<Integer> restTime) {
		double integralValue = integrate(consumptionDuringExercise,time).getValue(VolumeUnit.l);
		calculateAverageRestComsumption(restConsumption, restTime);
		Integer timeRange = Time.timeRange(time);
		double value = integralValue - (averageRestConsumption.getValue(FlowUnit.lPerSecond)*timeRange);
		return new UnitValue<VolumeUnit>(value,VolumeUnit.l);
	}
	
	public UnitValue<EnergyUnit> calculateEnergyConsumption(List<UnitValue<FlowUnit>> consumptionDuringExercise,
			List<UnitValue<FlowUnit>> restConsumption, List<Integer> time, List<Integer> restTime, UnitValue<WeightUnit> weight) {
		UnitValue<VolumeUnit> oxygenConsumption = calculateOxygenConsumptionDuringExercise(consumptionDuringExercise,
			restConsumption, time, restTime);
		UnitValue<EnergyUnit> result;
		double energy = (oxygenConsumption.getValue(VolumeUnit.l)/weight.getValue(WeightUnit.Kg))*5;
		result = new UnitValue<EnergyUnit>(energy,EnergyUnit.Kcal);
		return result;
	}
	
	public UnitValue<EnergyUnit> calculateEnergyConsumption(List<UnitValue<FlowUnit>> consumptionDuringExercise,
			List<UnitValue<FlowUnit>> restConsumption, List<Integer> time, List<Integer> restTime) {
		UnitValue<VolumeUnit> oxygenConsumption = calculateOxygenConsumptionDuringExercise(consumptionDuringExercise,
				restConsumption, time, restTime);
		UnitValue<EnergyUnit> result;
		double energy = (oxygenConsumption.getValue(VolumeUnit.l))*5;
		result = new UnitValue<EnergyUnit>(energy,EnergyUnit.Kcal);
		return result;
	}
}
