package com.gedaeusp.domain;

public class BiexponentialFitData {
	private double v0;
	private double t0;
	private double a1;
	private double a2;
	private double tau1;
	private double tau2;
	private double rSquared;
	private double[] expectedOxygenConsumption;
	
	public double getV0() {
		return v0;
	}
	public void setV0(double v0) {
		this.v0 = v0;
	}
	public double getT0() {
		return t0;
	}
	public void setT0(double t0) {
		this.t0 = t0;
	}
	public double getA1() {
		return a1;
	}
	public void setA1(double a1) {
		this.a1 = a1;
	}
	public double getA2() {
		return a2;
	}
	public void setA2(double a2) {
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
	public double[] getExpectedOxygenConsumption() {
		return expectedOxygenConsumption;
	}
	public void setExpectedOxygenConsumption(double[] expectedOxygenConsumption) {
		this.expectedOxygenConsumption = expectedOxygenConsumption;
	}
	
	
	
}
