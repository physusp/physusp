package com.gedaeusp.models;

import com.gedaeusp.domain.UnitValue;
import com.gedaeusp.domain.VolumeUnit;

public class MonoexponentialFitData {
	private UnitValue<VolumeUnit> v0;
	private double t0;
	private UnitValue<VolumeUnit> a;
	private double tau;
	private double rSquared;
	private UnitValue<VolumeUnit>[] expectedOxygenConsumption;
	
	public UnitValue<VolumeUnit> getV0() {
		return v0;
	}
	public void setV0(UnitValue<VolumeUnit> v0) {
		this.v0 = v0;
	}
	public double getT0() {
		return t0;
	}
	public void setT0(double t0) {
		this.t0 = t0;
	}
	public UnitValue<VolumeUnit> getA() {
		return a;
	}
	public void setA(UnitValue<VolumeUnit> a) {
		this.a = a;
	}
	public double getTau() {
		return tau;
	}
	public void setTau(double tau) {
		this.tau = tau;
	}
	public double getRSquared() {
		return rSquared;
	}
	public void setRSquared(double rSquared) {
		this.rSquared = rSquared;
	}
	public UnitValue<VolumeUnit>[] getExpectedOxygenConsumption() {
		return expectedOxygenConsumption;
	}
	public void setExpectedOxygenConsumption(UnitValue<VolumeUnit>[] expectedOxygenConsumption) {
		this.expectedOxygenConsumption = expectedOxygenConsumption;
	}
	
}
