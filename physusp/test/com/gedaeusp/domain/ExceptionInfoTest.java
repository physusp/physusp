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

package com.gedaeusp.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExceptionInfoTest {
	
	@Test	
	public void exceptionInfoTest() {
		ExceptionInfo exceptionInfo = new ExceptionInfo(new DomainException("Oi Alfredo!"));
		assertEquals("com.gedaeusp.domain.DomainException", exceptionInfo.getName());
		assertEquals("Oi Alfredo!", exceptionInfo.getMessage());	
	}	
}
