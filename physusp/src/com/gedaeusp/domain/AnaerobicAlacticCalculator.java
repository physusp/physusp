package com.gedaeusp.domain;

import java.util.List;

import org.apache.commons.math3.fitting.CurveFitter;
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer;

public class AnaerobicAlacticCalculator {

	/**
	 * @param comsumption
	 *            Oxygen consumption in ml.
	 * @param time
	 *            In seconds.
	 * @param mass
	 *            Pacient mass in kg.
	 * @param v0
	 *            Baseline oxygen consumption in ml.
	 * @return Energy in KJ.
	 */
	public static double calculate(List<Double> consumption,
			List<Integer> time, double mass, double v0) {
		// TODO Reimplementar usando unidades genéricas (sendo implementado no
		// momento por Victor e Jorge)

		CurveFitter<Biexponential.Parametric> fitter = new CurveFitter<Biexponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		for (int i = 0; i < consumption.size() - 1; i++) {
			fitter.addObservedPoint(time.get(i), consumption.get(0));
		}

		double[] init = { v0, 100, 100, 100, 100 };

		double[] best = fitter.fit(new Biexponential.Parametric(), init);
		
		// TODO: Fazer os cálculos.
		return 0;
	}

}
