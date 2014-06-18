package com.gedaeusp.domain;

import java.util.List;

import com.gedaeusp.models.BiexponentialFitData;
import com.gedaeusp.models.MonoexponentialFitData;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class AnaerobicAlacticCalculator {


	private final NonlinearCurveFitter fitter;
	private double[] consumptionArray;
	private double[] timesArray;
	private double[] normalizedTimesArray;
	private double timeDelay;
	
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
			double time) {
		consumptionArray = toArray(consumption, FlowUnit.lPerSecond);
		timesArray = toArray(times);
		normalizedTimesArray = new double[timesArray.length];
		for(int i = 0; i < timesArray.length; i++)
		{
			normalizedTimesArray[i] = timesArray[i]-timesArray[0];
		}
		timeDelay = time;
	}
	
	public UnitValue<EnergyUnit> calculateEnergyWithBiexponential(BiexponentialFitData biexponentialFitData) {
		double[] init = fitter.guessBiexponentialInitialParameters(
				consumptionArray, normalizedTimesArray,
				0, timeDelay);

		double[] best = fitter.doBiexponentialFit(consumptionArray, normalizedTimesArray, init);
		
		double v0 = best[Biexponential.PARAM_v0];
		UnitValue<FlowUnit> _v0 = new UnitValue<FlowUnit>(v0, FlowUnit.lPerSecond);
		double t0 = best[Biexponential.PARAM_t0];
		double a1 = best[Biexponential.PARAM_a1];
		UnitValue<FlowUnit> _a1 = new UnitValue<FlowUnit>(a1, FlowUnit.lPerSecond);
		double a2 = best[Biexponential.PARAM_a2];
		UnitValue<FlowUnit> _a2 = new UnitValue<FlowUnit>(a2, FlowUnit.lPerSecond);
		double tau1 = best[Biexponential.PARAM_tau1];
		double tau2 = best[Biexponential.PARAM_tau2];
		
		biexponentialFitData.setV0(_v0);
		biexponentialFitData.setT0(t0);
		biexponentialFitData.setA1(_a1);
		biexponentialFitData.setA2(_a2);
		biexponentialFitData.setTau1(tau1);
		biexponentialFitData.setTau2(tau2);
		
		Biexponential biexponentialCalculator = new Biexponential(v0, t0, a1, a2, tau1, tau2);
		double[] expectedOxygenConsumptionValues = new double[normalizedTimesArray.length];
		
		@SuppressWarnings("unchecked")
		UnitValue<FlowUnit>[] expectedOxygenConsumption = new UnitValue[normalizedTimesArray.length];
		
		for (int i = 0; i < normalizedTimesArray.length; i++) {
			expectedOxygenConsumptionValues[i] = biexponentialCalculator.value(normalizedTimesArray[i]);
			expectedOxygenConsumption[i] = new UnitValue<FlowUnit>(expectedOxygenConsumptionValues[i], FlowUnit.lPerSecond);
		}
		biexponentialFitData.setExpectedOxygenConsumptions(expectedOxygenConsumption);
		
		RSquaredCalculator rSquaredCalculator = new RSquaredCalculator();
		double rSquared = rSquaredCalculator.calculate(consumptionArray, expectedOxygenConsumptionValues);
		biexponentialFitData.setRSquared(rSquared);
		
		
		return calculateEnergy(best[Biexponential.PARAM_a1], best[Biexponential.PARAM_tau1]);
	}
	
	public UnitValue<EnergyUnit> calculateEnergyWithMonoexponential(MonoexponentialFitData monoexponentialFitData){
		double[] init = fitter.guessExponentialInitialParameters(
				consumptionArray, normalizedTimesArray,
				0, timeDelay);

		double[] best = fitter.doDelayedExponentialFit(consumptionArray, normalizedTimesArray, init);

		double v0 = best[DelayedExponential.PARAM_v0];
		UnitValue<FlowUnit> _v0 = new UnitValue<FlowUnit>(v0, FlowUnit.lPerSecond);
		double t0 = best[DelayedExponential.PARAM_t0];
		double a = best[DelayedExponential.PARAM_a];
		UnitValue<FlowUnit> _a = new UnitValue<FlowUnit>(a, FlowUnit.lPerSecond);
		double tau = best[DelayedExponential.PARAM_tau];

		monoexponentialFitData.setV0(_v0);
		monoexponentialFitData.setT0(t0);
		monoexponentialFitData.setA(_a);
		monoexponentialFitData.setTau(tau);
		
		DelayedExponential monoexponentialCalculator = new DelayedExponential(v0, a, tau, t0);
		double[] expectedOxygenConsumptionValues = new double[normalizedTimesArray.length];
		
		@SuppressWarnings("unchecked")
		UnitValue<FlowUnit>[] expectedOxygenConsumption = new UnitValue[normalizedTimesArray.length];
		
		for (int i = 0; i < normalizedTimesArray.length; i++) {
			expectedOxygenConsumptionValues[i] = monoexponentialCalculator.value(normalizedTimesArray[i]);
			expectedOxygenConsumption[i] = new UnitValue<FlowUnit>(expectedOxygenConsumptionValues[i], FlowUnit.lPerSecond);
		}
		monoexponentialFitData.setExpectedOxygenConsumptions(expectedOxygenConsumption);
		
		RSquaredCalculator rSquaredCalculator = new RSquaredCalculator();
		double rSquared = rSquaredCalculator.calculate(consumptionArray, expectedOxygenConsumptionValues);
		monoexponentialFitData.setRSquared(rSquared);

		
		return calculateEnergy(best[DelayedExponential.PARAM_a], best[DelayedExponential.PARAM_tau]);
	}

	public UnitValue<EnergyUnit> calculateEnergy(double a, double tau) {
		double energy = a * tau * Constants.OXYGEN_TO_KCAL;
		return new UnitValue<EnergyUnit>(energy, EnergyUnit.Kcal);
	}
}
