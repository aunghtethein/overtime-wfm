package com.ot.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateConverter {
	
	public static Date convertStartDateToUtilDate(LocalDate startDate) throws ParseException {
		Date sdate = asDate(startDate);
		
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String str1=f.format(sdate);
		
		Date utilStartDate=new SimpleDateFormat("yyyy-MM-dd").parse(str1);
	
		return utilStartDate;
	}
	public static Date convertEndDateToUtilDate(LocalDate endDate) throws ParseException {
		Date edate = asDate(endDate);
		
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String str2=f.format(edate);
		
		Date utilEndDate=new SimpleDateFormat("yyyy-MM-dd").parse(str2);
	
		return utilEndDate;
	}
	
	public static Date asDate(LocalDate localDate) {
	    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
}

