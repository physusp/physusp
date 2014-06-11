package com.gedaeusp.domain;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.util.FastMath;

public class ExponentialParametric implements ParametricUnivariateFunction {

	private boolean fixedV0;


	public ExponentialParametric(boolean fixedV0) {
		this.fixedV0 = fixedV0;
	}

	@Override
	public double value(double t, double... params) {
		double v0 = params[Exponential.PARAM_v0];
		double a = params[Exponential.PARAM_a];
		double tau = params[Exponential.PARAM_tau];

		return Exponential.value(t, v0, a, tau);
	}

	@Override
	public double[] gradient(double t, double... params) {
		double a = params[Exponential.PARAM_a];
		double tau = params[Exponential.PARAM_tau];

		double dv0 = fixedV0 ? 0 : 1;

		double da = FastMath.exp(-t / tau);
		double dTau = a * t * FastMath.exp(-t / tau) / FastMath.pow(tau, 2);
		return new double[] { dv0, da, dTau };
	}

}