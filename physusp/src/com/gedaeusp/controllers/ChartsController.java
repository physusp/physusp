package com.gedaeusp.controllers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.view.Results;

import com.gedaeusp.domain.AerobicCalculator;
import com.gedaeusp.domain.AnaerobicLactic;
import com.gedaeusp.domain.EnergyValue;
import com.gedaeusp.domain.MolarConcentrationValue;
import com.gedaeusp.domain.Time;
import com.gedaeusp.domain.WeightValue;
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
	public void calculate(Parameters parameters, UploadedFile oxygenConsumption, UploadedFile oxygenConsumptionRest){
		
		AnaerobicLactic anaerobicLactic = new AnaerobicLactic();
		
		MolarConcentrationValue restLactic = new MolarConcentrationValue(parameters.getRestLactateConcentration(), MolarConcentrationValue.MiliMolPerLiter);
		MolarConcentrationValue maxLactic = new MolarConcentrationValue(parameters.getMaxLactateConcentration(), MolarConcentrationValue.MiliMolPerLiter);
		WeightValue weight = new WeightValue(parameters.getWeight(), WeightValue.Kg);
		
		EnergyConsumption energyConsumption = new EnergyConsumption(); 
		
		EnergyValue energy = anaerobicLactic.calculate(restLactic, maxLactic, weight);
		
		energyConsumption.setAnaerobicLactic(energy.getValue(EnergyValue.Kcal));

		List<Integer> times = new ArrayList<Integer>();
		List<Double> values = new ArrayList<Double>();
		readFile(oxygenConsumption, times, values);
		
		List<Integer> timesRest = new ArrayList<Integer>();
		List<Double> valuesRest = new ArrayList<Double>();
		readFile(oxygenConsumptionRest, timesRest, valuesRest);
		
		double energyAerobic = AerobicCalculator.calculateAerobicComsumption(values, valuesRest, times, timesRest);
		energyConsumption.setAerobic(energyAerobic);
		
		this.result.use(Results.json()).from(energyConsumption).serialize();
	}

	private void readFile(UploadedFile oxygenConsumption, List<Integer> times, List<Double> values) {
		InputStream file = oxygenConsumption.getFile();
		BufferedReader reader = new BufferedReader(new InputStreamReader(file));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				int time = Time.convertDateToSeconds(data[0]);
				times.add(time);
				values.add(Double.parseDouble(data[1]));
			}
		} catch (Exception e) {
			log.error("Error reading file: " + e.getMessage());
		}
	}
}
