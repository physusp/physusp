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
import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.util.FastMath;

import com.gedaeusp.domain.Biexponential;

public class BiexponentialParametric implements ParametricUnivariateFunction {
	
	private boolean fixedV0;
	
	public BiexponentialParametric() {
		this(false);
	}
	
	public BiexponentialParametric(boolean fixedV0) {
		this.fixedV0 = fixedV0;
	}
	
	@Override
	public double value(double t, double... params) {
		double v0 = params[Biexponential.PARAM_v0];
		double a1 = params[Biexponential.PARAM_a1];
		double a2 = params[Biexponential.PARAM_a2];
		double tau1 = params[Biexponential.PARAM_tau1];
		double tau2 = params[Biexponential.PARAM_tau2];

		return Biexponential.value(t, v0, a1, a2, tau1, tau2);
	}

	@Override
	public double[] gradient(double t, double... params) {
		double a1 = params[Biexponential.PARAM_a1];
		double a2 = params[Biexponential.PARAM_a2];
		double tau1 = params[Biexponential.PARAM_tau1];
		double tau2 = params[Biexponential.PARAM_tau2];
		
		double dv0 = fixedV0 == true ? 0 : 1;
		double da1 = FastMath.exp(-t / tau1);
		double da2 = FastMath.exp(-t / tau2);
		double dTau1 = (a1 * t * FastMath.exp(-t / tau1))
				/ FastMath.pow(tau1, 2);
		double dTau2 = (a2 * t * FastMath.exp(-t / tau2))
				/ FastMath.pow(tau2, 2);

		return new double[] { dv0, da1, da2, dTau1, dTau2 };
	}

}
