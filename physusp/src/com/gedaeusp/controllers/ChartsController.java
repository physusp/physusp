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
import com.gedaeusp.domain.AnaerobicLactic;
import com.gedaeusp.domain.EnergyUnit;
import com.gedaeusp.domain.MolarConcentrationUnit;
import com.gedaeusp.domain.Time;
import com.gedaeusp.domain.UnitValue;
import com.gedaeusp.domain.VolumeUnit;
import com.gedaeusp.domain.WeightUnit;
import com.gedaeusp.models.EnergyConsumption;
import com.gedaeusp.models.Parameters;

@Resource
public class ChartsController {
	private final Result result;
	private Log log = LogFactory.getLog(this.getClass());
	
	public ChartsController(Result result) {
		this.result = result;
	}
	
	@Path("/charts")
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
		List<UnitValue<VolumeUnit>> values = new ArrayList<UnitValue<VolumeUnit>>();
		readFile(parameters.getOxygenConsumption(), times, values);
		
		List<Integer> timesRest = new ArrayList<Integer>();
		List<UnitValue<VolumeUnit>> valuesRest = new ArrayList<UnitValue<VolumeUnit>>();
		readFile(parameters.getOxygenConsumptionRest(), timesRest, valuesRest);
		
		UnitValue<VolumeUnit> aerobicOxygenEquivalent = AerobicCalculator.calculateAerobicComsumption(values, valuesRest, times, timesRest);
		
		UnitValue<EnergyUnit> aerobicEnergyConsumption = AerobicCalculator.AerobicEnergyComsumption(aerobicOxygenEquivalent);
		
		energyConsumption.setAerobic(aerobicEnergyConsumption.getValue(EnergyUnit.Kcal));
		
		this.result.use(Results.json()).from(energyConsumption).serialize();
	}

	private void readFile(String oxygenConsumption, List<Integer> times, List<UnitValue<VolumeUnit>> values) {
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
				values.add(new UnitValue<VolumeUnit>(Double.parseDouble(data[1]), VolumeUnit.ml));
			}
		} catch (Exception e) {
			log.error("Error reading file: " + e.getMessage());
		}
	}
}
