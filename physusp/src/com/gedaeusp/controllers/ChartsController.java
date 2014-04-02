package com.gedaeusp.controllers;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class ChartsController {
	private final Result result;
	
	public ChartsController(Result result) {
		this.result = result;
	}
	
	@Path({"/charts", "/charts/", "/charts/index"})
	public void index() {
		
	}
}
