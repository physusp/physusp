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

public class AnaerobicLacticParameters {

	private double restLactateConcentration;
	private double peakLactateConcentration;
	private double weight;
	
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getPeakLactateConcentration() {
		return peakLactateConcentration;
	}
	public void setPeakLactateConcentration(double peakLactateConcentration) {
		this.peakLactateConcentration = peakLactateConcentration;
	}
	public double getRestLactateConcentration() {
		return restLactateConcentration;
	}
	public void setRestLactateConcentration(double restLactateConcentration) {
		this.restLactateConcentration = restLactateConcentration;
	}
	
}
