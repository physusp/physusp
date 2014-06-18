package com.gedaeusp.domain;

import org.apache.commons.math3.fitting.CurveFitter;
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.util.FastMath;

import br.com.caelum.vraptor.ioc.Component;

import com.gedaeusp.domain.BiexponentialParametric;
import com.gedaeusp.domain.DelayedExponentialParametric;


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

		double[] exp = doExponentialFit(v, t, v0);
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

		double[] exp = doExponentialFit(v, t, v0);
		double initT0 = (t0 < 0) ? -1 : t0;
		double initV0 = StatUtils.min(v);
		System.out.println("600 ? initV0 = " + initV0);
		double initTau = exp[Exponential.PARAM_tau];
		double initA = exp[Exponential.PARAM_a] * FastMath.exp(-initT0 / initTau);

		return new double[] { initV0, initA, initTau, initT0 };
	}


	public double[] doExponentialFit(double[] v, double[] t, double v0) {
		boolean fixedV0 = false;
		CurveFitter<ExponentialParametric> fitter = new CurveFitter<ExponentialParametric>(
				new LevenbergMarquardtOptimizer());
		addObservedPointsToFitter(v, t, fitter);

		return fitter.fit(new ExponentialParametric(fixedV0), new double[] { v0, 1, 1 });
	}


	public double[] doDelayedExponentialFit(double[] v, double[] t, double[] init) {
		boolean fixedV0 = false;
		boolean fixedT0 = (init[Biexponential.PARAM_t0] >= 0);
		CurveFitter<DelayedExponentialParametric> fitter = new CurveFitter<DelayedExponentialParametric>(
				new LevenbergMarquardtOptimizer());
		addObservedPointsToFitter(v, t, fitter);

		return fitter.fit(new DelayedExponentialParametric(fixedV0, fixedT0), init);
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
