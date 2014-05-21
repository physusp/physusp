package com.gedaeusp.domain;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class UnitValueConverter implements Converter {

	@Override
	public boolean canConvert(Class clazz) {
		return clazz.equals(UnitValue.class);
	}
	
	@Override
	public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context) {
		UnitValue<?> unitValue = (UnitValue<?>) obj;
		Class<?> unitType = unitValue.getUnit().getClass();
		for(Object unitObj : unitType.getEnumConstants()) {
			Unit unit = (Unit)unitObj;
			writer.startNode(unit.toString());
			writer.setValue(Double.toString(unitValue.getValue(unitObj)));
			writer.endNode();
		}
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		return null;
	}

}
