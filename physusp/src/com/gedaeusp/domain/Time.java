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
