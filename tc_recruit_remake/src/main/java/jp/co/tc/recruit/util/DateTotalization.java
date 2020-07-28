package jp.co.tc.recruit.util;

import java.util.Calendar;
import java.util.Date;

public class DateTotalization {
	public static Integer checkDate(Date date) {
		Integer month = 0;
		Calendar calender = Calendar.getInstance();
		if(date != null) {
			calender.setTime(date);
			month = calender.get(Calendar.MONTH);
			return month + 1;
		}
		return null;
	}
}
