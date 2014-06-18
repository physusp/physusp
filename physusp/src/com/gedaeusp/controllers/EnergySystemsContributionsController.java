package com.gedaeusp.controllers;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

import com.gedaeusp.domain.EnergyConsumptionResponseBuilder;
import com.gedaeusp.models.AerobicParameters;
import com.gedaeusp.models.AnaerobicAlacticParameters;
import com.gedaeusp.models.AnaerobicLacticParameters;
import com.gedaeusp.models.Parameters;
import com.gedaeusp.models.RestOxygenParameters;

@Resource
@Path("esc")
public class EnergySystemsContributionsController {
	private final Result result;

	public EnergySystemsContributionsController(Result result) {
		this.result = result;
	}
	
	@Path("")
	public void index() {
	}

	@Post
	@Path("calculate")
	public void calculate(Parameters parameters, AerobicParameters aerobicParameters, AnaerobicLacticParameters anaerobicLacticParameters,
			AnaerobicAlacticParameters anaerobicAlacticParameters, RestOxygenParameters restOxygenParameters) {

		EnergyConsumptionResponseBuilder builder = new EnergyConsumptionResponseBuilder();
		
		if (parameters.getCalculateAnaerobicLactic()) {
			builder = builder.addAnaerobicLactic(anaerobicLacticParameters);
		}

		if (parameters.getCalculateAerobic()){
			builder = builder.addAerobic(aerobicParameters, restOxygenParameters);
		}
				
		if (parameters.getCalculateAnaerobicAlactic()) {	
			builder = builder.addAnaerobicAlactic(anaerobicAlacticParameters);
		}
		this.result.use(Results.json()).from(builder.getResponse()).recursive().serialize();
	}

}
