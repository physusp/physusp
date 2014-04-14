package com.gedaeusp.domain;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.math3.fitting.CurveFitter;
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.util.FastMath;

public class AnaerobicAlacticCalculator {

//	private static Log log = LogFactory
//			.getLog(AnaerobicAlacticCalculator.class);

	static void addObservedPointsToFitter(
			List<UnitValue<FlowUnit>> consumption, List<Integer> times,
			CurveFitter<?> fitter, FlowUnit unit) {

		Integer minTime = times.get(0); // offsets time data
		Iterator<Integer> timesIter = times.iterator();
		Iterator<UnitValue<FlowUnit>> consIter = consumption.iterator();

		while (consIter.hasNext() && timesIter.hasNext()) {
			UnitValue<FlowUnit> oxygen = consIter.next();
			Integer time = timesIter.next() - minTime;
			fitter.addObservedPoint(time, oxygen.getValue(unit));
		}
	}

	static double[] guessBiexponentialInitialParameters(
			List<UnitValue<FlowUnit>> consumption, List<Integer> times,
			UnitValue<FlowUnit> baselineOxygenVol, Integer timeDelay,
			FlowUnit unit) {

		// Use a monoexponential curve fitter to guess good initial parameters
		double[] exp = doExponentialFit(consumption, times, baselineOxygenVol,
				unit);

		double initT0 = (timeDelay == 0) ? 1 : timeDelay;
		double initV0 = exp[Exponential.PARAM_v0];
		double initTau = exp[Exponential.PARAM_tau];
		double initA = exp[Exponential.PARAM_a] * 0.5
				* FastMath.exp(-initT0 / initTau);

		return new double[] { initV0, initT0, initA, initA, initTau * 0.9,
				initTau * 1.1 };
	}

	static double[] guessExponentialInitialParameters(
			List<UnitValue<FlowUnit>> consumption, List<Integer> times,
			UnitValue<FlowUnit> baselineOxygenVol, Integer timeDelay,
			FlowUnit unit) {

		// Use a monoexponential curve fitter to guess good initial parameters
		double[] exp = doExponentialFit(consumption, times, baselineOxygenVol,
				unit);

		double initV0 = exp[Exponential.PARAM_v0];
		double initTau = exp[Exponential.PARAM_tau];
		double initA = exp[Exponential.PARAM_a]
				* FastMath.exp(-timeDelay / initTau);

		return new double[] { initV0, initA, initTau, timeDelay };
	}

	static double[] doExponentialFit(List<UnitValue<FlowUnit>> consumption,
			List<Integer> times, UnitValue<FlowUnit> baselineOxygenVol,
			FlowUnit unit) {

		CurveFitter<Exponential.Parametric> fitter = new CurveFitter<Exponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		addObservedPointsToFitter(consumption, times, fitter, unit);

		Exponential.ParametricBuilder builder = new Exponential.ParametricBuilder()
				.fixedV0(true); // We never optimize the given v0

		return fitter.fit(builder.build(),
				new double[] { baselineOxygenVol.getValue(unit), 1, 1 });
	}

	static double[] doDelayedExponentialFit(
			List<UnitValue<FlowUnit>> consumption, List<Integer> times,
			Integer timeDelay, double[] init, FlowUnit unit) {

		CurveFitter<DelayedExponential.Parametric> fitter = new CurveFitter<DelayedExponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		addObservedPointsToFitter(consumption, times, fitter, unit);

		DelayedExponential.ParametricBuilder builder = new DelayedExponential.ParametricBuilder()
				.fixedV0(true) // We never optimize the given v0
				// If a timeDalay was given, we won't optimize the parameter t0
				.fixedT0(timeDelay > 0);

		return fitter.fit(builder.build(), init);
	}

	static double[] doBixponentialFit(List<UnitValue<FlowUnit>> consumption,
			List<Integer> times, Integer timeDelay, double[] init, FlowUnit unit) {

		CurveFitter<Biexponential.Parametric> fitter = new CurveFitter<Biexponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		addObservedPointsToFitter(consumption, times, fitter, unit);

		Biexponential.ParametricBuilder builder = new Biexponential.ParametricBuilder()
				.fixedV0(true) // We never optimize the given v0
				// If a timeDalay was given, we won't optimize the parameter t0
				.fixedT0(timeDelay > 0);

		return fitter.fit(builder.build(), init);
	}

	public static UnitValue<EnergyUnit> calculateEnergyWithBiExponential(
			List<UnitValue<FlowUnit>> consumption, List<Integer> times,
			UnitValue<FlowUnit> baselineOxygenVol, Integer timeDelay) {

		// Guess the initial parameters for the curve fitting
		double[] init = guessBiexponentialInitialParameters(consumption, times,
				baselineOxygenVol, timeDelay, FlowUnit.lPerSecond);

		// Do the biexponential fitting using the initial guessed parameters
		double[] best = doBixponentialFit(consumption, times, timeDelay, init,
				FlowUnit.lPerSecond);

		double a = best[Biexponential.PARAM_a1];
		double tau = best[Biexponential.PARAM_tau1];
		double energy = a * tau * Constants.OXYGEN_TO_KCAL;

		return new UnitValue<EnergyUnit>(energy, EnergyUnit.Kcal);
	}

	public static UnitValue<EnergyUnit> calculateEnergyWithMonoExponential(
			List<UnitValue<FlowUnit>> consumption, List<Integer> times,
			UnitValue<FlowUnit> baselineOxygenVol, Integer timeDelay) {

		// Guess the initial parameters for the curve fitting
		double[] init = guessExponentialInitialParameters(consumption, times,
				baselineOxygenVol, timeDelay, FlowUnit.lPerSecond);

		// Do the monoexponential fitting using the initial guessed parameters
		double[] best = doDelayedExponentialFit(consumption, times, timeDelay,
				init, FlowUnit.lPerSecond);

		double a = best[DelayedExponential.PARAM_a];
		double tau = best[DelayedExponential.PARAM_tau];
		double energy = a * tau * Constants.OXYGEN_TO_KCAL;

		return new UnitValue<EnergyUnit>(energy, EnergyUnit.Kcal);
	}

}
