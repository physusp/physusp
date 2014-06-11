package com.gedaeusp.domain;

import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.util.FastMath;


/**
 * f(t) = v0 + a1 * exp( -(t-t0)/tau1 ) + a2 * exp( -(t-t0)/tau2 ) 
**/
public class Biexponential implements UnivariateDifferentiableFunction {

	public static final int PARAM_v0 = 0;
	public static final int PARAM_t0 = 1;
	public static final int PARAM_a1 = 2;
	public static final int PARAM_a2 = 3;
	public static final int PARAM_tau1 = 4;
	public static final int PARAM_tau2 = 5;

	private final double v0;
	private final double t0;
	private final double a1;
	private final double a2;
	private final double tau1;
	private final double tau2;

	public Biexponential(double v0, double t0, double a1, double a2,
			double tau1, double tau2) {
		this.v0 = v0;
		this.t0 = t0;
		this.a1 = a1;
		this.a2 = a2;
		this.tau1 = tau1;
		this.tau2 = tau2;
	}

	@Override
	public double value(double t) {
		return value(t, v0, t0, a1, a2, tau1, tau2);
	}

	public static double value(double t, double v0, double t0, double a1,
			double a2, double tau1, double tau2) {
		return v0 + a1 * FastMath.exp(-(t - t0) / tau1) + a2
				* FastMath.exp(-(t - t0) / tau2);
	}

	@Override
	public DerivativeStructure value(DerivativeStructure t) throws MathIllegalArgumentException {
		return t.exp();
	}


}
