package com.gedaeusp.domain;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.math3.fitting.CurveFitter;
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.util.FastMath;

public class AnaerobicAlacticCalculator {

	public static UnitValue<EnergyUnit> calculateBiexponential(
			List<UnitValue<FlowUnit>> consumption, List<Integer> times,
			UnitValue<FlowUnit> baselineOxygenVol, Integer timeDelay) {

		CurveFitter<Biexponential.Parametric> biexpFitter = new CurveFitter<Biexponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		CurveFitter<Exponential.Parametric> monoexpFitter = new CurveFitter<Exponential.Parametric>(
				new LevenbergMarquardtOptimizer());

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
