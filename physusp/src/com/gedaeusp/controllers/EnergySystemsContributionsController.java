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

package com.gedaeusp.controllers;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

import com.gedaeusp.domain.EnergyConsumptionResponseBuilder;
import com.gedaeusp.models.AerobicParameters;
import com.gedaeusp.models.AnaerobicAlacticParameters;
import com.gedaeusp.models.AnaerobicLacticParameters;
import com.gedaeusp.models.Parameters;
import com.gedaeusp.models.RestOxygenParameters;
import javax.inject.Inject;

@Controller
@Path("esc")
public class EnergySystemsContributionsController {
	
        @Inject
        private Result result;

        protected EnergySystemsContributionsController(){

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
		this.result.use(Results.json()).from(builder.getResponse(), "consumption").recursive().serialize();
	}

}
