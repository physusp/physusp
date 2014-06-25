/*
Copyright 2014 António Miranda, Caio Valente, Igor Topcin, Jorge Melegati, Thales Paiva, Victor Santos

This file is part of PhysUSP.

PhysUSP is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

PhysUSP is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PhysUSP. If not, see <http://www.gnu.org/licenses/>.
*/

package com.gedaeusp.models;

import java.io.Serializable;

import com.gedaeusp.domain.EnergyUnit;
import com.gedaeusp.domain.UnitValue;
import com.gedaeusp.domain.FlowUnit;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@SuppressWarnings("serial")
@XStreamAlias("consumption")
public class EnergyConsumptionResponse implements Serializable {

	@XStreamAlias("aerobic")
	private UnitValue<EnergyUnit> aerobic;
	
	@XStreamAlias("anaerobicLactic")
	private UnitValue<EnergyUnit> anaerobicLactic;
	
	@XStreamAlias("anaerobicAlactic")
	private UnitValue<EnergyUnit> anaerobicAlactic;
	
	@XStreamAlias("expectedOxygenConsumption")
	private UnitValue<FlowUnit>[] expectedOxygenConsumptions;
	
	@XStreamAlias("rSquared")
	private double rSquared;
	
	@XStreamAlias("v0")
	private UnitValue<FlowUnit> v0;
	
	@XStreamAlias("t0")
	private double t0;
	
	@XStreamAlias("a1")
	private UnitValue<FlowUnit> a1;
	
	@XStreamAlias("a2")
	private UnitValue<FlowUnit> a2;
	
	@XStreamAlias("tau1")
	private double tau1;
	
	@XStreamAlias("tau2")
	private double tau2;
	
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
	public UnitValue<FlowUnit>[] getExpectedOxygenConsumptions() {
		return expectedOxygenConsumptions;
	}
	public void setExpectedOxygenConsumptions(UnitValue<FlowUnit>[] expectedOxygenConsumptions) {
		this.expectedOxygenConsumptions = expectedOxygenConsumptions;
	}
	public double getRSquared() {
		return rSquared;
	}
	public void setRSquared(double rSquared) {
		this.rSquared = rSquared;
	}
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
	public UnitValue<FlowUnit> getA1() {
		return a1;
	}
	public void setA1(UnitValue<FlowUnit> a1) {
		this.a1 = a1;
	}
	public UnitValue<FlowUnit> getA2() {
		return a2;
	}
	public void setA2(UnitValue<FlowUnit> a2) {
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
	
}
