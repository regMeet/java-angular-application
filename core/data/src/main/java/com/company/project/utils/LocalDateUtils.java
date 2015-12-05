package com.company.project.utils;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

public class LocalDateUtils {
	private static final int THREE_HOUR = 3;

	public static Long getMilliSecondsFromLocalDate(LocalDate date) {
		return date.toDateTimeAtStartOfDay(DateTimeZone.UTC).toDate().getTime();
	}

	public static Long getMilliSecondsFromDateTime(DateTime date) {
		return date.toDateTime(DateTimeZone.UTC).toDate().getTime();
	}

	public static LocalDate getLocalDateFromMilliSeconds(Long millis) {
		return new DateTime(millis, DateTimeZone.UTC).toLocalDate();
	}

	public static DateTime getDateTimeFromMilliSeconds(Long millis) {
		return new DateTime(millis, DateTimeZone.UTC);
	}

	public static boolean isLocalDateInThePast(Long millis) {
		LocalDate targetDate = getLocalDateFromMilliSeconds(millis);
		LocalDate now = LocalDate.now();
		return targetDate.isBefore(now);
	}

	public static boolean isLocalDateInTheFuture(Long millis) {
		LocalDate targetDate = getLocalDateFromMilliSeconds(millis);
		LocalDate now = LocalDate.now();
		return targetDate.isAfter(now);
	}

	public static boolean isDateTimeInThePast(Long millis) {
		DateTime targetDate = getDateTimeFromMilliSeconds(millis);
		DateTime now = DateTime.now();
		return targetDate.isBefore(now);
	}

	public static LocalDate getTodayLocalDate() {
		return new LocalDate(DateTimeZone.UTC);
	}

	public static DateTime getTodayDateTime() {
		return new DateTime(DateTimeZone.UTC);
	}

	public static Date getTodayDate() {
		return new DateTime(DateTimeZone.UTC).toDate();
	}

	public static Long getTodayInMillis() {
		return new DateTime(DateTimeZone.UTC).toDate().getTime();
	}

	public static boolean isInPastMonth(LocalDate date) {
		LocalDate pastMonth = LocalDate.now().minusDays(30);
		return date.isAfter(pastMonth);
	}

	public static DateTime getDateTimeFromDate(Date date) {
		return new DateTime(date);
	}

	public static Date getDateFromDateTime(DateTime dateTime) {
		return dateTime.toDate();
	}

	public static boolean isWithin3Hours(Date date) {
		Date past3Hours = new DateTime(DateTimeZone.UTC).minusHours(THREE_HOUR).toDate();
		return date.after(past3Hours);

	}
}
