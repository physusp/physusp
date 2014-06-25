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

import com.gedaeusp.domain.DomainException;
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
		try {
			stack.next(method, resourceInstance);
		}
		catch(InterceptionException ex) {
			result.use(Results.http()).setStatusCode(500);
			Throwable causeException = ex.getCause();
			
			if(causeException instanceof DomainException) {
				result.use(Results.json()).from(new ExceptionInfo((DomainException) causeException)).serialize();
			}
			else throw ex;
		}
	}

	@Override
	public boolean accepts(ResourceMethod method) {
		return true;
	}

}
