package com.gedaeusp.domain;

import java.util.List;

public class AerobicCalculator {

	public static UnitValue<VolumeUnit> integrate(List<UnitValue<VolumeUnit>> comsumption, List<Integer> time) {
		double integralValue = 0;
		for (int i = 0; i < comsumption.size() - 1; i++) {
			integralValue += (time.get(i + 1) - time.get(i))
					* (comsumption.get(i).getValue(VolumeUnit.l) + comsumption.get(i + 1).getValue(VolumeUnit.l)) / 2.0;
		}
		return new UnitValue<VolumeUnit>(integralValue,VolumeUnit.l);
	}
	

	public static UnitValue<VolumeUnit> restComsumption(List<UnitValue<VolumeUnit>> comsumption,
			List<Integer> time) {
		double result = 0;
		double comsumptionValue = 0;
		int totalTime = 0;
		for (int i = 0; i < comsumption.size() - 1; i++) {
			totalTime += time.get(i + 1) - time.get(i);
			comsumptionValue += (time.get(i + 1) - time.get(i))
					* (comsumption.get(i).getValue(VolumeUnit.l) + comsumption.get(i + 1).getValue(VolumeUnit.l)) / 2.0;
		}
		result = comsumptionValue / totalTime;
		return new UnitValue<VolumeUnit>(result,VolumeUnit.l);
	}

	public static UnitValue<VolumeUnit> calculateAerobicComsumption(List<UnitValue<VolumeUnit>> comsumption,
			List<UnitValue<VolumeUnit>> restComsumption, List<Integer> time, List<Integer> restTime) {
		double integralValue = integrate(comsumption,time).getValue(VolumeUnit.l);
		double restComsumptionAverage = restComsumption(restComsumption, restTime).getValue(VolumeUnit.l);
		Integer timeRange = Time.timeRange(time);
		double value = integralValue-(restComsumptionAverage*timeRange);
		return new UnitValue<VolumeUnit>(value,VolumeUnit.l);
	}
	
	public static UnitValue<EnergyUnit> AerobicEnergyComsumption(UnitValue<VolumeUnit> oxygenComsumption, UnitValue<WeightUnit> weight)
	{
		double energy;
		UnitValue<EnergyUnit> result;
		energy = (oxygenComsumption.getValue(VolumeUnit.l)/weight.getValue(WeightUnit.Kg))*20.9;
		result = new UnitValue<EnergyUnit>(energy,EnergyUnit.Kcal);
		return result;
	}
	
	public static UnitValue<EnergyUnit> AerobicEnergyComsumption(UnitValue<VolumeUnit> oxygenComsumption)
	{
		double energy;
		UnitValue<EnergyUnit> result;
		energy = (oxygenComsumption.getValue(VolumeUnit.l))*20.9;
		result = new UnitValue<EnergyUnit>(energy,EnergyUnit.Kcal);
		return result;
	}
}
