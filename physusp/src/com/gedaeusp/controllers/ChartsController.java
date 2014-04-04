package com.gedaeusp.controllers;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

import com.gedaeusp.domain.AnaerobicLactic;
import com.gedaeusp.domain.EnergyValue;
import com.gedaeusp.domain.MolarConcentrationValue;
import com.gedaeusp.domain.WeightValue;
import com.gedaeusp.models.EnergyConsumption;
import com.gedaeusp.models.Parameters;

@Resource
public class ChartsController {
	private final Result result;
	
	public ChartsController(Result result) {
		this.result = result;
	}
	
	@Path("/charts")
	public void index() {
		
	}
	
	@Post
	public void calculate(Parameters parameters){
		
		AnaerobicLactic anaerobicLactic = new AnaerobicLactic();
		
		MolarConcentrationValue restLactic = new MolarConcentrationValue(parameters.getRestLactateConcentration(), MolarConcentrationValue.MiliMolPerLiter);
		MolarConcentrationValue maxLactic = new MolarConcentrationValue(parameters.getMaxLactateConcentration(), MolarConcentrationValue.MiliMolPerLiter);
		WeightValue weight = new WeightValue(parameters.getWeight(), WeightValue.Kg);
		
		EnergyConsumption energyConsumption = new EnergyConsumption(); 
		
		EnergyValue energy = anaerobicLactic.calculate(restLactic, maxLactic, weight);
		
		energyConsumption.setAnaerobicLactic(energy.getValue(EnergyValue.Kcal));
		
		
		this.result.use(Results.json()).from(energyConsumption).serialize();
	}
}
