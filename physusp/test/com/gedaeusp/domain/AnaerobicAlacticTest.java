package com.gedaeusp.domain;

import static org.junit.Assert.assertEquals;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.math3.fitting.CurveFitter;
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.util.FastMath;
import org.junit.Test;

public class AnaerobicAlacticTest {

	private Log log = LogFactory.getLog(this.getClass());

	@Test
	public void testExponentialFunction() {
		double vo2 = 17;
		double a = 13;
		double tau = 7;
		
		Exponential exp = new Exponential(vo2, a, tau);
		
		for (int i = 0; i <= 100; i++) {
			double expected = vo2 + a * FastMath.exp((double) -i / tau);
			double real = exp.value(i);
			assertEquals(real, expected, Constants.ANAEROBIC_ALACTIC_EPS);
		}
	}
	
	@Test
	public void addCurveFit() {
		double vo2 = 17;
		double a = 13;
		double tau = 7;
		
		Exponential exp = new Exponential(vo2, a, tau);
		
		CurveFitter<Exponential.Parametric> fitter = new CurveFitter<Exponential.Parametric>(new LevenbergMarquardtOptimizer());

		System.out.println("Observed points:");
		for (int i = 0; i <= 100; i++) {
			double value = exp.value(i);
			fitter.addObservedPoint(i, value);
			log.debug("(" + i + ", " + value + ")");
		}
		
		double[] init = {vo2, 100, 100};
		
		double[] best = fitter.fit(new Exponential.Parametric(), init);
		
		log.debug("Resultados da regressão:");
		log.debug("VO2_base = " + best[0]);
		log.debug("A = " + best[1]);
		log.debug("tau = " + best[2]);
		
		assertEquals(vo2, best[0], Constants.ANAEROBIC_ALACTIC_EPS);
		assertEquals(a, best[1], Constants.ANAEROBIC_ALACTIC_EPS);
		assertEquals(tau, best[2], Constants.ANAEROBIC_ALACTIC_EPS);
	}
}
