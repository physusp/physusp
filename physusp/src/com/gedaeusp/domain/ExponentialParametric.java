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

public class ExponentialParametric implements ParametricUnivariateFunction {

	@Override
	public double value(double t, double... params) {
		double v0  = params[Exponential.PARAM_v0];
		double a   = params[Exponential.PARAM_a];
		double tau = params[Exponential.PARAM_tau];

		return Exponential.value(t, v0, a, tau);
	}

	@Override
	public double[] gradient(double t, double... params) {
		double a    = params[Exponential.PARAM_a];
		double tau  = params[Exponential.PARAM_tau];

		double dv0  = 1;
		double da   = FastMath.exp(-t / tau);
		double dTau = a * t * FastMath.exp(-t / tau) / FastMath.pow(tau, 2);
		
		return new double[] { dv0, da, dTau };
	}

}
