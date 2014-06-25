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

public class AnaerobicAlacticParameters {

	private double timeDelay;
	private String oxygenConsumptionPostExercise;
	private int exponentialType;
	private boolean useTimeDelay;
	
	public boolean isUseTimeDelay() {
		return useTimeDelay;
	}
	public void setUseTimeDelay(boolean useTimeDelay) {
		this.useTimeDelay = useTimeDelay;
	}	
	public String getOxygenConsumptionPostExercise() {
		return oxygenConsumptionPostExercise;
	}
	public void setOxygenConsumptionPostExercise(String oxygenConsumptionPost) {
		this.oxygenConsumptionPostExercise = oxygenConsumptionPost;
	}
	public double getTimeDelay() {
		return timeDelay;
	}
	public void setTimeDelay(double timeDelayPost) {
		this.timeDelay = timeDelayPost;
	}
	
	public int getExponentialType() {
		return exponentialType;
	}
	public void setExponentialType(int exponentialType) {
		this.exponentialType = exponentialType;
	}
	
}
