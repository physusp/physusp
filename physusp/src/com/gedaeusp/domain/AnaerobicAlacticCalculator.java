package com.gedaeusp.domain;

import java.util.List;

import com.gedaeusp.models.BiexponentialFitData;
import com.gedaeusp.models.MonoexponentialFitData;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class AnaerobicAlacticCalculator {

	// private static Log log = LogFactory
	// .getLog(AnaerobicAlacticCalculator.class);

	private final NonlinearCurveFitter fitter;
	private double[] consumptionArray;
	private double[] timesArray;
	private double[] normalizedTimesArray;
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
			UnitValue<FlowUnit> baselineOxygenVol, double time) {
		consumptionArray = toArray(consumption, FlowUnit.lPerSecond);
		timesArray = toArray(times);
		normalizedTimesArray = new double[timesArray.length];
		for(int i = 0; i < timesArray.length; i++)
		{
			normalizedTimesArray[i] = timesArray[i]-timesArray[0];
		}
		timeDelay = time;
		this.baselineOxygenVol = baselineOxygenVol;
	}
	
	public UnitValue<EnergyUnit> calculateEnergyWithBiexponential(BiexponentialFitData biexponentialFitData) {
		// Guess the initial parameters for the curve fitting
		double[] init = fitter.guessBiexponentialInitialParameters(
				consumptionArray, normalizedTimesArray,
				baselineOxygenVol.getValue(FlowUnit.lPerSecond), timeDelay);

		// Do the biexponential fitting using the initial guessed parameters
		double[] best = fitter.doBiexponentialFit(consumptionArray, normalizedTimesArray, init);
		
		double v0 = best[Biexponential.PARAM_v0];
		double t0 = best[Biexponential.PARAM_t0];
		double a1 = best[Biexponential.PARAM_a1];
		double a2 = best[Biexponential.PARAM_a2];
		double tau1 = best[Biexponential.PARAM_tau1];
		double tau2 = best[Biexponential.PARAM_tau2];
		
		biexponentialFitData.setV0(v0);
		biexponentialFitData.setT0(t0);
		biexponentialFitData.setA1(a1);
		biexponentialFitData.setA2(a2);
		biexponentialFitData.setTau1(tau1);
		biexponentialFitData.setTau2(tau2);
		
		Biexponential biexponentialCalculator = new Biexponential(v0, t0, a1, a2, tau1, tau2);
		double[] expectedOxygenConsumption = new double[normalizedTimesArray.length];
		for (int i = 0; i < normalizedTimesArray.length; i++)
			expectedOxygenConsumption[i] = biexponentialCalculator.value(normalizedTimesArray[i]);
		biexponentialFitData.setExpectedOxygenConsumption(expectedOxygenConsumption);
		
		RSquaredCalculator rSquaredCalculator = new RSquaredCalculator();
		double rSquared = rSquaredCalculator.calculate(consumptionArray, expectedOxygenConsumption);
		biexponentialFitData.setRSquared(rSquared);
		
		for (int i = 0; i < normalizedTimesArray.length; i++)
		{
			expectedOxygenConsumption[i] *= 60000;
		}
		
		return calculateEnergy(best[Biexponential.PARAM_a1], best[Biexponential.PARAM_tau1]);
	}
	
	public UnitValue<EnergyUnit> calculateEnergyWithMonoexponential(MonoexponentialFitData monoexponentialFitData){
		// Guess the initial parameters for the curve fitting
		double[] init = fitter.guessExponentialInitialParameters(
				consumptionArray, normalizedTimesArray,
				baselineOxygenVol.getValue(FlowUnit.lPerSecond), timeDelay);

		// Do the monoexponential fitting using the initial guessed parameters
		double[] best = fitter.doDelayedExponentialFit(consumptionArray,
				normalizedTimesArray, init);

		double v0 = best[DelayedExponential.PARAM_v0];
		double t0 = best[DelayedExponential.PARAM_t0];
		double a = best[DelayedExponential.PARAM_a];
		double tau = best[DelayedExponential.PARAM_tau];

		monoexponentialFitData.setV0(v0);
		monoexponentialFitData.setT0(t0);
		monoexponentialFitData.setA(a);
		monoexponentialFitData.setTau(tau);
		
		DelayedExponential monoexponentialCalculator = new DelayedExponential(v0, a, tau, t0);
		double[] expectedOxygenConsumption = new double[normalizedTimesArray.length];
		for (int i = 0; i < normalizedTimesArray.length; i++)
			expectedOxygenConsumption[i] = monoexponentialCalculator.value(normalizedTimesArray[i]);
		monoexponentialFitData.setExpectedOxygenConsumption(expectedOxygenConsumption);
		
		RSquaredCalculator rSquaredCalculator = new RSquaredCalculator();
		double rSquared = rSquaredCalculator.calculate(consumptionArray, expectedOxygenConsumption);
		monoexponentialFitData.setRSquared(rSquared);
		
		for (int i = 0; i < normalizedTimesArray.length; i++)
		{
			expectedOxygenConsumption[i] = expectedOxygenConsumption[i]*60000;
		}
		
		return calculateEnergy(best[DelayedExponential.PARAM_a], best[DelayedExponential.PARAM_tau]);
	}

	public UnitValue<EnergyUnit> calculateEnergy(double a, double tau) {
		double energy = a * tau * Constants.OXYGEN_TO_KCAL;
		return new UnitValue<EnergyUnit>(energy, EnergyUnit.Kcal);
	}
}
