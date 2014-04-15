package com.gedaeusp.domain;

import java.util.List;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class AerobicCalculator {
	
	private UnitValue<FlowUnit> averageRestConsumption;
	
	private void calculateAverageRestComsumption(List<UnitValue<FlowUnit>> consumption,
			List<Integer> time) {
		IntegralCalculator<FlowUnit> integralCalculator = new IntegralCalculator<FlowUnit>(time, consumption);
		double integralValue = integralCalculator.calculate().getValue(FlowUnit.lPerSecond);
		double totalTime = time.get(time.size() - 1) - time.get(0);
		double result = integralValue / totalTime;
		setAverageRestConsumption(new UnitValue<FlowUnit>(result,FlowUnit.lPerSecond));;
	}

	public UnitValue<VolumeUnit> calculateOxygenConsumptionDuringExercise(List<UnitValue<FlowUnit>> consumptionDuringExercise,
			List<UnitValue<FlowUnit>> restConsumption, List<Integer> time, List<Integer> restTime) {
		IntegralCalculator<FlowUnit> integralCalculator = new IntegralCalculator<FlowUnit>(time, consumptionDuringExercise);
		double integralValue = integralCalculator.calculate().getValue(FlowUnit.lPerSecond);
		calculateAverageRestComsumption(restConsumption, restTime);
		Integer timeRange = Time.timeRange(time);
		double value = integralValue - (getAverageRestConsumption().getValue(FlowUnit.lPerSecond)*timeRange);
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

	public UnitValue<FlowUnit> getAverageRestConsumption() {
		return averageRestConsumption;
	}

	public void setAverageRestConsumption(UnitValue<FlowUnit> averageRestConsumption) {
		this.averageRestConsumption = averageRestConsumption;
	}
	
}
