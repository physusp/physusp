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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Time {

	@SuppressWarnings("deprecation")
	public static Integer convertDateToSeconds(String string) throws ParseException {
	    DateFormat formatter = new SimpleDateFormat("hh:mm:ss");  
	    Date date = (Date)formatter.parse(string);
	    return date.getHours()*3600+date.getMinutes()*60+date.getSeconds();

	}
	
	public static Integer timeRange(List<Integer> time) {
		Integer timeRange = 0;
		for (int i = 0; i < time.size() - 1; i++) {
			timeRange += (time.get(i + 1) - time.get(i));
		}
		return timeRange;
	}

}
