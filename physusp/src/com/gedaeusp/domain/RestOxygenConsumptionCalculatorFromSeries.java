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

public class RestOxygenConsumptionCalculatorFromSeries implements RestOxygenConsumptionCalculator {
	
	private UnitValue<FlowUnit> averageRestConsumption;
	
	public RestOxygenConsumptionCalculatorFromSeries(List<UnitValue<FlowUnit>> consumption,
			List<Integer> time) {
		IntegralCalculator<FlowUnit> integralCalculator = new IntegralCalculator<FlowUnit>(time, consumption);
		double integralValue = integralCalculator.calculate().getValue(FlowUnit.lPerSecond);
		double totalTime = time.get(time.size() - 1) - time.get(0);
		double result = integralValue / totalTime;
		setAverageRestConsumption(new UnitValue<FlowUnit>(result,FlowUnit.lPerSecond));;
	}
	

	@Override
	public UnitValue<FlowUnit> getAverageRestConsumption() {
		return averageRestConsumption;
	}

	public void setAverageRestConsumption(UnitValue<FlowUnit> averageRestConsumption) {
		this.averageRestConsumption = averageRestConsumption;
	}

}
