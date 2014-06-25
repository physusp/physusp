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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UnitValueTest {
	@Test	
	public void convert100KgTo_g()
	{
		UnitValue<WeightUnit> weight = new UnitValue<WeightUnit>(100.0, WeightUnit.Kg);
		double weightIn_g = weight.getValue(WeightUnit.g);
		assertEquals(100000.0, weightIn_g, 0.000000001);
	}
	
	@Test	
	public void convert100lTo_ml()
	{
		UnitValue<VolumeUnit> volume = new UnitValue<VolumeUnit>(100.0, VolumeUnit.l);
		double volumeIn_ml = volume.getValue(VolumeUnit.ml);
		assertEquals(100000.0, volumeIn_ml, 0.000000001);
	}
	
	@Test	
	public void convert100mlTo_l()
	{
		UnitValue<VolumeUnit> volume = new UnitValue<VolumeUnit>(100.0, VolumeUnit.ml);
		double volumeIn_l = volume.getValue(VolumeUnit.l);
		assertEquals(0.1, volumeIn_l, 0.000000001);
	}
}
