package com.gedaeusp.models;

public class MonoexponentialFitData {
	private double v0;
	private double t0;
	private double a;
	private double tau;
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
	public double getA() {
		return a;
	}
	public void setA(double a) {
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
	public double[] getExpectedOxygenConsumption() {
		return expectedOxygenConsumption;
	}
	public void setExpectedOxygenConsumption(double[] expectedOxygenConsumption) {
		this.expectedOxygenConsumption = expectedOxygenConsumption;
	}
	
	
}
