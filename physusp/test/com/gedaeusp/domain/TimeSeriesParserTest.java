package com.gedaeusp.domain;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;


public class TimeSeriesParserTest {

	@Test
	public void canReadEmptySeries(){
		TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();
		LinkedHashMap<Integer, UnitValue<FlowUnit>> series = null;
		try {
			series = parser.parse("", FlowUnit.lPerMinute);
		} catch (ParseException e) {
			fail();
		}
		assertEquals(0, series.keySet().size());
		assertEquals(0, series.values().size());
	}
	
	@Test
	public void canReadOneLineCommaSeparatedSeries() {
		TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();
		LinkedHashMap<Integer, UnitValue<FlowUnit>> series;
		try {
			series = parser.parse("00:00:01,2", FlowUnit.lPerMinute);
			assertEquals(1, series.keySet().toArray()[0]);
			UnitValue<FlowUnit> value = new ArrayList<UnitValue<FlowUnit>>(series.values()).get(0);
			assertEquals(2, value.getValue(), 0.001);
		} catch (ParseException e) {
			fail();
		}
	}
	
	@Test
	public void canReadSeveralLineCommaSeparatedSeries() {
		TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();
		LinkedHashMap<Integer, UnitValue<FlowUnit>> series;
		try {
			StringWriter stringWriter = new StringWriter();
			BufferedWriter writer = new BufferedWriter(stringWriter);
			writer.write("00:00:01,2");
			writer.newLine();
			writer.write("00:00:02,3");
			writer.newLine();
			writer.write("00:00:03,4");
			writer.newLine();
			writer.flush();
			series = parser.parse(stringWriter.toString(), FlowUnit.lPerMinute);
			assertEquals(1, series.keySet().toArray()[0]);
			assertEquals(2, series.keySet().toArray()[1]);
			assertEquals(3, series.keySet().toArray()[2]);
			List<UnitValue<FlowUnit>> values = new ArrayList<UnitValue<FlowUnit>>(series.values());
			assertEquals(2, values.get(0).getValue(), 0.001);
			assertEquals(3, values.get(1).getValue(), 0.001);
			assertEquals(4, values.get(2).getValue(), 0.001);
		} catch (ParseException | IOException e) {
			fail();
		}
	}
	
}
