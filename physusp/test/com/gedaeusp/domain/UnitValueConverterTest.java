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
