package com.gedaeusp.controllers;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

import com.gedaeusp.domain.AnaerobicLactic;
import com.gedaeusp.domain.EnergyUnit;
import com.gedaeusp.domain.UnitValue;
import com.gedaeusp.domain.MolarConcentrationUnit;
import com.gedaeusp.domain.WeightUnit;
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
		
		UnitValue<MolarConcentrationUnit> restLactic = new UnitValue<MolarConcentrationUnit>(parameters.getRestLactateConcentration(), MolarConcentrationUnit.MiliMolPerLiter);
		UnitValue<MolarConcentrationUnit> maxLactic = new UnitValue<MolarConcentrationUnit>(parameters.getMaxLactateConcentration(), MolarConcentrationUnit.MiliMolPerLiter);
		UnitValue<WeightUnit> weight = new UnitValue<WeightUnit>(parameters.getWeight(), WeightUnit.Kg);
		
		
		UnitValue<EnergyUnit> energy = anaerobicLactic.calculate(restLactic, maxLactic, weight);
		
		EnergyConsumption energyConsumption = new EnergyConsumption();

		energyConsumption.setAnaerobicLactic(energy.getValue(EnergyUnit.Kcal));
		
		
		this.result.use(Results.json()).from(energyConsumption).serialize();
	}
}
