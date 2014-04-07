package com.gedaeusp.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	public void testBiexponentialFunction() {
		double t0 = 5;
		double vo2 = 17;
		double a1 = 13;
		double tau1 = 7;
		double a2 = 23;
		double tau2 = 3;

		Biexponential exp = new Biexponential(vo2, t0, a1, a2, tau1, tau2);

		for (int i = 0; i <= 100; i++) {
			double expected = vo2 + a1 * FastMath.exp(-(i - t0) / tau1) + a2
					* FastMath.exp(-(i - t0) / tau2);
			double real = exp.value(i);
			assertEquals(real, expected, Constants.ANAEROBIC_ALACTIC_EPS);
		}
	}

	@Test
	public void testExponentialCurveFit() {
		double vo2 = 170;
		double a = 1300;
		double tau = 70;

		Exponential exp = new Exponential(vo2, a, tau);

		CurveFitter<Exponential.Parametric> fitter = new CurveFitter<Exponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		System.out.println("Observed points:");
		for (int i = 0; i <= 100; i++) {
			double value = exp.value(i);
			fitter.addObservedPoint(i, value);
			log.debug("(" + i + ", " + value + ")");
		}

		double[] init = { vo2, 10, 1 };

		double[] best = fitter.fit(new Exponential.Parametric(), init);

		log.debug("Resultados da regressão:");
		log.debug("VO2_base = " + best[0]);
		log.debug("A = " + best[1]);
		log.debug("tau = " + best[2]);

		assertEquals(vo2, best[0], Constants.ANAEROBIC_ALACTIC_EPS);
		assertEquals(a, best[1], Constants.ANAEROBIC_ALACTIC_EPS);
		assertEquals(tau, best[2], Constants.ANAEROBIC_ALACTIC_EPS);
	}

	@Test
	public void testBiexponentialCurveFit() {
		// TODO: Se trocarmos os a1/a2 e tau1/tau2, o teste provavelmente
		// falhará, apesar da curve fit estar correta.
		double t0 = 5;
		double vo2 = 17;
		double a1 = 13;
		double tau1 = 7;
		double a2 = 23;
		double tau2 = 3;

		Biexponential exp = new Biexponential(vo2, t0, a1, a2, tau1, tau2);
		
		CurveFitter<Biexponential.Parametric> fitter = new CurveFitter<Biexponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		CurveFitter<Exponential.Parametric> initFitter = new CurveFitter<Exponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		log.debug("Observed points:");
		for (int i = 0; i <= 100; i++) {
			double value = exp.value(i);
			fitter.addObservedPoint(i, value);
			initFitter.addObservedPoint(i, value);
			log.debug("(" + i + ", " + value + ")");
		}
		
		double[] initBest = initFitter.fit(new Exponential.Parametric(),
				new double[] { vo2, 1, 1 });
		log.debug("init v0 = " + initBest[0]);
		log.debug("init A = " + initBest[1]);
		log.debug("init tau = " + initBest[2]);
		
		double initT0 = 5; // Collections.min(times);
		double initV0 = vo2;
		double initTau1 = initBest[2] * 1.25;
		double initTau2 = initBest[2] * 0.75;
		double initA1, initA2;
		initA1 = initA2 = initBest[1] * 0.5 * FastMath.exp(-t0/initBest[2]);

		double[] init = { initV0, initT0, initA1, initA2, initTau1, initTau2 };
		//double[] init = { initV0, initT0, 1, 1, 1, 1 };

		double[] best = fitter.fit(new Biexponential.Parametric(), init);

		log.debug("Resultados da regressão:");
		log.debug("VO2_base = " + best[0]);
		log.debug("t0 = " + best[1]);
		log.debug("A1 = " + best[2]);
		log.debug("A2 = " + best[3]);
		log.debug("tau1 = " + best[4]);
		log.debug("tau2 = " + best[5]);

		assertEquals(vo2, best[0], Constants.ANAEROBIC_ALACTIC_EPS);
		assertEquals(t0, best[1], Constants.ANAEROBIC_ALACTIC_EPS);
		assertEquals(a1, best[2], Constants.ANAEROBIC_ALACTIC_EPS);
		assertEquals(a2, best[3], Constants.ANAEROBIC_ALACTIC_EPS);
		assertEquals(tau1, best[4], Constants.ANAEROBIC_ALACTIC_EPS);
		assertEquals(tau2, best[5], Constants.ANAEROBIC_ALACTIC_EPS);
	}

	// @Test
	public void testMonoexponentialWithBiexponentialCurveFit() {
		// TODO: Se trocarmos os a1/a2 e tau1/tau2, o teste provavelmente
		// falhará, apesar da curve fit estar correta.
		double vo2 = 17;
		double a1 = 13;
		double tau1 = 7;

		Exponential exp = new Exponential(vo2, a1, tau1);

		CurveFitter<Biexponential.Parametric> fitter = new CurveFitter<Biexponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		log.debug("Observed points:");
		for (int i = 0; i <= 100; i++) {
			double value = exp.value(i);
			fitter.addObservedPoint(i, value);
			log.debug("(" + i + ", " + value + ")");
		}

		double[] init = { vo2, 100, 100, 100, 100 };

		double[] best = fitter.fit(new Biexponential.Parametric(), init);

		log.debug("Resultados da regressão:");
		log.debug("VO2_base = " + best[0]);
		log.debug("A1 = " + best[1]);
		log.debug("A2 = " + best[2]);
		log.debug("tau1 = " + best[3]);
		log.debug("tau2 = " + best[4]);

		assertEquals(vo2, best[0], Constants.ANAEROBIC_ALACTIC_EPS);
		assertEquals(a1, best[2], Constants.ANAEROBIC_ALACTIC_EPS);
		assertEquals(tau1, best[4], Constants.ANAEROBIC_ALACTIC_EPS);
	}

	// @Test
	public void calculateRestAerobicComsumption() {
		List<Double> comsumption = new ArrayList<Double>();
		// TODO: Verificar se há necessidade de usar Double para escala de tempo
		List<Integer> time = new ArrayList<Integer>();

		double mass = 80;
		double v0 = 17;
		double a1 = 13;
		double tau1 = 7;
		Exponential exp = new Exponential(v0, a1, tau1);

		log.debug("Observed points:");
		for (int i = 0; i <= 100; i++) {
			double value = exp.value(i);
			comsumption.add(value);
			time.add(i);
			log.debug("(" + i + ", " + value + ")");

		}

		assertEquals(AnaerobicAlacticCalculator.calculate(comsumption, time,
				mass, v0), 305, Constants.ANAEROBIC_ALACTIC_EPS);
	}
}
