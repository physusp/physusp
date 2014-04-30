package com.gedaeusp.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

import com.gedaeusp.domain.AerobicCalculator;
import com.gedaeusp.domain.AnaerobicAlacticCalculator;
import com.gedaeusp.domain.AnaerobicLacticCalculator;
import com.gedaeusp.domain.EnergyUnit;
import com.gedaeusp.domain.FlowUnit;
import com.gedaeusp.domain.MolarConcentrationUnit;
import com.gedaeusp.domain.NonlinearCurveFitter;
import com.gedaeusp.domain.RestOxygenConsumptionCalculator;
import com.gedaeusp.domain.TimeSeriesParser;
import com.gedaeusp.domain.UnitValue;
import com.gedaeusp.domain.WeightUnit;
import com.gedaeusp.models.EnergyConsumptionResponse;
import com.gedaeusp.models.Parameters;

@Resource
@Path("/esc")
public class EnergySystemsContributionsController {
	private final Result result;

	public EnergySystemsContributionsController(Result result) {
		this.result = result;
	}

	@Path("")
	public void index() {
	}

	@Post
	public void calculate(Parameters parameters) {

		EnergyConsumptionResponse response = new EnergyConsumptionResponse();

		if (parameters.getCalculateAnaerobicLactic()) {
			UnitValue<EnergyUnit> anaerobicLacticEnergy = getAerobicCalculator(parameters);
			response.setAnaerobicLactic(anaerobicLacticEnergy.getValue(EnergyUnit.Kcal));
		}

		if (parameters.getCalculateAerobic() || parameters.getCalculateAnaerobicAlactic()) {
			
			try {
				RestOxygenConsumptionCalculator restOxygenConsumptionCalculator = getRestOxygenConsumptionCalculator(parameters);
			
				if (parameters.getCalculateAerobic()){
					UnitValue<EnergyUnit> aerobicEnergy;
					try {
						aerobicEnergy = getAerobicCalculator(parameters, restOxygenConsumptionCalculator);
						response.setAerobic(aerobicEnergy.getValue(EnergyUnit.Kcal));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if (parameters.getCalculateAnaerobicAlactic()) {
					
					UnitValue<EnergyUnit> anaerobicAlacticEnergy;
					try {
						anaerobicAlacticEnergy = getAnaerobicAlacticCalculator(parameters, restOxygenConsumptionCalculator);
						response.setAnaerobicAlactic(anaerobicAlacticEnergy.getValue(EnergyUnit.Kcal));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		this.result.use(Results.json()).from(response).serialize();
	}

	private UnitValue<EnergyUnit> getAnaerobicAlacticCalculator(
			Parameters parameters,
			RestOxygenConsumptionCalculator restOxygenConsumptionCalculator)
			throws ParseException {
		TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();
		
		LinkedHashMap<Integer, UnitValue<FlowUnit>> oxygenConsumptionPostExerciseSeries = parser
				.parse(parameters.getOxygenConsumptionPostExercise(),
						FlowUnit.mlPerMinute);

		AnaerobicAlacticCalculator alacticCalculator = new AnaerobicAlacticCalculator(new NonlinearCurveFitter());
		
		alacticCalculator.setExponentialInput(
				new ArrayList<UnitValue<FlowUnit>>(
						oxygenConsumptionPostExerciseSeries.values()),
				new ArrayList<Integer>(
						oxygenConsumptionPostExerciseSeries.keySet()),
			restOxygenConsumptionCalculator.getAverageRestConsumption(),
				(int) parameters.getTimeDelayPost());

		UnitValue<EnergyUnit> anaerobicAlacticEnergy = parameters
				.getExponentialType() == 1 ? alacticCalculator
				.calculateEnergyWithMonoExponential()
				: alacticCalculator.calculateEnergyWithBiExponential();
		return anaerobicAlacticEnergy;
	}

	private UnitValue<EnergyUnit> getAerobicCalculator(Parameters parameters,
			RestOxygenConsumptionCalculator restOxygenConsumptionCalculator)
			throws ParseException {
		TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();
		
		LinkedHashMap<Integer, UnitValue<FlowUnit>> oxygenConsumptionDuringExerciseSeries = parser
				.parse(parameters.getOxygenConsumptionDuringExercise(),
						FlowUnit.mlPerMinute);
		AerobicCalculator aerobicCalculator = new AerobicCalculator();
		
		UnitValue<EnergyUnit> aerobicEnergy = aerobicCalculator
				.calculateEnergyConsumption(
						new ArrayList<UnitValue<FlowUnit>>(
								oxygenConsumptionDuringExerciseSeries
										.values()),
										restOxygenConsumptionCalculator.getAverageRestConsumption(),
						new ArrayList<Integer>(
								oxygenConsumptionDuringExerciseSeries
										.keySet()));
		return aerobicEnergy;
	}

	private RestOxygenConsumptionCalculator getRestOxygenConsumptionCalculator(
			Parameters parameters) throws ParseException {
		TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();
		LinkedHashMap<Integer, UnitValue<FlowUnit>> oxygenConsumptionRestSeries = parser
				.parse(parameters.getOxygenConsumptionRest(),
						FlowUnit.mlPerMinute);

		RestOxygenConsumptionCalculator restOxygenConsumptionCalculator = new RestOxygenConsumptionCalculator(
				new ArrayList<UnitValue<FlowUnit>>(
						oxygenConsumptionRestSeries.values()),
				new ArrayList<Integer>(oxygenConsumptionRestSeries
						.keySet()));
		return restOxygenConsumptionCalculator;
	}

	private UnitValue<EnergyUnit> getAerobicCalculator(Parameters parameters) {
		UnitValue<MolarConcentrationUnit> restLactic = new UnitValue<MolarConcentrationUnit>(
				parameters.getRestLactateConcentration(),
				MolarConcentrationUnit.MiliMolPerLiter);
		UnitValue<MolarConcentrationUnit> maxLactic = new UnitValue<MolarConcentrationUnit>(
				parameters.getMaxLactateConcentration(),
				MolarConcentrationUnit.MiliMolPerLiter);
		UnitValue<WeightUnit> weight = new UnitValue<WeightUnit>(
				parameters.getWeight(), WeightUnit.Kg);
		AnaerobicLacticCalculator anaerobicLactic = new AnaerobicLacticCalculator();
		UnitValue<EnergyUnit> anaerobicLacticEnergy = anaerobicLactic
				.calculate(restLactic, maxLactic, weight);
		return anaerobicLacticEnergy;
	}

}
