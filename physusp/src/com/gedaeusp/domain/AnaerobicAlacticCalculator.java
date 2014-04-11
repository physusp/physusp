package com.gedaeusp.domain;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.math3.fitting.CurveFitter;
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.util.FastMath;

public class AnaerobicAlacticCalculator {

	/**
	 * 1
	 * 
	 * @param comsumption
	 *            Oxygen consumption (volume per unit of time - e.g. Liters per
	 *            second) observed in a given moment.
	 * @param times
	 *            Time in which the oxygen consumption was observed.
	 * @param weight
	 *            Subject mass in kg.
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

		CurveFitter<Biexponential.Parametric> biexpFitter = new CurveFitter<Biexponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		CurveFitter<Exponential.Parametric> monoexpFitter = new CurveFitter<Exponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		// 2 Varre o conjunto de tempos e consumos adicionando cada ponto
		// 2 observados ao MonoExponential e Biexponential fitter.
		Iterator<Integer> timesIter = times.iterator();
		Iterator<UnitValue<FlowUnit>> consIter = consumption.iterator();

		while (consIter.hasNext() && timesIter.hasNext()) {
			UnitValue<FlowUnit> oxygen = consIter.next();
			Integer time = timesIter.next();

			biexpFitter.addObservedPoint(time,
					oxygen.getValue(FlowUnit.lPerSecond));
			monoexpFitter.addObservedPoint(time,
					oxygen.getValue(FlowUnit.lPerSecond));
		}

		double[] initBest = monoexpFitter.fit(new Exponential.Parametric(),
				new double[] { baselineOxygenVol.getValue(FlowUnit.lPerSecond),
						1, 1 });
		double initV0 = initBest[0];
		double initA = initBest[1];
		double initTau = initBest[2];

		double[] init = { initV0,
				timeDelay, // could be Collections.min(times)
				initA * 0.5 * FastMath.exp(-1 / initTau), initA * 0.5,
				initTau * 0.9, initTau * 1.1 };

		double[] best = biexpFitter.fit(new Biexponential.Parametric(), init);

		double a = best[2];
		double tau = best[4];
		double energy = a * tau * Constants.OXYGEN_TO_KCAL;

		return new UnitValue<EnergyUnit>(energy, EnergyUnit.Kcal);
	}
	
	public static UnitValue<EnergyUnit> calculateExponential(
			List<UnitValue<FlowUnit>> consumption, List<Integer> times,
			UnitValue<FlowUnit> baselineOxygenVol, Integer timeDelay) {
		return null;
	}


}
