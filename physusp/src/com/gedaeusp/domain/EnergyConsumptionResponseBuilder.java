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

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.gedaeusp.models.AerobicParameters;
import com.gedaeusp.models.AnaerobicAlacticParameters;
import com.gedaeusp.models.AnaerobicLacticParameters;
import com.gedaeusp.models.BiexponentialFitData;
import com.gedaeusp.models.EnergyConsumptionResponse;
import com.gedaeusp.models.MonoexponentialFitData;
import com.gedaeusp.models.RestOxygenParameters;

public class EnergyConsumptionResponseBuilder {

	private EnergyConsumptionResponse response;
	private RestOxygenConsumptionCalculator restOxygenConsumptionCalculator;

	public EnergyConsumptionResponseBuilder() {
		response = new EnergyConsumptionResponse();
	}

	public EnergyConsumptionResponse getResponse() {
		return response;
	}

	public EnergyConsumptionResponseBuilder addAnaerobicLactic(
			AnaerobicLacticParameters parameters) {
		UnitValue<EnergyUnit> anaerobicLacticEnergy = getAnaerobicLacticCalculator(parameters);
		response.setAnaerobicLactic(anaerobicLacticEnergy);
		return this;
	}

	public EnergyConsumptionResponseBuilder addAerobic(
			AerobicParameters parameters, RestOxygenParameters restParameters) {
		UnitValue<EnergyUnit> aerobicEnergy;
		aerobicEnergy = getAerobicCalculator(parameters,
				getRestOxygenConsumptionCalculator(restParameters));
		response.setAerobic(aerobicEnergy);
		return this;
	}

	public EnergyConsumptionResponseBuilder addAnaerobicAlactic(AnaerobicAlacticParameters parameters) {
		UnitValue<EnergyUnit> anaerobicAlacticEnergy;
		if (!parameters.isUseTimeDelay())
			parameters.setTimeDelay(-1);
		anaerobicAlacticEnergy = getAnaerobicAlacticCalculator(parameters);
		response.setAnaerobicAlactic(anaerobicAlacticEnergy);
		return this;
	}

	private UnitValue<EnergyUnit> getAerobicCalculator(
			AerobicParameters parameters,
			RestOxygenConsumptionCalculator restOxygenConsumptionCalculator) {
		TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();

		LinkedHashMap<Integer, UnitValue<FlowUnit>> oxygenConsumptionDuringExerciseSeries = parser
				.parse(parameters.getOxygenConsumptionDuringExercise(),
						FlowUnit.mlPerMinute);
		AerobicCalculator aerobicCalculator = new AerobicCalculator();

		UnitValue<EnergyUnit> aerobicEnergy = aerobicCalculator
				.calculateEnergyConsumption(new ArrayList<UnitValue<FlowUnit>>(
						oxygenConsumptionDuringExerciseSeries.values()),
						restOxygenConsumptionCalculator
								.getAverageRestConsumption(),
						new ArrayList<Integer>(
								oxygenConsumptionDuringExerciseSeries.keySet()));
		return aerobicEnergy;
	}

	private RestOxygenConsumptionCalculator getRestOxygenConsumptionCalculator(
			RestOxygenParameters parameters) {

		if (restOxygenConsumptionCalculator != null)
			return restOxygenConsumptionCalculator;

		switch (parameters.getCalculateMethod()) {
		case "fixed":
			restOxygenConsumptionCalculator = new RestOxygenConsumptionCalculatorFixed(
					new UnitValue<FlowUnit>(parameters.getFixedValue(),
							FlowUnit.mlPerMinute));
			break;
		case "ignore":
			restOxygenConsumptionCalculator = new RestOxygenConsumptionCalculatorFixed(
					new UnitValue<FlowUnit>(0, FlowUnit.mlPerMinute));
			break;
		case "series":
			TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();
			LinkedHashMap<Integer, UnitValue<FlowUnit>> oxygenConsumptionRestSeries = parser
					.parse(parameters.getOxygenConsumptionRest(),
							FlowUnit.mlPerMinute);

			restOxygenConsumptionCalculator = new RestOxygenConsumptionCalculatorFromSeries(
					new ArrayList<UnitValue<FlowUnit>>(
							oxygenConsumptionRestSeries.values()),
					new ArrayList<Integer>(oxygenConsumptionRestSeries.keySet()));
			break;
		}

		return restOxygenConsumptionCalculator;
	}

	private UnitValue<EnergyUnit> getAnaerobicLacticCalculator(
			AnaerobicLacticParameters parameters) {
		UnitValue<MolarConcentrationUnit> restLactic = new UnitValue<MolarConcentrationUnit>(
				parameters.getRestLactateConcentration(),
				MolarConcentrationUnit.MiliMolPerLiter);
		UnitValue<MolarConcentrationUnit> maxLactic = new UnitValue<MolarConcentrationUnit>(
				parameters.getPeakLactateConcentration(),
				MolarConcentrationUnit.MiliMolPerLiter);
		UnitValue<WeightUnit> weight = new UnitValue<WeightUnit>(
				parameters.getWeight(), WeightUnit.Kg);
		AnaerobicLacticCalculator anaerobicLactic = new AnaerobicLacticCalculator();
		UnitValue<EnergyUnit> anaerobicLacticEnergy = anaerobicLactic
				.calculate(restLactic, maxLactic, weight);
		return anaerobicLacticEnergy;
	}

	private UnitValue<EnergyUnit> getAnaerobicAlacticCalculator(
			AnaerobicAlacticParameters parameters) {
		TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();

		LinkedHashMap<Integer, UnitValue<FlowUnit>> oxygenConsumptionPostExerciseSeries = parser
				.parse(parameters.getOxygenConsumptionPostExercise(),
						FlowUnit.mlPerMinute);

		AnaerobicAlacticCalculator alacticCalculator = new AnaerobicAlacticCalculator(
				new NonlinearCurveFitter());

		alacticCalculator.setExponentialInput(
				new ArrayList<UnitValue<FlowUnit>>(
						oxygenConsumptionPostExerciseSeries.values()),
				new ArrayList<Integer>(oxygenConsumptionPostExerciseSeries
						.keySet()), (double) parameters
						.getTimeDelay());

		UnitValue<EnergyUnit> anaerobicAlacticEnergy;
		if (parameters.getExponentialType() == 1) {
//		    TODO: Alterações para ajustar o time delay
			MonoexponentialFitData monoexponentialFitData = new MonoexponentialFitData();
			anaerobicAlacticEnergy = alacticCalculator.calculateEnergyWithMonoexponential(monoexponentialFitData);
			response.setExpectedOxygenConsumptions(monoexponentialFitData.getExpectedOxygenConsumptions());
			response.setRSquared(monoexponentialFitData.getRSquared());
			response.setV0(monoexponentialFitData.getV0());
			response.setT0(monoexponentialFitData.getT0());
			response.setA1(monoexponentialFitData.getA());
			response.setTau1(monoexponentialFitData.getTau());

		} else {
//	        TODO: Alterações para ajustar o time delay 
			BiexponentialFitData biexponentialFitData = new BiexponentialFitData();
			anaerobicAlacticEnergy = alacticCalculator.calculateEnergyWithBiexponential(biexponentialFitData);
			response.setExpectedOxygenConsumptions(biexponentialFitData.getExpectedOxygenConsumptions());
			response.setRSquared(biexponentialFitData.getRSquared());
			response.setV0(biexponentialFitData.getV0());
			response.setT0(biexponentialFitData.getT0());
			response.setA1(biexponentialFitData.getA1());
			response.setA2(biexponentialFitData.getA2());
			response.setTau1(biexponentialFitData.getTau1());
			response.setTau2(biexponentialFitData.getTau2());
		}

		return anaerobicAlacticEnergy;
	}

}
