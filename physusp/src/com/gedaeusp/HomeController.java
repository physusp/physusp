package com.gedaeusp;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

/*
* anotando seu controller com @Resource, seus métodos públicos ficarão disponíveis
* para receber requisições web.
*/
@Resource
public class HomeController {

 	private final Result result;

     


 	public HomeController(Result result) {
 		this.result = result;
 	}

 	@Path("/")
 	public void index() {
 		result.include("variable", "Alfredo!");
 	}

     
}
