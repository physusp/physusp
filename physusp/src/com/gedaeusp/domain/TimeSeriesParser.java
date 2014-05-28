package com.gedaeusp.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.LinkedHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TimeSeriesParser<T extends Unit> {

	private Log log = LogFactory.getLog(this.getClass());
	
	public LinkedHashMap<Integer, UnitValue<T>> parse(String string, T unit) throws ParseException {
		LinkedHashMap<Integer, UnitValue<T>> result = new LinkedHashMap<Integer, UnitValue<T>>();
		BufferedReader reader = new BufferedReader(new StringReader(string));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				if(line.isEmpty())
					continue;
				String[] data = line.split(",");
				int time = Time.convertDateToSeconds(data[0]);
				result.put(time, new UnitValue<T>(Double.parseDouble(data[1]), unit));
			}
		} catch (Exception e) {
			log.error("Error reading file: " + e.getMessage());
			throw new DomainException("Failed to read data points.");
		}
		return result;
	}

}
