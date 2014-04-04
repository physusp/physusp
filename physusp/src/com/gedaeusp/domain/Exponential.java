package com.gedaeusp.domain;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.util.FastMath;

/**
 * A function of the form: {@code V(t) = V0 + A * exp(-t/tau)}. {@code V(t)} is
 * the oxygen uptake at time {@code t}. {@code V0}, {@code A} and {@code tau}
 * are the oxygen uptake at baseline, the amplitude and the time constant,
 * respectively.
 * 
 * As proposed in the article
 * "Energy system contributions in indoor rock climbing", equations [1] and [2].
 * 
 * @see "R. C. deM. Bertuzzi et al., Energy system contributions in indoor rock climbing, 2007, Springer-Verlag"
 * @author igortopcin
 */
public class Exponential implements UnivariateDifferentiableFunction {

	/** The oxygen uptake at baseline */
	private final double v0;

	/** The amplitude */
	private final double a;

	/** Time constant */
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

	private static double value(double t, double v0, double a, double tau) {
		return v0 + a * FastMath.exp(-t / tau);
	}

	@Override
	public DerivativeStructure value(DerivativeStructure t)
			throws MathIllegalArgumentException {
		return t.exp();
	}

	public static class Parametric implements ParametricUnivariateFunction {

		@Override
		public double value(double t, double... params) {
			double v0 = params[0];
			double a = params[1];
			double tau = params[2];

			return Exponential.value(t, v0, a, tau);
		}

		@Override
		public double[] gradient(double x, double... params) {
			double a = params[1];
			double tau = params[2];

			double da = FastMath.exp(-x / tau);
			double dTau = a * x * FastMath.exp(-x / tau) / FastMath.pow(tau, 2);
			return new double[] { 0, da, dTau };
		}

	}

}
