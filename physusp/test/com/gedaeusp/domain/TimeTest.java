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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TimeTest {
	
	@Test
	public void timeConverterTest() throws ParseException {
		assertEquals(Time.convertDateToSeconds("02:01:24"), (Integer) 7284);
	}
	
	
	@Test
	public void timeRangeTest()
	{
		List<Integer> time = new ArrayList<Integer>();
		time.add((int) 10);
		time.add((int) 18);
		time.add((int) 133);
		time.add((int) 143);
		time.add((int) 150);
		assertEquals(Time.timeRange(time), (Integer) 140);
	
	}
	
}
