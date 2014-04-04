package com.gedaeusp.controllers;

import com.gedaeusp.models.EnergyConsumption;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

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
	@Consumes("application/json")
	public void calculate(EnergyConsumption consumption){
		this.result.use(Results.json()).from(consumption).serialize();
	}
}
