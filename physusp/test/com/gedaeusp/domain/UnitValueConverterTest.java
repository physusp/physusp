package com.gedaeusp.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringWriter;

import org.junit.Test;

import com.thoughtworks.xstream.io.json.JsonWriter;

public class UnitValueConverterTest {

	@Test
	public void testCanConvert() {
		UnitValueConverter converter = new UnitValueConverter();
		assertTrue(converter.canConvert(UnitValue.class));
	}

	@Test
	public void testCannotConvert() {
		UnitValueConverter converter = new UnitValueConverter();
		assertFalse(converter.canConvert(Unit.class));
	}
	
	@Test
	public void testMarshal() {
		UnitValueConverter converter = new UnitValueConverter();
		UnitValue<FlowUnit> obj = new UnitValue<FlowUnit>(60, FlowUnit.lPerMinute);
		
		StringWriter stringWriter = new StringWriter();
		JsonWriter writer = new JsonWriter(stringWriter);
		
		converter.marshal(obj, writer, null);
		assertTrue(stringWriter.toString().equals("{\"mlPerSecond\": \"1000.0\"}{\n"
				+ "\"lPerSecond\": \"1.0\"}{\n"
				+ "\"mlPerMinute\": \"60000.0\"}{\n"
				+ "\"lPerMinute\": \"60.0\"}"));
	}

}
