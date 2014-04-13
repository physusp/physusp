package com.gedaeusp.controllers;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
import com.gedaeusp.domain.Time;
import com.gedaeusp.domain.UnitValue;
import com.gedaeusp.domain.WeightUnit;
import com.gedaeusp.models.EnergyConsumption;
import com.gedaeusp.models.Parameters;

@Resource
@Path("/esc")
public class EnergySystemsContributionsController {
	private final Result result;
	private Log log = LogFactory.getLog(this.getClass());
	
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

		List<Integer> times = new ArrayList<Integer>();
		List<UnitValue<FlowUnit>> values = new ArrayList<UnitValue<FlowUnit>>();
		readFile(parameters.getOxygenConsumption(), times, values);
		
		List<Integer> timesRest = new ArrayList<Integer>();
		List<UnitValue<FlowUnit>> valuesRest = new ArrayList<UnitValue<FlowUnit>>();
		readFile(parameters.getOxygenConsumptionRest(), timesRest, valuesRest);
		
		List<Integer> timesPost = new ArrayList<Integer>();
		List<UnitValue<FlowUnit>> valuesPost = new ArrayList<UnitValue<FlowUnit>>();
		readFile(parameters.getOxygenConsumptionPost(), timesPost, valuesPost);
		
		AerobicCalculator aerobicCalculator = new AerobicCalculator();
		UnitValue<EnergyUnit> aerobicEnergy = aerobicCalculator.calculateEnergyConsumption(values, valuesRest, times, timesRest);
		
		UnitValue<EnergyUnit> anaerobicAlacticEnergy = AnaerobicAlacticCalculator.calculateBiexponential(valuesPost, timesPost, aerobicCalculator.getAverageRestConsumption(), (int) parameters.getTimeDelayPost());
		
		energyConsumption.setAerobic(aerobicEnergy.getValue(EnergyUnit.Kcal));
		energyConsumption.setAnaerobicAlactic(anaerobicAlacticEnergy.getValue(EnergyUnit.Kcal));
		
		this.result.use(Results.json()).from(energyConsumption).serialize();
	}

	private void readFile(String oxygenConsumption, List<Integer> times, List<UnitValue<FlowUnit>> values) {
		StringReader file = new StringReader(oxygenConsumption);
		BufferedReader reader = new BufferedReader(file);
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				if(line.isEmpty())
					continue;
				String[] data = line.split(",");
				int time = Time.convertDateToSeconds(data[0]);
				times.add(time);
				values.add(new UnitValue<FlowUnit>(Double.parseDouble(data[1]), FlowUnit.mlPerMinute));
			}
		} catch (Exception e) {
			log.error("Error reading file: " + e.getMessage());
		}
	}
}
