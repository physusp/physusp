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

	public static final int PARAM_v0 = 0;
	public static final int PARAM_t0 = 1;
	public static final int PARAM_a1 = 2;
	public static final int PARAM_a2 = 3;
	public static final int PARAM_tau1 = 4;
	public static final int PARAM_tau2 = 5;

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
	public DerivativeStructure value(DerivativeStructure t) throws MathIllegalArgumentException {
		return t.exp();
	}

	/**
	 * A class representing a bi-exponential function that depends on one
	 * independent variable plus some extra parameters.
	 * 
	 * This is meant to be used by a curve fitter.
	 * 
	 * Use {@link Biexponential.ParametricBuilder} to create
	 * {@link Biexponential.Parametric} instances.
	 * 
	 * @see {@link Biexponential.ParametricBuilder}
	 */
	public static class Parametric implements ParametricUnivariateFunction {

		private boolean fixedV0;
		private boolean fixedT0;

		/**
		 * Constructor that takes a builder as parameter in order to create a
		 * {@link Biexponential.Parametric} instance.
		 */
		public Parametric(Biexponential.ParametricBuilder builder) {
			fixedV0 = builder.fixedV0;
			fixedT0 = builder.fixedT0;
		}

		/**
		 * Compute the value of the function.
		 */
		@Override
		public double value(double t, double... params) {
			double v0 = params[PARAM_v0];
			double t0 = params[PARAM_t0];
			double a1 = params[PARAM_a1];
			double a2 = params[PARAM_a2];
			double tau1 = params[PARAM_tau1];
			double tau2 = params[PARAM_tau2];

			return Biexponential.value(t, v0, t0, a1, a2, tau1, tau2);
		}

		/**
		 * Compute the gradient of the function with respect to its parameters.
		 */
		@Override
		public double[] gradient(double t, double... params) {
			double t0 = params[PARAM_t0];
			double a1 = params[PARAM_a1];
			double a2 = params[PARAM_a2];
			double tau1 = params[PARAM_tau1];
			double tau2 = params[PARAM_tau2];

			// We do not optimize v0 if fixedV0 is true
			double dv0 = fixedV0 ? 0 : 1;

			// We do not optimize t0 if fixedT0 is true
			// Also, we set a lower boundary for t0
			double dt0 = 0;
			if (!fixedT0) {
				if (t0 < 0) {
					params[PARAM_t0] = 0;
				} else {
					dt0 = (a1 / tau1) * FastMath.exp((t0 - t) / tau1)
							+ (a2 / tau2) * FastMath.exp((t0 - t) / tau2);
				}
			}

			// We always try to optimize the rest of the parameters, that is,
			// A1, A2, Tau1, Tau2
			double da1 = FastMath.exp((t0 - t) / tau1);
			double da2 = FastMath.exp((t0 - t) / tau2);
			double dTau1 = (a1 * (t - t0) * FastMath.exp((t0 - t) / tau1))
					/ FastMath.pow(tau1, 2);
			double dTau2 = (a2 * (t - t0) * FastMath.exp((t0 - t) / tau2))
					/ FastMath.pow(tau2, 2);

			return new double[] { dv0, dt0, da1, da2, dTau1, dTau2 };
		}

	}

	/**
	 * A Builder class for {@link Biexponential.Parametric}.
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
		 * Creates a {@link Biexponential.Parametric}
		 */
		public Biexponential.Parametric build() {
			return new Biexponential.Parametric(this);
		}

	}

}
