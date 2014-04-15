package com.gedaeusp.domain;

import org.apache.commons.math3.fitting.CurveFitter;
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.util.FastMath;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class NonlinearCurveFitter {

	private void addObservedPointsToFitter(double[] v, double[] t,
			CurveFitter<?> fitter) {

		double min = t[0]; // offsets time data

		for (int i = 0; i < t.length; i++) {
			fitter.addObservedPoint(t[i] - min, v[i]);
		}
	}

	/**
	 * v(t) = v0 + A1 * exp(-(t - t0)/tau1) + A2 * exp(-(t - t0)/tau2)
	 * 
	 * @return { v0, t0, A1, A2, tau1, tau2 }
	 */
	public double[] guessBiexponentialInitialParameters(double[] v, double[] t,
			double v0, double t0) {

		// Use a monoexponential curve fitter to guess good initial parameters
		double[] exp = doExponentialFit(v, t, v0);

		double initT0 = (t0 == 0) ? 1 : t0;
		double initV0 = exp[Exponential.PARAM_v0];
		double initTau = exp[Exponential.PARAM_tau];
		double initA = exp[Exponential.PARAM_a] * 0.5
				* FastMath.exp(-initT0 / initTau);

		return new double[] { initV0, t0, initA, initA, initTau * 0.9,
				initTau * 1.1 };
	}

	/**
	 * v(t) = v0 + A * exp(-(t - t0)/tau)
	 * 
	 * @return { v0, A, tau, t0 }
	 */
	public double[] guessExponentialInitialParameters(double[] v, double[] t,
			double v0, double t0) {

		// Use a monoexponential curve fitter to guess good initial parameters
		double[] exp = doExponentialFit(v, t, v0);

		double initV0 = exp[Exponential.PARAM_v0];
		double initTau = exp[Exponential.PARAM_tau];
		double initA = exp[Exponential.PARAM_a] * FastMath.exp(-t0 / initTau);

		return new double[] { initV0, initA, initTau, t0 };
	}

	/**
	 * v(t) = v0 + A * exp(-t/tau)
	 * 
	 * @return { v0, A, tau}
	 */
	public double[] doExponentialFit(double[] v, double[] t, double v0) {

		CurveFitter<Exponential.Parametric> fitter = new CurveFitter<Exponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		addObservedPointsToFitter(v, t, fitter);

		Exponential.ParametricBuilder builder = new Exponential.ParametricBuilder()
				.fixedV0(true); // We never optimize the given v0

		return fitter.fit(builder.build(), new double[] { v0, 1, 1 });
	}

	/**
	 * v(t) = v0 + A * exp(-(t - t0)/tau)
	 * 
	 * @return { v0, A, tau, t0 }
	 */
	public double[] doDelayedExponentialFit(double[] v, double[] t,
			double[] init) {

		CurveFitter<DelayedExponential.Parametric> fitter = new CurveFitter<DelayedExponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		addObservedPointsToFitter(v, t, fitter);

		DelayedExponential.ParametricBuilder builder = new DelayedExponential.ParametricBuilder()
				.fixedV0(true) // We never optimize the given v0
				// If a timeDalay was given, we won't optimize the parameter t0
				.fixedT0(init[Biexponential.PARAM_t0] > 0);

		return fitter.fit(builder.build(), init);
	}

	/**
	 * v(t) = v0 + A1 * exp(-(t - t0)/tau1) + A2 * exp(-(t - t0)/tau2)
	 * 
	 * @return { v0, t0, A1, A2, tau1, tau2 }
	 */
	public double[] doBixponentialFit(double[] v, double[] t, double[] init) {

		CurveFitter<Biexponential.Parametric> fitter = new CurveFitter<Biexponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		addObservedPointsToFitter(v, t, fitter);

		Biexponential.ParametricBuilder builder = new Biexponential.ParametricBuilder()
				.fixedV0(true) // We never optimize the given v0
				// If a timeDalay was given, we won't optimize the parameter t0
				.fixedT0(init[Biexponential.PARAM_t0] > 0);

		return fitter.fit(builder.build(), init);
	}
}
