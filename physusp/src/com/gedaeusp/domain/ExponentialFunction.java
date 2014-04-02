package com.gedaeusp.domain;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.util.FastMath;

public class ExponentialFunction implements ParametricUnivariateFunction {

	@Override
	public double value(double x, double... params) {
		double vo2 = params[0];
		double a = params[1];
		double tau = params[2];
		
		return vo2 + a * FastMath.exp(-x/tau);
	}

	@Override
	public double[] gradient(double x, double... params) {
		double a = params[1];
		double tau = params[2];

		double da = FastMath.exp(-x/tau);
		double dTau = a * x * FastMath.exp(-x/tau) / FastMath.pow(tau, 2);
		return new double[] {0, da, dTau};
	}

}
