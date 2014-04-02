package com.gedaeusp.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConverter {

	@SuppressWarnings("deprecation")
	public static long convertDateToSeconds(String string) throws ParseException {
	    DateFormat formatter = new SimpleDateFormat("hh:mm:ss");  
	    Date date = (Date)formatter.parse(string);
	    return date.getHours()*3600+date.getMinutes()*60+date.getSeconds();
	}

}
