package com.gedaeusp.domain;

import java.util.List;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class AnaerobicAlacticCalculator {

	// private static Log log = LogFactory
	// .getLog(AnaerobicAlacticCalculator.class);

	private final NonlinearCurveFitter fitter;
	private double[] consumptionArray;
	private double[] timesArray;
	private double timeDelay;
	private UnitValue<FlowUnit> baselineOxygenVol;
	
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

	public void setExponentialInput(List<UnitValue<FlowUnit>> consumption, List<Integer> times,
			UnitValue<FlowUnit> baselineOxygenVol, double time){
		consumptionArray = toArray(consumption, FlowUnit.lPerSecond);
		timesArray = toArray(times);
		timeDelay = time;
		this.baselineOxygenVol = baselineOxygenVol;
			}
	
	public UnitValue<EnergyUnit> calculateEnergyWithBiExponential() {

		
		// Guess the initial parameters for the curve fitting
		double[] init = fitter.guessBiexponentialInitialParameters(
				consumptionArray, timesArray,
				baselineOxygenVol.getValue(FlowUnit.lPerSecond), timeDelay);

		// Do the biexponential fitting using the initial guessed parameters
		double[] best = fitter
				.doBixponentialFit(consumptionArray, timesArray, init);

		double a = best[Biexponential.PARAM_a1];
		double tau = best[Biexponential.PARAM_tau1];
		double energy = a * tau * Constants.OXYGEN_TO_KCAL;

		return new UnitValue<EnergyUnit>(energy, EnergyUnit.Kcal);
	}

	public UnitValue<EnergyUnit> calculateEnergyWithMonoExponential() {

		// Guess the initial parameters for the curve fitting
		double[] init = fitter.guessExponentialInitialParameters(
				consumptionArray, timesArray,
				baselineOxygenVol.getValue(FlowUnit.lPerSecond), timeDelay);

		// Do the monoexponential fitting using the initial guessed parameters
		double[] best = fitter.doDelayedExponentialFit(consumptionArray,
				timesArray, init);

		double a = best[DelayedExponential.PARAM_a];
		double tau = best[DelayedExponential.PARAM_tau];
		double energy = a * tau * Constants.OXYGEN_TO_KCAL;

		return new UnitValue<EnergyUnit>(energy, EnergyUnit.Kcal);
	}

}
