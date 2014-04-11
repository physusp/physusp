package com.gedaeusp.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.math3.fitting.CurveFitter;
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.util.FastMath;
import org.junit.Test;

public class AnaerobicAlacticTest {

	private static final double ERROR = 0.1;

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
		double v0 = 600;
		double a1 = 1700;
		double tau1 = 40;
		double a2 = 200;
		double tau2 = 140;

		Biexponential exp = new Biexponential(v0, t0, a1, a2, tau1, tau2);

		CurveFitter<Biexponential.Parametric> fitter = new CurveFitter<Biexponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		CurveFitter<Exponential.Parametric> initFitter = new CurveFitter<Exponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		log.debug("Observed points:");
		for (int i = 0; i <= 600; i++) {
			double value = exp.value(i);
			fitter.addObservedPoint(i, value);
			initFitter.addObservedPoint(i, value);
			log.debug("(" + i + ", " + value + ")");
		}

		double[] initBest = initFitter.fit(new Exponential.Parametric(),
				new double[] { 1, 1, 1 });
		log.debug("init v0 = " + initBest[0]);
		log.debug("init A = " + initBest[1]);
		log.debug("init tau = " + initBest[2]);
		double initV0 = initBest[0];
		double initA = initBest[1];
		double initTau = initBest[2];
		double initT0 = 0;

		double[] init = { initV0,
				initT0, // could be Collections.min(times)
				initA * 0.5 * FastMath.exp(-1 / initTau), initA * 0.5,
				initTau * 0.9, initTau * 1.1 };
		for (double d : init) {
			log.debug(d);
		}
		// double[] init = { initV0, initT0, 1, 1, 1, 1 };

		double[] best = fitter.fit(new Biexponential.Parametric(), init);

		log.debug("Resultados da regressão:");
		log.debug("VO2_base = " + best[0]);
		log.debug("t0 = " + best[1]);
		log.debug("A1 = " + best[2]);
		log.debug("A2 = " + best[3]);
		log.debug("tau1 = " + best[4]);
		log.debug("tau2 = " + best[5]);

		assertTrue("Error for estimated v0 is too high",
				1 - best[0] / v0 < ERROR);
		assertTrue("Error for estimated A1 is too high",
				1 - best[2] / a1 < ERROR);
		assertTrue("Error for estimated tau1 is too high",
				1 - best[4] / tau1 < ERROR);
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

	@Test
	public void calculateRestAerobicComsumption() {
		List<UnitValue<FlowUnit>> consumption = new ArrayList<UnitValue<FlowUnit>>();
		List<Integer> times = new ArrayList<Integer>();

		double t0 = 5;
		double v0 = 600;
		double a1 = 1700;
		double tau1 = 40;
		double a2 = 200;
		double tau2 = 140;

		Biexponential exp = new Biexponential(v0, t0, a1, a2, tau1, tau2);

		for (int i = 0; i <= 600; i++) {
			consumption.add(new UnitValue<FlowUnit>(exp.value(i),
					FlowUnit.mlPerMinute));
			times.add(i);
		}

		log.debug(AnaerobicAlacticCalculator.calculateBiexponential(
				consumption, times,
				new UnitValue<FlowUnit>(v0, FlowUnit.mlPerMinute), (int) t0)
				.getValue(EnergyUnit.Kcal));
	}
}
