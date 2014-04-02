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
		assertEquals(Time.timeRange(time), (Integer) 141);
	
	}
	
}
