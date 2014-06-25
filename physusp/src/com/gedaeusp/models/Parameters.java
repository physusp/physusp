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

public class Parameters {

	private boolean calculateAerobic;
	private boolean calculateAnaerobicLactic;
	private boolean calculateAnaerobicAlactic;
	
	public boolean getCalculateAnaerobicAlactic() {
		return calculateAnaerobicAlactic;
	}
	public void setCalculateAnaerobicAlactic(boolean calculateAnaerobicAlactic) {
		this.calculateAnaerobicAlactic = calculateAnaerobicAlactic;
	}
	public boolean getCalculateAnaerobicLactic() {
		return calculateAnaerobicLactic;
	}
	public void setCalculateAnaerobicLactic(boolean calculateAnaerobicLactic) {
		this.calculateAnaerobicLactic = calculateAnaerobicLactic;
	}
	public boolean getCalculateAerobic() {
		return calculateAerobic;
	}
	public void setCalculateAerobic(boolean calculateAerobic) {
		this.calculateAerobic = calculateAerobic;
	}
}
