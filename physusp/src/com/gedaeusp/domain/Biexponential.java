package com.gedaeusp.domain;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.util.FastMath;

/**
 * A function of the form:
 * {@code V(t) = V0 + A1 * exp(-(t - t0)/tau1) + A2 * exp(-(t - t0)/tau2)}.
 * {@code V(t)} is the oxygen uptake at time {@code t}. {@code V0}, {@code A1},
 * {@code tau1}, {@code A2} and {@code tau2} are the oxygen uptake at baseline,
 * the amplitude and the time constant, respectively.
 * 
 * As proposed in the article
 * "Energy system contributions in indoor rock climbing", equations [1] and [2].
 * 
 * @see "R. C. deM. Bertuzzi et al., Energy system contributions in indoor rock climbing, 2007, Springer-Verlag"
 * @author igortopcin
 */
public class Biexponential implements UnivariateDifferentiableFunction {

	/** The oxygen uptake at baseline */
	private final double v0;

	/** Time offset for exponential curve */
	private final double t0;

	/** The amplitude */
	private final double a1;
	private final double a2;

	/** Time constant */
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

	private static double value(double t, double v0, double t0, double a1,
			double a2, double tau1, double tau2) {
		return v0 + a1 * FastMath.exp(-(t - t0) / tau1) + a2
				* FastMath.exp(-(t - t0) / tau2);
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
			double t0 = params[1];
			double a1 = params[2];
			double a2 = params[3];
			double tau1 = params[4];
			double tau2 = params[5];

			return Biexponential.value(t, v0, t0, a1, a2, tau1, tau2);
		}

		@Override
		public double[] gradient(double t, double... params) {
			double t0 = params[1];
			double a1 = params[2];
			double a2 = params[3];
			double tau1 = params[4];
			double tau2 = params[5];

			double dt0 = 0;
			if (t0 < 0) {
				params[1] = 0;
			} else {
				dt0 = (a1 / tau1) * FastMath.exp((t0 - t) / tau1) + (a2 / tau2)
						* FastMath.exp((t0 - t) / tau2);
			}
			double da1 = FastMath.exp((t0 - t) / tau1);
			double da2 = FastMath.exp((t0 - t) / tau2);
			double dTau1 = (a1 * (t - t0) * FastMath.exp((t0 - t) / tau1))
					/ FastMath.pow(tau1, 2);
			double dTau2 = (a2 * (t - t0) * FastMath.exp((t0 - t) / tau2))
					/ FastMath.pow(tau2, 2);

			return new double[] { 1, dt0, da1, da2, dTau1, dTau2 };
		}

	}

}
