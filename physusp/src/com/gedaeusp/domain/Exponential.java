package com.gedaeusp.domain;

import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.util.FastMath;


public class Exponential implements UnivariateDifferentiableFunction {

	public static final int PARAM_v0 = 0;
	public static final int PARAM_a = 1;
	public static final int PARAM_tau = 2;

	private final double v0;

	private final double a;

	private final double tau;

	public Exponential(double v0, double a, double tau) {
		this.v0 = v0;
		this.a = a;
		this.tau = tau;
	}

	@Override
	public double value(double t) {
		return value(t, v0, a, tau);
	}

	public static double value(double t, double v0, double a, double tau) {
		return v0 + a * FastMath.exp(-t / tau);
	}

	@Override
	public DerivativeStructure value(DerivativeStructure t)
			throws MathIllegalArgumentException {
		return t.exp();
	}



}
