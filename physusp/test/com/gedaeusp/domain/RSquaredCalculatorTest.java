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

public class RSquaredCalculatorTest {
	static double EPS = 0.001;
	@Test
	public void testCalculate() {
		RSquaredCalculator rSquaredCalculator = new RSquaredCalculator();
		
		double x[] = new double[]{1, 2, 3, 4, 5};
		double yObserved[] = new double[]{1.9, 3.7, 5.8, 8.0, 9.6};
		double yExpected[] = new double[yObserved.length];
		for (int i = 0; i < x.length; i++)
			yExpected[i] = -0.11 + 1.97 * x[i]; 
		assertEquals(0.997, rSquaredCalculator.calculate(yObserved, yExpected), EPS);
	}
	
	@Test
	public void testCalculateMean() {
		RSquaredCalculator rSquaredCalculator = new RSquaredCalculator();
		
		double x[] = new double[]{1.2, 22.4, 5.2, 24.2, 5.3};
		assertEquals(11.66, rSquaredCalculator.calculateMean(x), EPS);
	}
	
}
