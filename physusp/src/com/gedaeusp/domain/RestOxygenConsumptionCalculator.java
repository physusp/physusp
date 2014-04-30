package com.gedaeusp.domain;

import java.util.List;

public class RestOxygenConsumptionCalculator {
	
	private UnitValue<FlowUnit> averageRestConsumption;
	
	public RestOxygenConsumptionCalculator(List<UnitValue<FlowUnit>> consumption,
			List<Integer> time) {
		IntegralCalculator<FlowUnit> integralCalculator = new IntegralCalculator<FlowUnit>(time, consumption);
		double integralValue = integralCalculator.calculate().getValue(FlowUnit.lPerSecond);
		double totalTime = time.get(time.size() - 1) - time.get(0);
		double result = integralValue / totalTime;
		setAverageRestConsumption(new UnitValue<FlowUnit>(result,FlowUnit.lPerSecond));;
	}
	
	public UnitValue<FlowUnit> getAverageRestConsumption() {
		return averageRestConsumption;
	}

	public void setAverageRestConsumption(UnitValue<FlowUnit> averageRestConsumption) {
		this.averageRestConsumption = averageRestConsumption;
	}

}
