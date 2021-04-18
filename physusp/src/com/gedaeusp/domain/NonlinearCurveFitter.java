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

import com.gedaeusp.domain.BiexponentialParametric;


public class NonlinearCurveFitter {

	private void addObservedPointsToFitter(double[] v, double[] t,
			CurveFitter<?> fitter) {

		double min = 0;
		if(t.length > 0) min = t[0];

		for (int i = 0; i < t.length; i++) {
			fitter.addObservedPoint(t[i] - min, v[i]);
		}
	}
	
	public double[] guessBiexponentialInitialParameters(double[] v, double[] t, double v0) {

		double[] exp = doExponentialFit(v, t);
		double initV0 = v0;
		double initTau = exp[Exponential.PARAM_tau];
		double initA = exp[Exponential.PARAM_a] * 0.5;
		
		return new double[] { initV0, initA, initA, initTau * 0.9, initTau * 1.1 };
	}
	
	
	public double[] doExponentialFitWithFixedV0(double[] v, double[] t, double v0) {
		CurveFitter<ExponentialParametric> fitter = new CurveFitter<ExponentialParametric>(new LevenbergMarquardtOptimizer());
		addObservedPointsToFitter(v, t, fitter);

		return fitter.fit(new ExponentialParametric(true), new double[] { v0, 1, 1 });		
	}
	
	public double[] doBiexponentialFitWithFixedV0(double[] v, double[] t, double[] init) {
		CurveFitter<BiexponentialParametric> fitter = new CurveFitter<BiexponentialParametric>(new LevenbergMarquardtOptimizer());
		addObservedPointsToFitter(v, t, fitter);
		
		/* Queremos garantir que a componente rápida está associada a tau1 e a1: */
		double[] best = fitter.fit(new BiexponentialParametric(true), init);
		if (best[Biexponential.PARAM_tau1] > best[Biexponential.PARAM_tau2]) {
			
			double tmp_tau1                = best[Biexponential.PARAM_tau2];
			best[Biexponential.PARAM_tau2] = best[Biexponential.PARAM_tau1];
			best[Biexponential.PARAM_tau1] = tmp_tau1;
			
			double tmp_a1                  = best[Biexponential.PARAM_a2];
			best[Biexponential.PARAM_a2]   = best[Biexponential.PARAM_a1];
			best[Biexponential.PARAM_a1]   = tmp_a1;
		}
				
		return best;
	}
	
	public double[] doExponentialFit(double[] v, double[] t) {
		CurveFitter<ExponentialParametric> fitter = new CurveFitter<ExponentialParametric>(new LevenbergMarquardtOptimizer());
		addObservedPointsToFitter(v, t, fitter);

		return fitter.fit(new ExponentialParametric(), new double[] { 0, 1, 1 });
	}
	
	public double[] doBiexponentialFit(double[] v, double[] t, double[] init) {
		CurveFitter<BiexponentialParametric> fitter = new CurveFitter<BiexponentialParametric>(new LevenbergMarquardtOptimizer());
		addObservedPointsToFitter(v, t, fitter);
		
		return fitter.fit(new BiexponentialParametric(), init);
	}
	
}
