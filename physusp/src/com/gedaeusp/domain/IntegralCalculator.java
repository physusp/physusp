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

package com.gedaeusp.domain;

import java.util.List;

public class IntegralCalculator<T extends Unit> {
	
	private List<Integer> domainPoints; 
	private List<UnitValue<T>> imagePoints;
	
	public IntegralCalculator(List<Integer> domainPoints, List<UnitValue<T>> imagePoints) {
		this.domainPoints = domainPoints;
		this.imagePoints = imagePoints;
	}
	
	public UnitValue<T> calculate() {
		T unit = imagePoints.get(0).getUnit();
		double value = 0;
		for (int i = 0; i < domainPoints.size() - 1; i++)
			value += (domainPoints.get(i + 1) - domainPoints.get(i)) *
					 (imagePoints.get(i).getValue(unit) + imagePoints.get(i + 1).getValue(unit)) / 2.0;		
		return new UnitValue<T>(value, unit);
	}
	
}
