package com.gedaeusp;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

/*
* anotando seu controller com @Resource, seus métodos públicos ficarão disponíveis
* para receber requisições web.
*/
@Resource
public class ClientsController {

 	private final Result result;

     


 	public ClientsController(Result result) {
 		this.result = result;
 	}

 	//@Path("/")
 	public void hello() {
 		result.include("variable", "VRaptor!");
 	}

     
}
