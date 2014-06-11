package com.gedaeusp.domain;


import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.util.FastMath;

public class DelayedExponentialParametric implements ParametricUnivariateFunction {

		private boolean fixedV0;
		private boolean fixedT0;

		public DelayedExponentialParametric(boolean fixedV0, boolean fixedT0) {
			this.fixedV0 = fixedV0;
			this.fixedT0 = fixedT0;
		}


		@Override
		public double value(double t, double... params) {
			double v0 = params[DelayedExponential.PARAM_v0];
			double a = params[DelayedExponential.PARAM_a];
			double tau = params[DelayedExponential.PARAM_tau];
			double t0 = params[DelayedExponential.PARAM_t0];

			return DelayedExponential.value(t, v0, a, tau, t0);
		}

		@Override
		public double[] gradient(double t, double... params) {
			double a = params[DelayedExponential.PARAM_a];
			double tau = params[DelayedExponential.PARAM_tau];
			double t0 = params[DelayedExponential.PARAM_t0];

			double dv0 = fixedV0 ? 0 : 1;

			double dt0 = 0;
			if (!fixedT0) {
				if (t0 < 0) {
					params[DelayedExponential.PARAM_t0] = 0;
				} else {
					dt0 = (a / tau) * FastMath.exp((t0 - t) / tau);
				}
			}

			double da = FastMath.exp((t0 - t) / tau);
			double dTau = a * (t - t0) * FastMath.exp((t0 - t) / tau)
					/ FastMath.pow(tau, 2);

			return new double[] { dv0, da, dTau, dt0 };
		}

	}