package com.gedaeusp.domain;
import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.util.FastMath;

import com.gedaeusp.domain.Biexponential;

public class BiexponentialParametric implements ParametricUnivariateFunction {

		private boolean fixedV0;
		private boolean fixedT0;

		public BiexponentialParametric(boolean fixedV0, boolean fixedT0) {
			this.fixedV0 = fixedV0;
			this.fixedT0 = fixedT0;
		}

		@Override
		public double value(double t, double... params) {
			double v0 = params[Biexponential.PARAM_v0];
			double t0 = params[Biexponential.PARAM_t0];
			double a1 = params[Biexponential.PARAM_a1];
			double a2 = params[Biexponential.PARAM_a2];
			double tau1 = params[Biexponential.PARAM_tau1];
			double tau2 = params[Biexponential.PARAM_tau2];

			return Biexponential.value(t, v0, t0, a1, a2, tau1, tau2);
		}

		@Override
		public double[] gradient(double t, double... params) {
			double t0 = params[Biexponential.PARAM_t0];
			double a1 = params[Biexponential.PARAM_a1];
			double a2 = params[Biexponential.PARAM_a2];
			double tau1 = params[Biexponential.PARAM_tau1];
			double tau2 = params[Biexponential.PARAM_tau2];

			double dv0 = fixedV0 ? 0 : 1;

			double dt0 = 0;
			if (!fixedT0) {
				if (t0 < 0) {
					params[Biexponential.PARAM_t0] = 0;
				} else {
					dt0 = (a1 / tau1) * FastMath.exp((t0 - t) / tau1)
							+ (a2 / tau2) * FastMath.exp((t0 - t) / tau2);
				}
			}

			double da1 = FastMath.exp((t0 - t) / tau1);
			double da2 = FastMath.exp((t0 - t) / tau2);
			double dTau1 = (a1 * (t - t0) * FastMath.exp((t0 - t) / tau1))
					/ FastMath.pow(tau1, 2);
			double dTau2 = (a2 * (t - t0) * FastMath.exp((t0 - t) / tau2))
					/ FastMath.pow(tau2, 2);

			return new double[] { dv0, dt0, da1, da2, dTau1, dTau2 };
		}

	}