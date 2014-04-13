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
		for (int i = 0; i <= 300; i++) {
			double value = exp.value(i);
			fitter.addObservedPoint(i, value);
			log.debug("(" + i + ", " + value + ")");
		}

		double[] init = { vo2, 1, 1 };

		Exponential.ParametricBuilder builder = new Exponential.ParametricBuilder()
				.fixedV0(true);
		double[] best = fitter.fit(builder.build(), init);

		log.debug("Resultados da regressão:");
		log.debug("VO2_base = " + best[Exponential.PARAM_v0]);
		log.debug("A = " + best[Exponential.PARAM_a]);
		log.debug("tau = " + best[Exponential.PARAM_tau]);

		assertEquals(vo2, best[Exponential.PARAM_v0],
				Constants.ANAEROBIC_ALACTIC_EPS);
		assertEquals(a, best[Exponential.PARAM_a],
				Constants.ANAEROBIC_ALACTIC_EPS);
		assertEquals(tau, best[Exponential.PARAM_tau],
				Constants.ANAEROBIC_ALACTIC_EPS);
	}

	/**
	 * This test is initialized with a simple exponential fitting, and then we
	 * use the result as the initial parameters for the delayed exponential
	 * curve fitting.
	 */
	@Test
	public void testDelayedExponentialCurveFit() {
		double v0 = 170;
		double t0 = 10;
		double a = 1300;
		double tau = 70;

		// Initialize points and baseline value
		List<Integer> times = new ArrayList<Integer>();
		List<UnitValue<FlowUnit>> consumption = new ArrayList<UnitValue<FlowUnit>>();
		UnitValue<FlowUnit> baselineOxygenVol = new UnitValue<FlowUnit>(v0,
				FlowUnit.mlPerMinute);

		DelayedExponential exp = new DelayedExponential(v0, a, tau, t0);

		System.out.println("Observed points:");
		for (int i = 0; i <= 600; i++) {
			double value = exp.value(i);
			log.debug("(" + i + ", " + value + ")");
			times.add(i);
			consumption
					.add(new UnitValue<FlowUnit>(value, FlowUnit.mlPerMinute));
		}

		// guess initial values
		double[] init = AnaerobicAlacticCalculator.guessExponentialParameters(
				consumption, times, baselineOxygenVol, (int) t0,
				FlowUnit.mlPerMinute);

		for (double d : init) {
			log.debug(d);
		}

		// do the curve fitting
		CurveFitter<DelayedExponential.Parametric> fitter = new CurveFitter<DelayedExponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		AnaerobicAlacticCalculator.addObservedPointsToFitter(consumption,
				times, fitter, FlowUnit.mlPerMinute);

		DelayedExponential.ParametricBuilder builder = new DelayedExponential.ParametricBuilder()
				.fixedT0(true).fixedV0(true);

		double[] best = fitter.fit(builder.build(), init);

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
		List<Integer> times = new ArrayList<Integer>();
		List<UnitValue<FlowUnit>> consumption = new ArrayList<UnitValue<FlowUnit>>();
		UnitValue<FlowUnit> baselineOxygenVol = new UnitValue<FlowUnit>(v0,
				FlowUnit.mlPerMinute);

		Biexponential exp = new Biexponential(v0, t0, a1, a2, tau1, tau2);

		System.out.println("Observed points:");
		for (int i = 0; i <= 600; i++) {
			double value = exp.value(i);
			log.debug("(" + i + ", " + value + ")");
			times.add(400+i);
			consumption
					.add(new UnitValue<FlowUnit>(value, FlowUnit.mlPerMinute));
		}

		// guess initial values
		// - we will not pass in an initial value for t0 parameter
		double[] init = AnaerobicAlacticCalculator
				.guessBiexponentialParameters(consumption, times,
						baselineOxygenVol, (int) t0, FlowUnit.mlPerMinute);

		for (double d : init) {
			log.debug(d);
		}

		// do the curve fitting
		// - we will try to find t0 without giving an initial guess
		Biexponential.ParametricBuilder builder = new Biexponential.ParametricBuilder()
				.fixedT0(true).fixedV0(true);

		CurveFitter<Biexponential.Parametric> fitter = new CurveFitter<Biexponential.Parametric>(
				new LevenbergMarquardtOptimizer());

		AnaerobicAlacticCalculator.addObservedPointsToFitter(consumption,
				times, fitter, FlowUnit.mlPerMinute);

		double[] best = fitter.fit(builder.build(), init);

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

		double actual = AnaerobicAlacticCalculator.calculateBiexponential(
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

		double actual = AnaerobicAlacticCalculator.calculateExponential(
				consumption, times,
				new UnitValue<FlowUnit>(v0, FlowUnit.mlPerMinute), (int) t0)
				.getValue(EnergyUnit.Kcal);

		double expected = (new UnitValue<FlowUnit>(a, FlowUnit.mlPerMinute))
				.getValue(FlowUnit.lPerSecond)
				* tau
				* Constants.OXYGEN_TO_KCAL;

		assertEquals("Expected and Actual energy values in KCal are different",
				expected, actual, Constants.ANAEROBIC_ALACTIC_EPS);
	}
}
