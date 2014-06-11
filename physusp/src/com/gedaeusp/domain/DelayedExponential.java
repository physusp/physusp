package com.gedaeusp.domain;

import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.util.FastMath;


public class DelayedExponential implements UnivariateDifferentiableFunction {

	public static final int PARAM_v0 = 0;
	public static final int PARAM_a = 1;
	public static final int PARAM_tau = 2;
	public static final int PARAM_t0 = 3;

	private final double v0;

	private final double a;

	private final double tau;

	private final double t0;

	public DelayedExponential(double v0, double a, double tau, double t0) {
		this.v0 = v0;
		this.a = a;
		this.tau = tau;
		this.t0 = t0;
	}

	@Override
	public double value(double t) {
		return value(t, v0, a, tau, t0);
	}

	public static double value(double t, double v0, double a, double tau,
			double t0) {
		return v0 + a * FastMath.exp(-(t - t0) / tau);
	}

	@Override
	public DerivativeStructure value(DerivativeStructure t)
			throws MathIllegalArgumentException {
		return t.exp();
	}

}
