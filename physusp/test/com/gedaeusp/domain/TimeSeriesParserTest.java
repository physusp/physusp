package com.gedaeusp.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;

public class TimeSeriesParserTest {

	@Test
	public void canReadEmptySeries() {
		TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();
		LinkedHashMap<Integer, UnitValue<FlowUnit>> series = null;
		series = parser.parse("", FlowUnit.lPerMinute);

		assertEquals(0, series.keySet().size());
		assertEquals(0, series.values().size());
	}

	@Test
	public void canReadBlankSeries() {
		TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();
		LinkedHashMap<Integer, UnitValue<FlowUnit>> series = null;
		series = parser.parse(" ", FlowUnit.lPerMinute);

		assertEquals(0, series.keySet().size());
		assertEquals(0, series.values().size());
	}

	@Test
	public void canReadOneLineCommaSeparatedSeries() {
		TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();
		LinkedHashMap<Integer, UnitValue<FlowUnit>> series;
		series = parser.parse("00:00:01,2", FlowUnit.lPerMinute);
		assertEquals(1, series.keySet().toArray()[0]);
		UnitValue<FlowUnit> value = new ArrayList<UnitValue<FlowUnit>>(
				series.values()).get(0);
		assertEquals(2, value.getValue(), 0.001);
	}

	@Test
	public void canReadSeveralLineCommaSeparatedSeries() {
		TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();
		LinkedHashMap<Integer, UnitValue<FlowUnit>> series;
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
		try {

			writer.write("00:00:01,2.01");
			writer.newLine();
			writer.write("00:00:02,3");
			writer.newLine();
			writer.write("00:00:03,4.1");
			writer.newLine();
			writer.flush();
		} catch (Exception e) {
			fail();
		}
		series = parser.parse(stringWriter.toString(), FlowUnit.lPerMinute);
		assertEquals(1, series.keySet().toArray()[0]);
		assertEquals(2, series.keySet().toArray()[1]);
		assertEquals(3, series.keySet().toArray()[2]);
		List<UnitValue<FlowUnit>> values = new ArrayList<UnitValue<FlowUnit>>(
				series.values());
		assertEquals(2.01, values.get(0).getValue(), 0.001);
		assertEquals(3, values.get(1).getValue(), 0.001);
		assertEquals(4.1, values.get(2).getValue(), 0.001);
	}

	@Test
	public void canReadNullSeries() {
		TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();
		LinkedHashMap<Integer, UnitValue<FlowUnit>> series = null;
		StringBuilder sb = new StringBuilder();
		sb.append("null,\n").append("null,null\n").append(",null\n")
				.append(" ,\n").append(", \n").append("00:00:01, \n")
				.append("00:00:01,null\n");
		series = parser.parse(sb.toString(), FlowUnit.lPerMinute);
		assertEquals(0, series.keySet().size());
		assertEquals(0, series.values().size());
	}

	@Test
	public void testWithGoodAndBadData() {
		TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();
		LinkedHashMap<Integer, UnitValue<FlowUnit>> series = null;
		StringBuilder sb = new StringBuilder();
		sb.append("00:00:00,0\n").append("00:00:01,1\n").append("00:00:02,2\n")
				.append("00:00:03,3\n").append(",null\n")
				.append("00:00:04,4\n").append(",null\n");
		series = parser.parse(sb.toString(), FlowUnit.lPerMinute);
		assertEquals(0, series.keySet().toArray()[0]);
		assertEquals(1, series.keySet().toArray()[1]);
		assertEquals(2, series.keySet().toArray()[2]);
		assertEquals(3, series.keySet().toArray()[3]);
		assertEquals(4, series.keySet().toArray()[4]);

		List<UnitValue<FlowUnit>> values = new ArrayList<UnitValue<FlowUnit>>(
				series.values());
		assertEquals(0, values.get(0).getValue(), 0.001);
		assertEquals(1, values.get(1).getValue(), 0.001);
		assertEquals(2, values.get(2).getValue(), 0.001);
		assertEquals(3, values.get(3).getValue(), 0.001);
		assertEquals(4, values.get(4).getValue(), 0.001);
		assertEquals(5, series.keySet().size());
		assertEquals(5, series.values().size());
	}

	@Test
	public void canReadEmptyWithCommaSeries() {
		TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();
		LinkedHashMap<Integer, UnitValue<FlowUnit>> series = null;
		series = parser.parse(",", FlowUnit.lPerMinute);
		assertEquals(0, series.keySet().size());
		assertEquals(0, series.values().size());
	}

	@Test
	public void canReadEmptyWithSpacesSeries() {
		TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();
		LinkedHashMap<Integer, UnitValue<FlowUnit>> series = null;
		series = parser.parse(" \t, \t", FlowUnit.lPerMinute);
		assertEquals(0, series.keySet().size());
		assertEquals(0, series.values().size());
	}

	@Test(expected = DomainException.class)
	public void testDomainException() throws ParseException {
		TimeSeriesParser<FlowUnit> parser = new TimeSeriesParser<FlowUnit>();
		parser.parse("banana,melancia", FlowUnit.lPerMinute);
	}
}
