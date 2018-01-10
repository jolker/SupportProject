package com.bliss.core.lib.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
/**
 * 
 * @author tuyenpv
 *
 */
public class DateUtils {
	/**
	 * https://en.wikipedia.org/wiki/ISO_8601
	 */
	private static final String PATTERN_DATE = "yyyy-MM-dd'T'HH:mm:ss";

	private static final ThreadLocal<DateFormat> DATE_FORMAT = new ThreadLocal<DateFormat>() {
		@Override
		public DateFormat get() {
			DateFormat f = super.get();
			if (f == null) {
				f = new SimpleDateFormat(PATTERN_DATE);
				f.setTimeZone(TimeZone.getTimeZone("GMT+0"));
				set(f);
			}
			return f;
		}
	};

	public static Date getDateTime(String strDate) throws IllegalArgumentException {
		if (strDate != null) {
			try {
				return DATE_FORMAT.get().parse(strDate);
			} catch (ParseException e) {
				throw new IllegalArgumentException("failed to parse dateTime: " + strDate);
			}
		}
		return null;
	}

	public static String getDateTime(Date date) throws IllegalArgumentException {
		if (date == null)
			return null;
		return DATE_FORMAT.get().format(date);
	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static Date subtractDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -days);
		return cal.getTime();
	}

}
