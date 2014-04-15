package com.gedaeusp.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.math3.util.FastMath;
import org.junit.Test;

public class AnaerobicAlacticCalculatorTest {

	private static final double ERROR = 0.1;

	private Log log = LogFactory.getLog(this.getClass());

	private NonlinearCurveFitter fitter = new NonlinearCurveFitter();
	
	private AnaerobicAlacticCalculator calculator = new AnaerobicAlacticCalculator(fitter);

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
		double v0 = 170;
		double a = 1300;
		double tau = 70;

		double[] v = new double[300];
		double[] t = new double[300];

		Exponential exp = new Exponential(v0, a, tau);

		System.out.println("Observed points:");
		for (int i = 0; i < 300; i++) {
			t[i] = i;
			v[i] = exp.value(i);
			log.debug("(" + t[i] + ", " + v[i] + ")");
		}

		double[] best = fitter.doExponentialFit(v, t, v0);

		log.debug("Resultados da regressão:");
		log.debug("VO2_base = " + best[Exponential.PARAM_v0]);
		log.debug("A = " + best[Exponential.PARAM_a]);
		log.debug("tau = " + best[Exponential.PARAM_tau]);

		assertEquals(v0, best[Exponential.PARAM_v0],
				Constants.ANAEROBIC_ALACTIC_EPS);
		assertEquals(a, best[Exponential.PARAM_a],
				Constants.ANAEROBIC_ALACTIC_EPS);
		assertEquals(tau, best[Exponential.PARAM_tau],
				Constants.ANAEROBIC_ALACTIC_EPS);
	}

	@Test
	public void testDelayedExponentialCurveFit() {
		double v0 = 170;
		double t0 = 10;
		double a = 1300;
		double tau = 70;

		double[] v = new double[600];
		double[] t = new double[600];

		DelayedExponential exp = new DelayedExponential(v0, a, tau, t0);

		System.out.println("Observed points:");
		for (int i = 0; i < 600; i++) {
			t[i] = i;
			v[i] = exp.value(i);
			log.debug("(" + t[i] + ", " + v[i] + ")");
		}

		// guess initial values
		double[] init = fitter.guessExponentialInitialParameters(v, t, v0, t0);
		for (double d : init) {
			log.debug(d);
		}

		// do the curve fitting
		double[] best = fitter.doDelayedExponentialFit(v, t, init);

		log.debug("Resultados da regressão:");
		log.debug("VO2_base = " + best[DelayedExponential.PARAM_v0]);
		log.debug("A = " + best[DelayedExponential.PARAM_a]);
		log.debug("tau = " + best[DelayedExponential.PARAM_tau]);
		log.debug("t0 = " + best[DelayedExponential.PARAM_t0]);

		assertEquals(v0, best[DelayedExponential.PARAM_v0],
				Constants.ANAEROBIC_ALACTIC_EPS);
		assertEquals(a, best[DelayedExponential.PARAM_a],
				Constants.ANAEROBIC_ALACTIC_EPS);
		assertEquals(tau, best[DelayedExponential.PARAM_tau],
				Constants.ANAEROBIC_ALACTIC_EPS);
		assertEquals(t0, best[DelayedExponential.PARAM_t0],
				Constants.ANAEROBIC_ALACTIC_EPS);
	}

	/**
	 * This test is initialized with a simple exponential fitting, and then we
	 * use the result as the initial parameters for the bi-exponential curve
	 * fitting.
	 */
	@Test
	public void testBiexponentialCurveFit() {
		double t0 = 5;
		double v0 = 600;
		double a1 = 1700;
		double tau1 = 40;
		double a2 = 200;
		double tau2 = 140;

		// Initialize points and baseline value
		double[] v = new double[600];
		double[] t = new double[600];

		Biexponential exp = new Biexponential(v0, t0, a1, a2, tau1, tau2);

		System.out.println("Observed points:");
		for (int i = 0; i < 600; i++) {
			t[i] = i;
			v[i] = exp.value(i);
			log.debug("(" + t[i] + ", " + v[i] + ")");
		}

		// guess initial values
		double[] init = fitter
				.guessBiexponentialInitialParameters(v, t, v0, t0);
		for (double d : init) {
			log.debug(d);
		}

		// do the curve fitting
		double[] best = fitter.doBixponentialFit(v, t, init);

		log.debug("Resultados da regressão:");
		log.debug("VO2_base = " + best[Biexponential.PARAM_v0]);
		log.debug("t0 = " + best[Biexponential.PARAM_t0]);
		log.debug("A1 = " + best[Biexponential.PARAM_a1]);
		log.debug("A2 = " + best[Biexponential.PARAM_a2]);
		log.debug("tau1 = " + best[Biexponential.PARAM_tau1]);
		log.debug("tau2 = " + best[Biexponential.PARAM_tau2]);

		assertTrue("Error for estimated v0 is too high", 1
				- best[Biexponential.PARAM_v0] / v0 < ERROR);
		assertTrue("Error for estimated A1 is too high", 1
				- best[Biexponential.PARAM_a1] / a1 < ERROR);
		assertTrue("Error for estimated tau1 is too high", 1
				- best[Biexponential.PARAM_tau1] / tau1 < ERROR);
	}

	/**
	 * Simulate points in a curve, and then calculate the off-exercise energy in
	 * Kcal using a biexponential fitting.
	 */
	@Test
	public void calculateSimulatedConsumptionWithBiexponential() {
		List<UnitValue<FlowUnit>> consumption = new ArrayList<UnitValue<FlowUnit>>();
		List<Integer> times = new ArrayList<Integer>();

		double t0 = 20;
		double v0 = 600;
		double a1 = 1700;
		double tau1 = 40;
		double a2 = 200;
		double tau2 = 140;

		Biexponential exp = new Biexponential(v0, t0, a1, a2, tau1, tau2);

		for (int i = 0; i <= 600; i += 2) {
			consumption.add(new UnitValue<FlowUnit>(exp.value(i),
					FlowUnit.mlPerMinute));
			times.add(i);
		}

		double actual = calculator.calculateEnergyWithBiExponential(
				consumption, times,
				new UnitValue<FlowUnit>(v0, FlowUnit.mlPerMinute), (int) t0)
				.getValue(EnergyUnit.Kcal);

		double expected = (new UnitValue<FlowUnit>(a1, FlowUnit.mlPerMinute))
				.getValue(FlowUnit.lPerSecond)
				* tau1
				* Constants.OXYGEN_TO_KCAL;

		assertEquals("Expected and Actual energy values in KCal are different",
				expected, actual, Constants.ANAEROBIC_ALACTIC_EPS);
	}

	/**
	 * Simulate points in a curve, and then calculate the off-exercise energy in
	 * Kcal using a monoexponential fitting.
	 */
	@Test
	public void calculateSimulatedConsumptionWithMonoexponential() {
		List<UnitValue<FlowUnit>> consumption = new ArrayList<UnitValue<FlowUnit>>();
		List<Integer> times = new ArrayList<Integer>();

		double t0 = 15;
		double v0 = 600;
		double a = 1700;
		double tau = 40;

		DelayedExponential exp = new DelayedExponential(v0, a, tau, t0);

		for (int i = 0; i <= 600; i += 2) {
			consumption.add(new UnitValue<FlowUnit>(exp.value(i),
					FlowUnit.mlPerMinute));
			times.add(i);
		}

		double actual = calculator.calculateEnergyWithMonoExponential(
				consumption, times,
				new UnitValue<FlowUnit>(v0, FlowUnit.mlPerMinute), (int) t0)
				.getValue(EnergyUnit.Kcal);

		double expected = (new UnitValue<FlowUnit>(a, FlowUnit.mlPerMinute))
				.getValue(FlowUnit.lPerSecond) * tau * Constants.OXYGEN_TO_KCAL;

		assertEquals("Expected and Actual energy values in KCal are different",
				expected, actual, Constants.ANAEROBIC_ALACTIC_EPS);
	}
}
