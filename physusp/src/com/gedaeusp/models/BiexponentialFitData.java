package com.gedaeusp.models;

import com.gedaeusp.domain.UnitValue;
import com.gedaeusp.domain.VolumeUnit;

public class BiexponentialFitData {
	private UnitValue<VolumeUnit> v0;
	private double t0;
	private UnitValue<VolumeUnit> a1;
	private UnitValue<VolumeUnit> a2;
	private double tau1;
	private double tau2;
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
	public UnitValue<VolumeUnit> getA1() {
		return a1;
	}
	public void setA1(UnitValue<VolumeUnit> a1) {
		this.a1 = a1;
	}
	public UnitValue<VolumeUnit> getA2() {
		return a2;
	}
	public void setA2(UnitValue<VolumeUnit> a2) {
		this.a2 = a2;
	}
	public double getTau1() {
		return tau1;
	}
	public void setTau1(double tau1) {
		this.tau1 = tau1;
	}
	public double getTau2() {
		return tau2;
	}
	public void setTau2(double tau2) {
		this.tau2 = tau2;
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
