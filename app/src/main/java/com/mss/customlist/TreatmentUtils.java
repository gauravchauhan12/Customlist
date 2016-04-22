package com.mss.customlist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.Activity;

public class TreatmentUtils {

	Activity mActivity;

	/**
	 * Constructor
	 * 
	 * @param activity
	 */
	public TreatmentUtils(Activity activity) {
		mActivity = activity;
	}

	public String getHourFromTimeStamps(long timeStamps) {
		System.out.println("" + timeStamps);
		Date date = new Date(timeStamps);
		DateFormat format = new SimpleDateFormat("HH:mm");
//		format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
		String formatted = format.format(date);
		System.out.println("" + formatted);
		return formatted;
	}

	@SuppressLint("SimpleDateFormat")
	public String getDateCurrentTimeZone(long timestamp) {
		try {
			System.out.println("" + timestamp);
			Calendar calendar = Calendar.getInstance();
			TimeZone tz = TimeZone.getDefault();
			calendar.setTimeInMillis(timestamp);
			calendar.add(Calendar.MILLISECOND,
					tz.getOffset(calendar.getTimeInMillis()));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date currenTimeZone = (Date) calendar.getTime();
			return sdf.format(currenTimeZone);
		} catch (Exception e) {
		}
		return "";
	}

	public long UnixTimestamp(String dt, String requiredFormat, String timezone) {
		SimpleDateFormat formatter;
		Date date = null;
		long unixtime;
		formatter = new SimpleDateFormat(requiredFormat);

		try {
			date = formatter.parse(dt);
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
		unixtime = date.getTime() / 1000L;
		return unixtime;
	}

	public static String getUnixToDateFormat(String date,
			String requiredFormat, String timezone) {
		try {
			Date d = new Date(Long.parseLong(date) * 1000);
			SimpleDateFormat sdf = new SimpleDateFormat(requiredFormat);
			sdf.setTimeZone(TimeZone.getTimeZone("Atlantic/Azores"));
			return sdf.format(d);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getUnixToDateFormat(String date, String requiredFormat) {
		try {
			Date d = new Date(Long.parseLong(date) * 1000);
			SimpleDateFormat sdf = new SimpleDateFormat(requiredFormat);
			return sdf.format(d);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getTimeInMillies(String string) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

		String inputString = string;

		Date date = sdf.parse(inputString);
		return date.getTime() + "";
	}

	public static long getUnixTimeDate(String date, String time) {

		String[] dateSplit = new String[3];
		String[] timeSplit = new String[2];

		dateSplit = date.split("/");
		int day = Integer.parseInt(dateSplit[0]);
		int month = Integer.parseInt(dateSplit[1]);
		int year = Integer.parseInt(dateSplit[2]);
		String timeString = time.split(" ")[0];
		String ampm = time.split(" ")[1];
		timeSplit = timeString.split(":");
		int hour = Integer.parseInt(timeSplit[0]);
		int min = Integer.parseInt(timeSplit[1]);

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR, hour);
		calendar.set(Calendar.MINUTE, min);
		if (ampm.equals("AM")) {
			calendar.set(Calendar.AM_PM, Calendar.AM);
		} else {
			calendar.set(Calendar.AM_PM, Calendar.PM);
		}

		long unixtime = calendar.getTimeInMillis() / 1000;
		return unixtime;
	}

	public static String getTimeFromUnix(long date, String format,
			String timezone) {
		long unixTime = date;
		String time = TreatmentUtils
				.getUnixToDateFormat("" + unixTime, format, timezone);
		return time;
	}

	public static long timeConversion(String date, String time) {
		// SimpleDateFormat dfm = new SimpleDateFormat("yyyyMMddHHmm");
		//
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(date.split("/")[2]));
		calendar.set(Calendar.MONTH, Integer.parseInt(date.split("/")[1]) - 1);
		calendar.set(Calendar.DAY_OF_MONTH,
				Integer.parseInt(date.split("/")[0]));
		calendar.set(Calendar.HOUR, Integer.parseInt(time.split(":")[0]));
		calendar.set(Calendar.MINUTE,
				Integer.parseInt(time.split(":")[1].split(" ")[0]));
		if (time.split(":")[1].split(" ")[1].equals("PM")) {
			calendar.set(Calendar.AM_PM, Calendar.PM);
		} else {
			calendar.set(Calendar.AM_PM, Calendar.AM);
		}
		calendar.set(Calendar.SECOND, 0);
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
		long timestamp = calendar.getTimeInMillis() / 1000;
		return timestamp;
	}

}
