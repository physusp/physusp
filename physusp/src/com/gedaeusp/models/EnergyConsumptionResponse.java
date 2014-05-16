package com.gedaeusp.models;

import java.io.Serializable;
import java.util.List;

import com.gedaeusp.domain.EnergyUnit;
import com.gedaeusp.domain.UnitValue;
import com.gedaeusp.domain.VolumeUnit;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("consumption")
public class EnergyConsumptionResponse implements Serializable {

	@XStreamAlias("aerobic")
	private UnitValue<EnergyUnit> aerobic;
	
	@XStreamAlias("anaerobicLactic")
	private UnitValue<EnergyUnit> anaerobicLactic;
	
	@XStreamAlias("anaerobicAlactic")
	private UnitValue<EnergyUnit> anaerobicAlactic;
	
	@XStreamAlias("expectedOxygenConsumption")
	private double[] expectedOxygenConsumption;
	
	@XStreamAlias("rSquared")
	private double rSquared;
	
	@XStreamAlias("biexponentialFitCoefficients")
	private double[] biexponentialFitCoefficients;
	
	@XStreamAlias("monoexponentialFitCoefficients")
	private double[] monoexponentialFitCoefficients;
	
	public UnitValue<EnergyUnit> getAerobic() {
		return aerobic;
	}
	public void setAerobic(UnitValue<EnergyUnit> aerobic) {
		this.aerobic = aerobic;
	}
	public UnitValue<EnergyUnit> getAnaerobicLactic() {
		return anaerobicLactic;
	}
	public void setAnaerobicLactic(UnitValue<EnergyUnit> anaerobicLactic) {
		this.anaerobicLactic = anaerobicLactic;
	} 	
	public UnitValue<EnergyUnit> getAnaerobicAlactic() {
		return anaerobicAlactic;
	}
	public void setAnaerobicAlactic(UnitValue<EnergyUnit> anaerobicAlactic) {
		this.anaerobicAlactic = anaerobicAlactic;
	}
	public double[] getExpectedOxygenConsumption() {
		return expectedOxygenConsumption;
	}
	public void setExpectedOxygenConsumption(double[] expectedOxygenConsumption) {
		this.expectedOxygenConsumption = expectedOxygenConsumption;
	}
	public double getRSquared() {
		return rSquared;
	}
	public void setRSquared(double rSquared) {
		this.rSquared = rSquared;
	}
	public double[] getBiexponentialFitCoefficients() {
		return biexponentialFitCoefficients;
	}
	public void setBiexponentialFitCoefficients(
			double[] biexponentialFitCoefficients) {
		this.biexponentialFitCoefficients = biexponentialFitCoefficients;
	}
	public double[] getMonoexponentialFitCoefficients() {
		return monoexponentialFitCoefficients;
	}
	public void setMonoexponentialFitCoefficients(
			double[] monoexponentialFitCoefficients) {
		this.monoexponentialFitCoefficients = monoexponentialFitCoefficients;
	}
	
}
