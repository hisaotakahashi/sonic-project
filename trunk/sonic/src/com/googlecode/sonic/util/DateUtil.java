/*
 * Copyright 2012 the Sonic Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.sonic.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * 日付ユーティリティクラス
 * @author hisao takahashi
 * @since 1.0
 */
public class DateUtil {

	/** フォーマット yyyyMMdd HHmmss */
	public static final String DATE_FORMAT_01 = "yyyyMMdd HHmmss";
	/** フォーマット yyyyMMdd HHmm */
	public static final String DATE_FORMAT_02 = "yyyyMMdd HHmm";
	/** フォーマット yyyyMMdd */
	public static final String DATE_FORMAT_03 = "yyyyMMdd";
	/** フォーマット yyyyMd Hms */
	public static final String DATE_FORMAT_04 = "yyyyMd Hms";
	/** フォーマット yyyyMd Hm */
	public static final String DATE_FORMAT_05 = "yyyyMd Hm";
	/** フォーマット yyyyMd */
	public static final String DATE_FORMAT_06 = "yyyyMd";
	/** フォーマット(ハイフンタイプ) yyyy-MM-dd HH:mm:ss */
	public static final String DATE_FORMAT_HYPHEN_01 = "yyyy-MM-dd HH:mm:ss";
	/** フォーマット(ハイフンタイプ) yyyy-MM-dd(EEE) kk:mm:ss */
	public static final String DATE_FORMAT_HYPHEN_011 = "yyyy-MM-dd(EEE) kk:mm:ss";
	/** フォーマット(ハイフンタイプ) yyyy-MM-dd HH:mm */
	public static final String DATE_FORMAT_HYPHEN_02 = "yyyy-MM-dd HH:mm";
	/** フォーマット(ハイフンタイプ) yyyy-MM-dd */
	public static final String DATE_FORMAT_HYPHEN_03 = "yyyy-MM-dd";
	/** フォーマット(ハイフンタイプ) yyyy-M-d H:m:s */
	public static final String DATE_FORMAT_HYPHEN_04 = "yyyy-M-d H:m:s";
	/** フォーマット(ハイフンタイプ) yyyy-M-d H:m */
	public static final String DATE_FORMAT_HYPHEN_05 = "yyyy-M-d H:m";
	/** フォーマット(ハイフンタイプ) yyyy-M-d */
	public static final String DATE_FORMAT_HYPHEN_06 = "yyyy-M-d";
	/** フォーマット(スラッシュタイプ) yyyy/MM/dd HH:mm:ss */
	public static final String DATE_FORMAT_SLASH_01 = "yyyy/MM/dd HH:mm:ss";
	/** フォーマット(スラッシュタイプ) yyyy/MM/dd HH:mm */
	public static final String DATE_FORMAT_SLASH_02 = "yyyy/MM/dd HH:mm";
	/** フォーマット(スラッシュタイプ) yyyy/MM/dd */
	public static final String DATE_FORMAT_SLASH_03 = "yyyy/MM/dd";
	/** フォーマット(スラッシュタイプ) yyyy/M/d H:m:s */
	public static final String DATE_FORMAT_SLASH_04 = "yyyy/M/d H:m:s";
	/** フォーマット(スラッシュタイプ) yyyy/M/d H:m */
	public static final String DATE_FORMAT_SLASH_05 = "yyyy/M/d H:m";
	/** フォーマット(スラッシュタイプ) yyyy/M/d */
	public static final String DATE_FORMAT_SLASH_06 = "yyyy/M/d";
	/** フォーマット(ドットタイプ) yyyy.MM.dd HH:mm:ss */
	public static final String DATE_FORMAT_DOT_01 = "yyyy.MM.dd HH:mm:ss";
	/** フォーマット(ドットタイプ) yyyy.MM.dd HH:mm */
	public static final String DATE_FORMAT_DOT_02 = "yyyy.MM.dd HH:mm";
	/** フォーマット(ドットタイプ) yyyy.MM.dd */
	public static final String DATE_FORMAT_DOT_03 = "yyyy.MM.dd";
	/** フォーマット(ドットタイプ) yyyy.M.d H:m:s */
	public static final String DATE_FORMAT_DOT_04 = "yyyy.M.d H:m:s";
	/** フォーマット(ドットタイプ) yyyy.M.d H:m */
	public static final String DATE_FORMAT_DOT_05 = "yyyy.M.d H:m";
	/** フォーマット(ドットタイプ) yyyy.M.d */
	public static final String DATE_FORMAT_DOT_06 = "yyyy.M.d";
	/** フォーマット(漢字タイプ) yyyy年MM月dd日 HH時mm分ss秒 */
	public static final String DATE_FORMAT_KANJI_01 = "yyyy年MM月dd日 HH時mm分ss秒";
	/** フォーマット(漢字タイプ) yyyy年MM月dd日 HH時mm分 */
	public static final String DATE_FORMAT_KANJI_02 = "yyyy年MM月dd日 HH時mm分";
	/** フォーマット(漢字タイプ) yyyy年MM月dd日 */
	public static final String DATE_FORMAT_KANJI_03 = "yyyy年MM月dd日";
	/** フォーマット(漢字タイプ) yyyy年M月d日 H時m分s秒 */
	public static final String DATE_FORMAT_KANJI_04 = "yyyy年M月d日 H時m分s秒";
	/** フォーマット(漢字タイプ) yyyy年M月d日 H時m分 */
	public static final String DATE_FORMAT_KANJI_05 = "yyyy年M月d日 H時m分";
	/** フォーマット(漢字タイプ) yyyy年M月d日 */
	public static final String DATE_FORMAT_KANJI_06 = "yyyy年M月d日";
	/** フォーマット(漢字タイプ) yyyy年M月 */
	public static final String DATE_FORMAT_KANJI_07 = "yyyy年M月";
	/** フォーマット(漢字タイプ) yyyy年M月 */
	public static final String DATE_FORMAT_KANJI_08 = "M月d日";

	/**
	 * 当日日時を返却する。
	 * 
	 * @return
	 */
	public static Date getNow() {
		// TimeZone tz = TimeZone.getTimeZone("Asia/Tokyo");
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * 当日年を返却する。
	 * 
	 * @return
	 */
	public static int getYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 当日月を返却する。
	 * 
	 * @return
	 */
	public static int getMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 当日日を返却する。
	 * 
	 * @return
	 */
	public static int getDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 当日時間(24時間単位)を返却する。
	 * 
	 * @return
	 */
	public static int getHourOfDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 当日分を返却する。
	 * 
	 * @return
	 */
	public static int getMinute() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 当日秒を返却する。
	 * 
	 * @return
	 */
	public static int getSecond() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 引数の年、月、日でDateクラスを生成して返却する。 時間、分、秒はそれぞれ0
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * 
	 * @return Date
	 */
	public static Date getDate(int yyyy, int mm, int dd) {
		Calendar calendar = new GregorianCalendar(yyyy, (mm - 1), dd, 0, 0, 0);
		return calendar.getTime();
	}

	/**
	 * 
	 * 引数の年、月に指定年数をプラスしたカレンダーを返却する。
	 * 
	 * @param year
	 * @param month
	 * @param addYear
	 * @return
	 */
	public static Calendar getAddYear(int year, int month, int addYear) {
		Calendar calendar = new GregorianCalendar(year, (month - 1), 1, 0, 0, 0);
		calendar.add(Calendar.YEAR, addYear);
		return calendar;
	}

	/**
	 * 
	 * 引数の年、月に指定月数をプラスしたカレンダーを返却する。
	 * 
	 * @param year
	 * @param month
	 * @param addMonth
	 * @return
	 */
	public static Calendar getAddMonth(int year, int month, int addMonth) {

		Calendar calendar = new GregorianCalendar(year, (month - 1), 1, 0, 0, 0);
		calendar.add(Calendar.MONTH, addMonth);

		return calendar;
	}

	/**
	 * 
	 * 引数の年、月、日に指定日数をプラスしたカレンダーを返却する。
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param addDay
	 * @return
	 */
	public static Calendar getAddDate(int year, int month, int day, int addDay) {

		Calendar calendar = new GregorianCalendar(year, (month - 1), day, 0, 0,
				0);
		calendar.add(Calendar.DATE, addDay);

		return calendar;
	}

	/**
	 * 引数の年、月、日、時、分、秒に指定日数をプラスしたカレンダーを返却する。
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minutes
	 * @param second
	 * @param addDay
	 * @return
	 */
	public static Calendar getAddDate(int year, int month, int day, int hour,
			int minutes, int second, int addDay) {

		Calendar calendar = new GregorianCalendar(year, (month - 1), day, hour,
				minutes, second);
		calendar.add(Calendar.DATE, addDay);

		return calendar;
	}

	/**
	 * 引数のdateに引数の時間、分、秒を付与して返却する。
	 * 
	 * @param date
	 * @param hour
	 * @param min
	 * @param sec
	 * 
	 * @return Date
	 */
	public static Date getAddDate(Date date, int hour, int min, int sec)
			throws Exception {
		int[] dates = DateUtil.toDateArray(date);
		Calendar calendar = new GregorianCalendar(dates[0], (dates[1] - 1),
				dates[2], hour, min, sec);
		return calendar.getTime();
	}

	/**
	 * 引数の年、月、日、時、分、秒に指定時間(分)をプラスしたカレンダーを返却する。
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minutes
	 * @param second
	 * @param addDay
	 * @return
	 */
	public static Calendar getAddMinutes(int year, int month, int day,
			int hour, int minutes, int second, int addMinutes) {

		Calendar calendar = new GregorianCalendar(year, (month - 1), day, hour,
				minutes, second);
		calendar.add(Calendar.MINUTE, addMinutes);

		return calendar;
	}

	/**
	 * 日付フォーマッタを返却する。
	 * 
	 * @param format
	 * @return DateFormat
	 */
	public static DateFormat getFormat(String format) {
		return new SimpleDateFormat(format);
	}

	/**
	 * 
	 * 年月の最終日を返却する。
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getLastDayOfMonth(int year, int month) {

		Calendar calendar = new GregorianCalendar(year, (month - 1), 1);

		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 
	 * 引数で与えられた日付が存在するか判定する。
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static boolean isDate(int year, int month, int day) {

		Calendar c = new GregorianCalendar();
		c.setLenient(false);
		c.set(year, month - 1, day);

		try {
			c.getTime();

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * 引数の年月日 時分が有効な日付か判定する。
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param min
	 * @return
	 */
	public static boolean isDate(int year, int month, int day, int hour, int min) {

		Calendar calendar = new GregorianCalendar();
		calendar.setLenient(false);
		calendar.set(year, month - 1, day, hour, min);

		try {
			calendar.getTime();

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * 引数の年月日 時分が有効な日付か判定する。
	 * 
	 * @param value
	 * @param format
	 * @return
	 */
	public static boolean isDate(String value, String format) {

		// 入力日付に英字が含まれている場合
		if (Pattern.compile("[a-zA-Z]").matcher(value).find()) {
			return false;
		}

		int[] array;
		try {
			array = toDateArray(value, format);
		} catch (Exception e) {
			return false;
		}

		return isDate(array[0], array[1], array[2], array[3], array[4]);
	}

	/**
	 * 引数の日付がstart以上end以下の日付か判定する。
	 * 
	 * @param date
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public static boolean checkBetween(Date date, Date start, Date end)
			throws Exception {
		if (start.equals(end) || start.before(end)) {
			if ((date.equals(start) || date.after(start))
					&& (date.equals(end) || date.before(end))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 引数の日付がstartより大きくendより小さい日付か判定する。
	 * 
	 * @param date
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public static boolean checkBeforeAfter(Date date, Date start, Date end)
			throws Exception {
		if (start.before(end)) {
			if (date.after(start) && date.before(end)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * start以上end以下の日付か判定する。
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public static boolean checkBetween(Date start, Date end) throws Exception {
		if (start.equals(end) || start.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * startより大きくendより小さい日付か判定する。
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public static boolean checkBeforeAfter(Date start, Date end)
			throws Exception {
		if (start.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 引数の日付(Date型)を解釈し、「年/月/日/時/分/秒」に分解したものを格納した配列を返却する。
	 * 
	 * @param value
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static int[] toDateArray(Date date) throws Exception {
		int[] ret = new int[6];
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		calendar.setTime(date);
		ret[0] = calendar.get(Calendar.YEAR);
		ret[1] = calendar.get(Calendar.MONTH) + 1;
		ret[2] = calendar.get(Calendar.DATE);
		ret[3] = calendar.get(Calendar.HOUR_OF_DAY);
		ret[4] = calendar.get(Calendar.MINUTE);
		ret[5] = calendar.get(Calendar.SECOND);
		return ret;
	}

	/**
	 * 引数の日付を指定した書式に従って解釈し、「年/月/日/時/分/秒」に分解したものを格納した配列を返却する。
	 * 
	 * @param value
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static int[] toDateArray(String value, String format)
			throws Exception {
		int[] ret = new int[6];
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		Date date = parseDate(value, format);
		calendar.setTime(date);
		ret[0] = calendar.get(Calendar.YEAR);
		ret[1] = calendar.get(Calendar.MONTH) + 1;
		ret[2] = calendar.get(Calendar.DATE);
		ret[3] = calendar.get(Calendar.HOUR_OF_DAY);
		ret[4] = calendar.get(Calendar.MINUTE);
		ret[5] = calendar.get(Calendar.SECOND);
		return ret;
	}

	// TODO because dirty and quickly
	public static Date addHour(String value, String format, int diff)
			throws Exception {
		int[] dates = toDateArray(value, format);
		Calendar calendar = new GregorianCalendar(dates[0], dates[1], dates[2],
				(dates[3] + (diff)), dates[4], dates[5]);
		return calendar.getTime();
	}

	/**
	 * 引数の日付(String型)を指定した書式に従って解釈し、Date型のオブジェクトを返却する。
	 * 
	 * @param value
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Date parseDate(String value, String format) throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		simpleDateFormat.applyPattern(format);
		simpleDateFormat.setLenient(false);
		return simpleDateFormat.parse(value);
	}

	/**
	 * 引数の日付(Date型)を指定した書式に従って解釈し、String型のオブジェクトを返却する。
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String toFormatString(Date date, String format) {
		// XXX timezone設定。
		TimeZone tz = TimeZone.getTimeZone("Asia/Tokyo");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		simpleDateFormat.setTimeZone(tz);
		simpleDateFormat.applyPattern(format);
		simpleDateFormat.setLenient(false);
		return simpleDateFormat.format(date);
	}

	/**
	 * date1を基準としたdate2との差分(単位：ミリ秒)を取得する。
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getMilliSecondsDiff(Date date1, Date date2) {
		return date1.getTime() - date2.getTime();
	}

	/**
	 * date1を基準としたdate2との差分(単位：秒)を取得する(秒に満たない時間は切り捨てる)。
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getSecondsDiff(Date date1, Date date2) {
		return getMilliSecondsDiff(date1, date2) / 1000;
	}

	/**
	 * date1を基準としたdate2との差分(単位：分)を取得する(分に満たない時間は切り捨てる)。
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getMinutesDiff(Date date1, Date date2) {
		return getSecondsDiff(date1, date2) / 60;
	}

	/**
	 * date1を基準としたdate2との差分(単位：時)を取得する(時に満たない時間は切り捨てる)。
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getHoursDiff(Date date1, Date date2) {
		return getMinutesDiff(date1, date2) / 60;
	}

	/**
	 * date1を基準としたdate2との差分(単位：日)を取得する(日に満たない時間は切り捨てる)。
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDaysDiff(Date date1, Date date2) {
		return getHoursDiff(date1, date2) / 24;
	}

}