package com.gedaeusp.models;

import com.gedaeusp.domain.UnitValue;
import com.gedaeusp.domain.FlowUnit;

public class MonoexponentialFitData {
	private UnitValue<FlowUnit> v0;
	private double t0;
	private UnitValue<FlowUnit> a;
	private double tau;
	private double rSquared;
	private UnitValue<FlowUnit>[] expectedOxygenConsumptions;
	
	public UnitValue<FlowUnit> getV0() {
		return v0;
	}
	public void setV0(UnitValue<FlowUnit> v0) {
		this.v0 = v0;
	}
	public double getT0() {
		return t0;
	}
	public void setT0(double t0) {
		this.t0 = t0;
	}
	public UnitValue<FlowUnit> getA() {
		return a;
	}
	public void setA(UnitValue<FlowUnit> a) {
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
	public UnitValue<FlowUnit>[] getExpectedOxygenConsumptions() {
		return expectedOxygenConsumptions;
	}
	public void setExpectedOxygenConsumptions(UnitValue<FlowUnit>[] expectedOxygenConsumptions) {
		this.expectedOxygenConsumptions = expectedOxygenConsumptions;
	}
	
}
