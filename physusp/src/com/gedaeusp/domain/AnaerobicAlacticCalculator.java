package com.gedaeusp.domain;

import java.util.List;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class AnaerobicAlacticCalculator {

	// private static Log log = LogFactory
	// .getLog(AnaerobicAlacticCalculator.class);

	private final NonlinearCurveFitter fitter;
	
	public AnaerobicAlacticCalculator(NonlinearCurveFitter fitter) {
		this.fitter = fitter;
	}
	
	private <T extends Unit> double[] toArray(List<UnitValue<T>> list, T unit) {
		double[] array = new double[list.size()];
		int i = 0;

		for (UnitValue<T> element : list) {
			array[i++] = element.getValue(unit);
		}

		return array;
	}

	private <T extends Number> double[] toArray(List<T> list) {
		double[] array = new double[list.size()];
		int i = 0;

		for (T element : list) {
			array[i++] = element.doubleValue();
		}

		return array;
	}

	public UnitValue<EnergyUnit> calculateEnergyWithBiExponential(
			List<UnitValue<FlowUnit>> consumption, List<Integer> times,
			UnitValue<FlowUnit> baselineOxygenVol, Integer timeDelay) {

		double[] consumptionArr = toArray(consumption, FlowUnit.lPerSecond);
		double[] timesArr = toArray(times);

		// Guess the initial parameters for the curve fitting
		double[] init = fitter.guessBiexponentialInitialParameters(
				consumptionArr, timesArr,
				baselineOxygenVol.getValue(FlowUnit.lPerSecond), timeDelay);

		// Do the biexponential fitting using the initial guessed parameters
		double[] best = fitter
				.doBixponentialFit(consumptionArr, timesArr, init);

		double a = best[Biexponential.PARAM_a1];
		double tau = best[Biexponential.PARAM_tau1];
		double energy = a * tau * Constants.OXYGEN_TO_KCAL;

		return new UnitValue<EnergyUnit>(energy, EnergyUnit.Kcal);
	}

	public UnitValue<EnergyUnit> calculateEnergyWithMonoExponential(
			List<UnitValue<FlowUnit>> consumption, List<Integer> times,
			UnitValue<FlowUnit> baselineOxygenVol, Integer timeDelay) {

		double[] consumptionArr = toArray(consumption, FlowUnit.lPerSecond);
		double[] timesArr = toArray(times);

		// Guess the initial parameters for the curve fitting
		double[] init = fitter.guessExponentialInitialParameters(
				consumptionArr, timesArr,
				baselineOxygenVol.getValue(FlowUnit.lPerSecond), timeDelay);

		// Do the monoexponential fitting using the initial guessed parameters
		double[] best = fitter.doDelayedExponentialFit(consumptionArr,
				timesArr, init);

		double a = best[DelayedExponential.PARAM_a];
		double tau = best[DelayedExponential.PARAM_tau];
		double energy = a * tau * Constants.OXYGEN_TO_KCAL;

		return new UnitValue<EnergyUnit>(energy, EnergyUnit.Kcal);
	}

}
