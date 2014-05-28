package com.gedaeusp.controllers;

import com.gedaeusp.domain.ExceptionInfo;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.view.Results;


@Intercepts
public class ExceptionInterceptor implements Interceptor {

	private Result result;
	
	public ExceptionInterceptor(Result result){
		this.result = result;
	}
	
	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object resourceInstance) throws InterceptionException {
		try
		{
			stack.next(method, resourceInstance);
		}
		catch(Exception ex){
			result.use(Results.http()).setStatusCode(500);
			result.use(Results.json()).from(new ExceptionInfo(ex)).serialize();
		}
	}

	@Override
	public boolean accepts(ResourceMethod method) {
		return true;
	}

}
