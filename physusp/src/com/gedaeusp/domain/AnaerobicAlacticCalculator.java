package com.gedaeusp.domain;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.math3.fitting.CurveFitter;
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.util.FastMath;

public class AnaerobicAlacticCalculator {

	private static Log log = LogFactory
			.getLog(AnaerobicAlacticCalculator.class);

	/**
	 * Helper function that adds each observed point to the given fitter.
	 */
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

	/**
	 * Function that finds a good guess for the initial parameter values of the
	 * curve fitting.
	 * 
	 * @param comsumption
	 *            Oxygen consumption (volume per unit of time - e.g. Liters per
	 *            second) observed in a given moment.
	 * @param times
	 *            Time in which the oxygen consumption was observed.
	 * @param baselineOxygenVol
	 *            Baseline oxygen consumption (volume per unit of time - e.g.
	 *            Liters/second).
	 * @return an array with the initial parameter guessing.
	 */
	static double[] guessBiexponentialParameters(
			List<UnitValue<FlowUnit>> consumption, List<Integer> times,
			UnitValue<FlowUnit> baselineOxygenVol, Integer timeDelay,
			FlowUnit unit) {

		// Use a monoexponential curve fitter to guess good initial parameters
		double[] exp = exponentialFit(consumption, times, baselineOxygenVol,
				unit);

		double initT0 = (timeDelay == 0)? 1 : timeDelay;
		double initV0 = exp[Exponential.PARAM_v0];
		double initTau = exp[Exponential.PARAM_tau];
		double initA = exp[Exponential.PARAM_a] * 0.5
				* FastMath.exp(-initT0 / initTau);

		return new double[] { initV0, initT0, initA, initA, initTau * 0.9,
				initTau * 1.1 };
	}

	/**
	 * Function that finds a good guess for the initial parameter values of the
	 * curve fitting.
	 * 
	 * @param comsumption
	 *            Oxygen consumption (volume per unit of time - e.g. Liters per
	 *            second) observed in a given moment.
	 * @param times
	 *            Time in which the oxygen consumption was observed.
	 * @param baselineOxygenVol
	 *            Baseline oxygen consumption (volume per unit of time - e.g.
	 *            Liters/second).
	 * @return an array with the initial parameter guessing.
	 */
	static double[] guessExponentialParameters(
			List<UnitValue<FlowUnit>> consumption, List<Integer> times,
			UnitValue<FlowUnit> baselineOxygenVol, Integer timeDelay,
			FlowUnit unit) {

		// Use a monoexponential curve fitter to guess good initial parameters
		double[] exp = exponentialFit(consumption, times, baselineOxygenVol,
				unit);

		double initV0 = exp[Exponential.PARAM_v0];
		double initTau = exp[Exponential.PARAM_tau];
		double initA = exp[Exponential.PARAM_a]
				* FastMath.exp(-timeDelay / initTau);

		return new double[] { initV0, initA, initTau, timeDelay };
	}

	/**
	 * Performs a simple exponential fitting by using the {@link Exponential}
	 * function.
	 * 
	 * @param comsumption
	 *            Oxygen consumption (volume per unit of time - e.g. Liters per
	 *            second) observed in a given moment.
	 * @param times
	 *            Time in which the oxygen consumption was observed.
	 * @param baselineOxygenVol
	 *            Baseline oxygen consumption (volume per unit of time - e.g.
	 *            Liters/second).
	 * @return an array with the optimized values for each parameter in the
	 *         function.
	 */
	static double[] exponentialFit(List<UnitValue<FlowUnit>> consumption,
			List<Integer> times, UnitValue<FlowUnit> baselineOxygenVol,
			FlowUnit unit) {

		CurveFitter<Exponential.Parametric> fitter = new CurveFitter<Exponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		addObservedPointsToFitter(consumption, times, fitter, unit);

		Exponential.ParametricBuilder builder = new Exponential.ParametricBuilder()
				.fixedV0(true); // We never optimize the given v0

		double[] best = fitter.fit(builder.build(), new double[] {
				baselineOxygenVol.getValue(unit), 1, 1 });
		log.debug("init v0 = " + best[Exponential.PARAM_v0]);
		log.debug("init A = " + best[Exponential.PARAM_a]);
		log.debug("init tau = " + best[Exponential.PARAM_tau]);

		return best;
	}

	/**
	 * Returns the energy consumption for the off exercise phase.
	 * 
	 * @param comsumption
	 *            Oxygen consumption (volume per unit of time - e.g. Liters per
	 *            second) observed in a given moment.
	 * @param times
	 *            Time in which the oxygen consumption was observed.
	 * @param baselineOxygenVol
	 *            Baseline oxygen consumption (volume per unit of time - e.g.
	 *            Liters/second).
	 * @param timeDelay
	 *            Delay time (delta) in seconds.
	 * @return Energy in KJ.
	 */
	public static UnitValue<EnergyUnit> calculateBiexponential(
			List<UnitValue<FlowUnit>> consumption, List<Integer> times,
			UnitValue<FlowUnit> baselineOxygenVol, Integer timeDelay) {

		// Guess the initial parameters for the curve fitting
		double[] init = guessBiexponentialParameters(consumption, times,
				baselineOxygenVol, timeDelay, FlowUnit.lPerSecond);
		log.debug("init VO2_base = " + init[Biexponential.PARAM_v0]);
		log.debug("init t0 = " + init[Biexponential.PARAM_t0]);
		log.debug("init A1 = " + init[Biexponential.PARAM_a1]);
		log.debug("init A2 = " + init[Biexponential.PARAM_a2]);
		log.debug("init tau1 = " + init[Biexponential.PARAM_tau1]);
		log.debug("init tau2 = " + init[Biexponential.PARAM_tau2]);

		// Do the biexponential fitting using the initial guessed parameters
		CurveFitter<Biexponential.Parametric> fitter = new CurveFitter<Biexponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		addObservedPointsToFitter(consumption, times, fitter,
				FlowUnit.lPerSecond);

		Biexponential.ParametricBuilder builder = new Biexponential.ParametricBuilder()
				.fixedV0(true) // We never optimize the given v0
				// If a timeDalay was given, we won't optimize the parameter t0
				.fixedT0(timeDelay > 0);

		double[] best = fitter.fit(builder.build(), init);

		log.debug("Resultados da regressão:");
		log.debug("VO2_base = " + best[Biexponential.PARAM_v0]);
		log.debug("t0 = " + best[Biexponential.PARAM_t0]);
		log.debug("A1 = " + best[Biexponential.PARAM_a1]);
		log.debug("A2 = " + best[Biexponential.PARAM_a2]);
		log.debug("tau1 = " + best[Biexponential.PARAM_tau1]);
		log.debug("tau2 = " + best[Biexponential.PARAM_tau2]);

		double a = best[Biexponential.PARAM_a1];
		double tau = best[Biexponential.PARAM_tau1];
		double energy = a * tau * Constants.OXYGEN_TO_KCAL;

		return new UnitValue<EnergyUnit>(energy, EnergyUnit.Kcal);
	}

	public static UnitValue<EnergyUnit> calculateExponential(
			List<UnitValue<FlowUnit>> consumption, List<Integer> times,
			UnitValue<FlowUnit> baselineOxygenVol, Integer timeDelay) {

		// Guess the initial parameters for the curve fitting
		double[] init = guessExponentialParameters(consumption, times,
				baselineOxygenVol, timeDelay, FlowUnit.lPerSecond);

		// Do the monoexponential fitting using the initial guessed parameters
		CurveFitter<DelayedExponential.Parametric> fitter = new CurveFitter<DelayedExponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		addObservedPointsToFitter(consumption, times, fitter,
				FlowUnit.lPerSecond);

		DelayedExponential.ParametricBuilder builder = new DelayedExponential.ParametricBuilder()
				.fixedV0(true) // We never optimize the given v0
				// If a timeDalay was given, we won't optimize the parameter t0
				.fixedT0(timeDelay > 0);

		double[] best = fitter.fit(builder.build(), init);

		log.debug("Resultados da regressão:");
		log.debug("VO2_base = " + best[DelayedExponential.PARAM_v0]);
		log.debug("t0 = " + best[DelayedExponential.PARAM_t0]);
		log.debug("A = " + best[DelayedExponential.PARAM_a]);
		log.debug("Tau = " + best[DelayedExponential.PARAM_tau]);

		double a = best[DelayedExponential.PARAM_a];
		double tau = best[DelayedExponential.PARAM_tau];
		double energy = a * tau * Constants.OXYGEN_TO_KCAL;

		return new UnitValue<EnergyUnit>(energy, EnergyUnit.Kcal);
	}

}
