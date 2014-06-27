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

import org.apache.commons.math3.fitting.CurveFitter;
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.util.FastMath;

import br.com.caelum.vraptor.ioc.Component;

import com.gedaeusp.domain.BiexponentialParametric;



@Component
public class NonlinearCurveFitter {

	private void addObservedPointsToFitter(double[] v, double[] t,
			CurveFitter<?> fitter) {

		double min = t[0];

		for (int i = 0; i < t.length; i++) {
			fitter.addObservedPoint(t[i] - min, v[i]);
		}
	}

	public double[] guessBiexponentialInitialParameters(double[] v, double[] t,
			double v0, double t0) {

		double[] exp = doExponentialFit(v, t);
		double initT0 = (t0 < 0) ? -1 : t0;
		double initV0 = StatUtils.min(v);
		System.out.println("600 ? initV0 = " + initV0);
		double initTau = exp[Exponential.PARAM_tau];
		double initA = exp[Exponential.PARAM_a] * 0.5
				* FastMath.exp(-initT0 / initTau);

		return new double[] { initV0, initT0, initA, initA, initTau * 0.9,
				initTau * 1.1 };
	}


	public double[] guessExponentialInitialParameters(double[] v, double[] t,
			double v0, double t0) {

		double[] exp = doExponentialFit(v, t);
		double initT0 = (t0 < 0) ? -1 : t0;
		double initV0 = StatUtils.min(v);
		double initTau = exp[Exponential.PARAM_tau];
		double initA = exp[Exponential.PARAM_a] * FastMath.exp(-initT0 / initTau);

		return new double[] { initV0, initA, initTau, initT0 };
	}


	public double[] doExponentialFit(double[] v, double[] t) {
		CurveFitter<ExponentialParametric> fitter = new CurveFitter<ExponentialParametric>(new LevenbergMarquardtOptimizer());
		addObservedPointsToFitter(v, t, fitter);

		return fitter.fit(new ExponentialParametric(), new double[] { 0, 1, 1 });
	}

	@Deprecated
	public double[] doDelayedExponentialFit(double[] v, double[] t, double[] init) {
		CurveFitter<ExponentialParametric> fitter = new CurveFitter<ExponentialParametric>(new LevenbergMarquardtOptimizer());
		addObservedPointsToFitter(v, t, fitter);
		
		return fitter.fit(new ExponentialParametric(), init);
	}


	public double[] doBiexponentialFit(double[] v, double[] t, double[] init) {
		boolean fixedV0 = false;
		boolean fixedT0 = (init[Biexponential.PARAM_t0] >= 0);
		CurveFitter<BiexponentialParametric> fitter = new CurveFitter<BiexponentialParametric>(
				new LevenbergMarquardtOptimizer());
		addObservedPointsToFitter(v, t, fitter);
		
		return fitter.fit(new BiexponentialParametric(fixedV0, fixedT0), init);
	}
	
}
