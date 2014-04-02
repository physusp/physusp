package com.gedaeusp.domain;

import org.apache.commons.math3.fitting.CurveFitter;
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.util.FastMath;
import org.junit.Test;

public class AnaerobicAlacticTest {

	@Test
	public void addCurveFit() {
		CurveFitter<ExponentialFunction> fitter = new CurveFitter<ExponentialFunction>(new LevenbergMarquardtOptimizer());
		
		System.out.println("test function values");
		for (int i = 0; i <= 100; i++) {
			double value = 17.0 + 13.0 * FastMath.exp((double) -i / 7.0);
			System.out.println(i + " = " + value);
			fitter.addObservedPoint(i, value);
		}
		
		double[] init = {17, 1, 1};
		
		double[] best = fitter.fit(new ExponentialFunction(), init);
		
		System.out.println("VO2_base = " + best[0]);
		System.out.println("A = " + best[1]);
		System.out.println("tau = " + best[2]);
	}

}
