package com.gedaeusp.domain;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.util.FastMath;

/**
 * A function of the form: {@code V(t) = V0 + A * exp(-(t - t0)/tau)}.
 * {@code V(t)} is the oxygen uptake at time {@code t}. {@code V0}, {@code A}
 * and {@code tau} are the oxygen uptake at baseline, the amplitude and the time
 * constant, respectively. {@code t0} is the time delay.
 * 
 * @author igortopcin
 */
public class DelayedExponential implements UnivariateDifferentiableFunction {

	public static final int PARAM_v0 = 0;
	public static final int PARAM_a = 1;
	public static final int PARAM_tau = 2;
	public static final int PARAM_t0 = 3;

	/** The oxygen uptake at baseline */
	private final double v0;

	/** The amplitude */
	private final double a;

	/** Time constant */
	private final double tau;

	/** Time delay */
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

	private static double value(double t, double v0, double a, double tau,
			double t0) {
		return v0 + a * FastMath.exp(-(t - t0) / tau);
	}

	@Override
	public DerivativeStructure value(DerivativeStructure t)
			throws MathIllegalArgumentException {
		return t.exp();
	}

	/**
	 * A class representing a mono-exponential function that depends on one
	 * independent variable plus some extra parameters.
	 * 
	 * This is meant to be used by a curve fitter.
	 * 
	 * Use {@link DelayedExponential.ParametricBuilder} to create
	 * {@link DelayedExponential.Parametric} instances.
	 * 
	 * @see {@link DelayedExponential.ParametricBuilder}
	 */
	public static class Parametric implements ParametricUnivariateFunction {

		private boolean fixedV0;
		private boolean fixedT0;

		/**
		 * Constructor that takes a builder as parameter in order to create a
		 * {@link DelayedExponential.Parametric} instance.
		 */
		public Parametric(DelayedExponential.ParametricBuilder builder) {
			fixedV0 = builder.fixedV0;
			fixedT0 = builder.fixedT0;
		}

		/**
		 * Compute the value of the function.
		 */
		@Override
		public double value(double t, double... params) {
			double v0 = params[PARAM_v0];
			double a = params[PARAM_a];
			double tau = params[PARAM_tau];
			double t0 = params[PARAM_t0];

			return DelayedExponential.value(t, v0, a, tau, t0);
		}

		/**
		 * Compute the gradient of the function with respect to its parameters.
		 */
		@Override
		public double[] gradient(double t, double... params) {
			double a = params[PARAM_a];
			double tau = params[PARAM_tau];
			double t0 = params[PARAM_t0];

			// We do not optimize v0 if fixedV0 is true
			double dv0 = fixedV0 ? 0 : 1;

			// We do not optimize t0 if fixedT0 is true
			// Also, we set a lower boundary for t0
			double dt0 = 0;
			if (!fixedT0) {
				if (t0 < 0) {
					params[PARAM_t0] = 0;
				} else {
					dt0 = (a / tau) * FastMath.exp((t0 - t) / tau);
				}
			}

			// We optimize the rest of the parameters, that is, A and Tau
			double da = FastMath.exp((t0 - t) / tau);
			double dTau = a * (t - t0) * FastMath.exp((t0 - t) / tau)
					/ FastMath.pow(tau, 2);

			return new double[] { dv0, da, dTau, dt0 };
		}

	}

	/**
	 * A Builder class for {@link DelayedExponential.Parametric}.
	 */
	public static class ParametricBuilder {

		private boolean fixedV0;
		private boolean fixedT0;

		/**
		 * Tell the curve fitter not to optimize the {@code v0} parameter, that
		 * is, the passed in initial value for {@code v0} will remain untouched.
		 */
		public ParametricBuilder fixedV0(boolean fixedV0) {
			this.fixedV0 = fixedV0;
			return this;
		}

		/**
		 * Tell the curve fitter not to optimize the {@code t0} parameter, that
		 * is, the passed in initial value for {@code t0} will remain untouched.
		 */
		public ParametricBuilder fixedT0(boolean fixedT0) {
			this.fixedT0 = fixedT0;
			return this;
		}

		/**
		 * Creates a {@link DelayedExponential.Parametric}
		 */
		public DelayedExponential.Parametric build() {
			return new DelayedExponential.Parametric(this);
		}

	}

}
