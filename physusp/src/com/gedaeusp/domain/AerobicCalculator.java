package com.gedaeusp.domain;

import java.util.List;

public class AerobicCalculator {

	public static UnitValue<VolumeUnit> integrate(List<UnitValue<FlowUnit>> comsumption, List<Integer> time) {
		double integralValue = 0;
		for (int i = 0; i < comsumption.size() - 1; i++) {
			integralValue += (time.get(i + 1) - time.get(i))
					* (comsumption.get(i).getValue(FlowUnit.lPerSecond) + comsumption.get(i + 1).getValue(FlowUnit.lPerSecond)) / 2.0;
		}
		return new UnitValue<VolumeUnit>(integralValue,VolumeUnit.l);
	}
	

	public static UnitValue<FlowUnit> restComsumption(List<UnitValue<FlowUnit>> comsumption,
			List<Integer> time) {
		double result = 0;
		double comsumptionValue = 0;
		int totalTime = 0;
		for (int i = 0; i < comsumption.size() - 1; i++) {
			totalTime += time.get(i + 1) - time.get(i);
			comsumptionValue += (time.get(i + 1) - time.get(i))
					* (comsumption.get(i).getValue(FlowUnit.lPerSecond) + comsumption.get(i + 1).getValue(FlowUnit.lPerSecond)) / 2.0;
		}
		result = comsumptionValue / totalTime;
		return new UnitValue<FlowUnit>(result,FlowUnit.lPerSecond);
	}

	public static UnitValue<VolumeUnit> calculateAerobicComsumption(List<UnitValue<FlowUnit>> comsumption,
			List<UnitValue<FlowUnit>> restComsumption, List<Integer> time, List<Integer> restTime) {
		double integralValue = integrate(comsumption,time).getValue(VolumeUnit.l);
		double restComsumptionAverage = restComsumption(restComsumption, restTime).getValue(FlowUnit.lPerSecond);
		Integer timeRange = Time.timeRange(time);
		double value = integralValue-(restComsumptionAverage*timeRange);
		return new UnitValue<VolumeUnit>(value,VolumeUnit.l);
	}
	
	public static UnitValue<EnergyUnit> AerobicEnergyComsumption(UnitValue<VolumeUnit> oxygenComsumption, UnitValue<WeightUnit> weight)
	{
		double energy;
		UnitValue<EnergyUnit> result;
		energy = (oxygenComsumption.getValue(VolumeUnit.l)/weight.getValue(WeightUnit.Kg))*5;
		result = new UnitValue<EnergyUnit>(energy,EnergyUnit.Kcal);
		return result;
	}
	
	public static UnitValue<EnergyUnit> AerobicEnergyComsumption(UnitValue<VolumeUnit> oxygenComsumption)
	{
		double energy;
		UnitValue<EnergyUnit> result;
		energy = (oxygenComsumption.getValue(VolumeUnit.l))*5;
		result = new UnitValue<EnergyUnit>(energy,EnergyUnit.Kcal);
		return result;
	}
}
