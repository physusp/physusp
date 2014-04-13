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
import com.gedaeusp.domain.AnaerobicLactic;
import com.gedaeusp.domain.EnergyUnit;
import com.gedaeusp.domain.FlowUnit;
import com.gedaeusp.domain.MolarConcentrationUnit;
import com.gedaeusp.domain.TimeSeriesParser;
import com.gedaeusp.domain.UnitValue;
import com.gedaeusp.domain.WeightUnit;
import com.gedaeusp.models.EnergyConsumption;
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
	public void calculate(Parameters parameters){
		
		AnaerobicLactic anaerobicLactic = new AnaerobicLactic();
		
		UnitValue<MolarConcentrationUnit> restLactic = new UnitValue<MolarConcentrationUnit>(parameters.getRestLactateConcentration(), MolarConcentrationUnit.MiliMolPerLiter);
		UnitValue<MolarConcentrationUnit> maxLactic = new UnitValue<MolarConcentrationUnit>(parameters.getMaxLactateConcentration(), MolarConcentrationUnit.MiliMolPerLiter);
		UnitValue<WeightUnit> weight = new UnitValue<WeightUnit>(parameters.getWeight(), WeightUnit.Kg);
		
		
		UnitValue<EnergyUnit> anaerobicLacticEnergy = anaerobicLactic.calculate(restLactic, maxLactic, weight);
		
		EnergyConsumption energyConsumption = new EnergyConsumption();

		energyConsumption.setAnaerobicLactic(anaerobicLacticEnergy.getValue(EnergyUnit.Kcal));

		TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();
		try {
			LinkedHashMap<Integer, UnitValue<FlowUnit>> oxygenConsumptionRestSeries = parser.parse(parameters.getOxygenConsumptionRest(), FlowUnit.mlPerMinute);
			LinkedHashMap<Integer, UnitValue<FlowUnit>> oxygenConsumptionDuringExerciseSeries = parser.parse(parameters.getOxygenConsumption(), FlowUnit.mlPerMinute);
			LinkedHashMap<Integer, UnitValue<FlowUnit>> oxygenConsumptionPostExerciseSeries = parser.parse(parameters.getOxygenConsumptionPost(), FlowUnit.mlPerMinute);
			
			AerobicCalculator aerobicCalculator = new AerobicCalculator();
			UnitValue<EnergyUnit> aerobicEnergy = aerobicCalculator.calculateEnergyConsumption(
					new ArrayList<UnitValue<FlowUnit>>(oxygenConsumptionDuringExerciseSeries.values()), 
					new ArrayList<UnitValue<FlowUnit>>(oxygenConsumptionRestSeries.values()), 
					new ArrayList<Integer>(oxygenConsumptionDuringExerciseSeries.keySet()), 
					new ArrayList<Integer>(oxygenConsumptionRestSeries.keySet()));
			
			UnitValue<EnergyUnit> anaerobicAlacticEnergy = AnaerobicAlacticCalculator.calculateBiexponential(
					new ArrayList<UnitValue<FlowUnit>>(oxygenConsumptionPostExerciseSeries.values()), 
					new ArrayList<Integer>(oxygenConsumptionPostExerciseSeries.keySet()), 
					aerobicCalculator.getAverageRestConsumption(), 
					0);
			
			energyConsumption.setAerobic(aerobicEnergy.getValue(EnergyUnit.Kcal));
			energyConsumption.setAnaerobicAlactic(anaerobicAlacticEnergy.getValue(EnergyUnit.Kcal));
			
			this.result.use(Results.json()).from(energyConsumption).serialize();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
