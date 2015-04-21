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

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.LinkedHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

public class TimeSeriesParser<T extends Unit> {

	private Log log = LogFactory.getLog(this.getClass());
	
	public LinkedHashMap<Integer, UnitValue<T>> parse(String string, T unit) {
		LinkedHashMap<Integer, UnitValue<T>> result = new LinkedHashMap<Integer, UnitValue<T>>();
		BufferedReader reader = new BufferedReader(new StringReader(string));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				if(StringUtils.isBlank(line))
					continue;
				String[] data = line.split(",");
				if (data.length != 2)
					continue;
				if (StringUtils.isBlank(data[0]) || "null".equals(data[0]))
					continue;
				if (StringUtils.isBlank(data[1]) || "null".equals(data[1]))
					continue;
				int time = Time.isTimeFormat(data[0]) ? Time.convertDateToSeconds(data[0]) : (int)Double.parseDouble(data[0]);
				result.put(time, new UnitValue<T>(Double.parseDouble(data[1]), unit));
			}
		} catch (Exception e) {
			log.error("Error reading file: ", e);
			throw new DomainException("Failed to read data points.", e);
		}
		return result;
	}

}
