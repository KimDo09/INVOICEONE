/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.Vector;

/**
 * Lop chua cac ham xu ly chung cho ngay thang (date time)
 * 
 * @author: Nhantd
 * @version: 1.0
 * @since: 1.0
 */
public abstract class DateUtils {

	// ---------------------------------------------------------------------------
	// Cac bien dung chung trong DatesUtil
	// ---------------------------------------------------------------------------

	private static final long SECOND = 1000;

	private static final long MINUTE = SECOND * 60;

	private static final long HOUR = MINUTE * 60;

	private static final long DAY = HOUR * 24;

	private static final String DATE_FORMAT_STANDARD = "yyyy-MM-dd HH:mm:ss";
	
	private static final String DATE_FORMAT_ISO8601 = "yyyy-MM-dd 'T' HH:mm:ss.SSS'Z'";

	private static final String[] DAY_IN_WEEK = { "Monday", "Tuesday",
			"Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

	private static final String[] DAY_IN_WEEK_VN = { "Thứ Hai", "Thứ Ba", "Thứ Tư",
			"Thứ Năm", "Thứ Sáu", "Thứ Bảy", "Chủ Nhật" };

	private static final String[] DAY_LINE_VN = { "T2", "T3", "T4", "T5", "T6",
			"T7", "CN" };

	// ---------------------------------------------------------------------------
	// Cac ham lam viec trong DatesUtil
	// ---------------------------------------------------------------------------
	
	private DateUtils() {}
	
	/**
	 * 
	 *  Tao ngay tu cac tham so dau vao
	 *  @author: Nhantd
	 *  @param year
	 *  		- Nam nhap vao
	 *  @param month
	 *  		- Thang nhap vao
	 *  @param dayOfMonth
	 *  		- Ngay trong thang nhap vao
	 *  @param hour
	 *  		- Gio nhap vao
	 *  @param minute
	 *  		- Phut nhap vao
	 *  @param second
	 *  		- Giay nhap vao
	 *  @return
	 *  @return: Date
	 *  @throws:
	 */
	public static Date creatDate(int year, int month, int dayOfMonth, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, dayOfMonth, hour, minute, second);
		return cal.getTime();
	}
	
	/**
	 * 
	 *  Tao danh sach nam tu nam bat dau startyear den nam hien tai.
	 *  Neu startyear > nam hien tai cua he thong thi tra ve nam hien tai.
	 *  @author: Nhantd
	 *  @param startyear
	 *  		-	Nam bat dau cua danh sach
	 *  @return
	 *  @return: List<Integer>
	 *  		-	Danh sach nam tra ve
	 *  @throws:
	 */
	public static List<Integer> creatYearList(int startyear) {
		List<Integer> result = new Vector<Integer>();
		Date currentDate = new Date();
		int currentYear = getYear(currentDate);
		if (currentYear < startyear) {
			result.add(currentYear);
		} else {
			for (int i = startyear; i <= currentYear; i++) {
				result.add(i);
			}
		}
		return result;
	}

	/**
	 * 
	 *  Lay chuoi string thoi gian hien tai theo dinh dang chuan : yyyy-MM-dd
	 * 	HH:mm:ss
	 *  @author: Nhantd
	 *  @param getLocalTime
	 *  		-	false : default khong get timezone "GMT" local
	 *  		-	true : get timezone "GMT" local
	 *  @return
	 *  @return: String
	 *  @throws:
	 */
	public static String getStringCurrentDate(boolean getLocalTime) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STANDARD);
		if (getLocalTime) {
			sdf.setTimeZone(new SimpleTimeZone(0, "GMT"));
		}
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 
	 *  Tra ve hour theo date nhap vao
	 *  @author: Nhantd
	 *  @param date
	 *  @return
	 *  @return: int
	 *  	-	Tra ve 0 neu date = null
	 *  @throws:
	 */
	public static int getHour(Date date) {
		if (date == null) {
			return 0;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 
	 *  Tra ve Day theo date nhap vao
	 *  @author: Nhantd
	 *  @param date
	 *  @return
	 *  @return: int
	 *  	-	Tra ve 0 neu date = null
	 *  @throws:
	 */
	public static int getDay(Date date) {
		if (date == null) {
			return 0;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 
	 *  Tra ve Day kieu String theo date nhap vao
	 *  @author: Nhantd
	 *  @param date
	 *  @return
	 *  @return: String
	 *  	- Tra ve "" neu date = null
	 *  @throws:
	 */
	public static String getDayString(Date date) {
		int day = getDay(date);
		if (day == 0) {
			return "";
		}
		if (day < 10) {
			return ("0" + day);
		} else {
			return (day + "");
		}
	}
	
	/**
	 * 
	 *  Tra ve Month theo date nhap vao
	 *  @author: Nhantd
	 *  @param date
	 *  @return
	 *  @return: int
	 *  	- 	Tra ve 0 neu date = null
	 *  @throws:
	 */
	public static int getMonth(Date date) {
		if (date == null) {
			return 0;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return (cal.get(Calendar.MONTH) + 1);
	}
	
	/**
	 * 
	 *  Tra ve Month kieu String theo date nhap vao
	 *  @author: Nhantd
	 *  @param date
	 *  @return
	 *  @return: String
	 *  	-	Tra ve "" neu date = null
	 *  @throws:
	 */
	public static String getMonthString(Date date) {
		int month = getMonth(date);
		if (month == 0) {
			return "";
		}
		if (month < 10) {
			return ("0" + month);
		} else {
			return (month + "");
		}
	}
	
	/**
	 * 
	 *  Tra ve year theo date nhap vao
	 *  @author: Nhantd
	 *  @param date
	 *  @return
	 *  @return: int
	 *  	-	Tra ve 0 neu date = null
	 *  @throws:
	 */
	public static int getYear(Date date) {
		if (date == null) {
			return 0;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * 
	 *  Tra ve year kieu String theo date nhap vao
	 *  @author: Nhantd
	 *  @param date
	 *  @return
	 *  @return: String
	 *  	-	Tra ve "" neu date = null
	 *  @throws:
	 */
	public static String getYearString(Date date) {
		int year = getYear(date);
		if (year == 0) {
			return "";
		} else {
			return (year + "");
		}
	}
	
	/**
	 * 
	 *  Lay chuoi string thoi gian hien tai theo dinh dang Iso 8601
	 *  yyyy-MM-dd 'T' HH:mm:ss.SSS'Z'
	 *  @author: Nhantd
	 *  @param getLocalTime
	 *  @return
	 *  @return: String
	 *  @throws:
	 */
	public static String getStringCurrentDateFormatIso8601(boolean getLocalTime) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_ISO8601);
		if (getLocalTime) {
			sdf.setTimeZone(new SimpleTimeZone(0, "GMT"));
		}
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 
	 *  Get index cua ngay hien tai
	 *  @author: Nhantd
	 *  @return
	 *  @return: int
	 *  		-	Tra ve 0 neu la thu hai
	 *  		-	Tra ve 1 neu la thu ba
	 *  		- 	Tra ve 2 neu la thu tu
	 *  		-	Tra ve 3 neu la thu nam
	 *  		-	Tra ve 4 neu la thu sau
	 *  		-	Tra ve 5 neu la thu bay
	 *  		-	Tra ve 6 neu la chu nhat
	 *  @throws:
	 */
	public static int getIndexCurrentDate() {
		Date d = new Date();
		Calendar.getInstance().setTime(d);
		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		int indexDate = -1;
		switch (day) {
		case Calendar.MONDAY:
			indexDate = 0;
			break;
		case Calendar.TUESDAY:
			indexDate = 1;
			break;
		case Calendar.WEDNESDAY:
			indexDate = 2;
			break;
		case Calendar.THURSDAY:
			indexDate = 3;
			break;
		case Calendar.FRIDAY:
			indexDate = 4;
			break;
		case Calendar.SATURDAY:
			indexDate = 5;
			break;
		case Calendar.SUNDAY:
			indexDate = 6;
			break;
		default:
			break;
		}
		return indexDate;
	}
	
	/**
	 * 
	 *  Get line cua ngay hien tai
	 *  @author: Nhantd
	 *  @return
	 *  @return: String
	 *  		-	Tra ve "T2" neu la thu hai
	 *  		-	Tra ve "T3" neu la thu ba
	 *  		-	Tra ve "T4" neu la thu tu
	 *  		-	Tra ve "T5" neu la thu nam
	 *  		-	Tra ve "T6" neu la thu sau
	 *  		-	Tra ve "T7" neu la thu bay
	 *  		-	Tra ve "CN" neu la chu nhat
	 *  @throws:
	 */
	public static String getLineCurrentDate() {
		Date d = new Date();
		Calendar.getInstance().setTime(d);
		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		String rs = "";
		switch (day) {
		case Calendar.MONDAY:
			rs = DAY_LINE_VN[0];
			break;
		case Calendar.TUESDAY:
			rs = DAY_LINE_VN[1];
			break;
		case Calendar.WEDNESDAY:
			rs = DAY_LINE_VN[2];
			break;
		case Calendar.THURSDAY:
			rs = DAY_LINE_VN[3];
			break;
		case Calendar.FRIDAY:
			rs = DAY_LINE_VN[4];
			break;
		case Calendar.SATURDAY:
			rs = DAY_LINE_VN[5];
			break;
		case Calendar.SUNDAY:
			rs = DAY_LINE_VN[6];
			break;
		default:
			break;
		}
		return rs;
	}
	
	/**
	 * 
	 *  Tra ve ngay trong tuan cua ngay nhap vao voi ngon ngu theo language
	 *  @author: Nhantd
	 *  @param date
	 *  	- Ngay nhap vao
	 *  @param language
	 *  	- Ngon ngu muon chuyen thanh
	 *  	- 0 : Tieng viet
	 *  	- 1 : Tieng Anh
	 *  @return
	 *  @return: String
	 *  	Ex: language = 0 : "Thứ 2"
	 *  		language = 1 : "Monday"
	 *  @throws:
	 */
	public static String getDayOfWeek(Date date, int language) {
		String today = "";
		Calendar.getInstance().setTime(date);
		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		switch (language) {
		// Tieng Viet
		case 0 : {
			switch (day) {
			case Calendar.MONDAY:
				today = DAY_IN_WEEK_VN[0];
				break;
			case Calendar.TUESDAY:
				today = DAY_IN_WEEK_VN[1];
				break;
			case Calendar.WEDNESDAY:
				today = DAY_IN_WEEK_VN[2];
				break;
			case Calendar.THURSDAY:
				today = DAY_IN_WEEK_VN[3];
				break;
			case Calendar.FRIDAY:
				today = DAY_IN_WEEK_VN[4];
				break;
			case Calendar.SATURDAY:
				today = DAY_IN_WEEK_VN[5];
				break;
			case Calendar.SUNDAY:
				today = DAY_IN_WEEK_VN[6];
				break;
			default:
				break;
			}
			break;
		}
		case 1: {
			switch (day) {
			case Calendar.MONDAY:
				today = DAY_IN_WEEK[0];
				break;
			case Calendar.TUESDAY:
				today = DAY_IN_WEEK[1];
				break;
			case Calendar.WEDNESDAY:
				today = DAY_IN_WEEK[2];
				break;
			case Calendar.THURSDAY:
				today = DAY_IN_WEEK[3];
				break;
			case Calendar.FRIDAY:
				today = DAY_IN_WEEK[4];
				break;
			case Calendar.SATURDAY:
				today = DAY_IN_WEEK[5];
				break;
			case Calendar.SUNDAY:
				today = DAY_IN_WEEK[6];
				break;
			default:
				break;
			}
			break;
		}
		default:
			break;
		}
		return today;
	}
	
	/**
	 * 
	 *  Tra ve ngay thang convert tu milliseconds theo dinh dang chuan 
	 *  "yyyy-MM-dd HH:mm:ss"
	 *  @author: Nhantd
	 *  @param milliseconds
	 *  @return
	 *  @return: Date
	 *  @throws:
	 */
	public static Date getDateByMilliseconds(long milliseconds) {
		return formatDateByMillisecondsWithFormat(milliseconds, DATE_FORMAT_STANDARD);
	}
	
	/**
	 * 
	 *  Lay ngay nam truoc date nhap vao voi days ngay cho truoc
	 *  @author: Nhantd
	 *  @param date
	 *  	-	date nhap vao lam moc
	 *  @param days
	 *  	-	so ngay nam truoc date
	 *  @return
	 *  @return: Date
	 *  @throws:
	 */
	public static Date getDateBefore(Date date, int days) {
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(date);
		gcal.add(Calendar.DAY_OF_YEAR, -days);
		return gcal.getTime();
	}
	
	/**
	 * 
	 *  Lay ngay nam sau date nhap vao voi days ngay cho truoc
	 *  @author: Nhantd
	 *  @param date
	 *  	-	date nhap vao lam moc
	 *  @param days
	 *  	-	so ngay nam sau date
	 *  @return
	 *  @return: Date
	 *  @throws:
	 */
	public static Date getDateAfter(Date date, int days) {
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(date);
		gcal.add(Calendar.DAY_OF_YEAR, days);
		return gcal.getTime();
	}
	
	/**
	 * 
	 *  Tra ve so ngay trong thang theo mot ngay bat ky nhap vao
	 *  @author: Nhantd
	 *  @param date
	 *  		- Ngay trong thang can xet
	 *  @return
	 *  @return: int
	 *  @throws:
	 */
	public static int getNumberDaysInMonth(Date date) {
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(date);
		return (short)gcal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 
	 *  Tra ve so ngay trong thang month va nam year nhap vao
	 *  @author: Nhantd
	 *  @param month
	 *  		- Thang can xet
	 *  @param year
	 *  		- Nam can xet
	 *  @return
	 *  @return: int
	 *  @throws:
	 */
	public static int getNumberDaysOfMonth(int month, int year) {
		Date date = creatDate(year, month, 1, 0, 0, 0);
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(date);
		return (short)gcal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 
	 *  Lay so quy theo thang month nhap vao
	 *  @author: Nhantd
	 *  @param month
	 *  		- Thang nhap vao
	 *  @return
	 *  @return: int
	 *  @throws:
	 */
	public static int getQuarterByMonth(int month) {
		return (((month - 1) / 3) + 1);
	}

	/**
	 * 
	 * Lay chuoi string thoi gian hien tai theo dinh dang dua vao
	 * 
	 * @author: Nhantd
	 * @param formatDate
	 *            - Dinh dang ngay thang dua vao. Ex: "dd MMM yyyy"
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String formatStringCurrentDate(String formatDate) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
		return sdf.format(cal.getTime());
	}

	/**
	 * 
	 * Lay thoi gian hien tai theo dinh dang dua vao. Neu khong lay duoc thi se
	 * tra ve thoi gian hien tai dinh dang chuan
	 * 
	 * @author: Nhantd
	 * @param formatDate
	 *            - Dinh dang ngay thang dua vao. Ex: "dd MMM yyyy"
	 * @return
	 * @return: Date
	 * @throws:
	 */
	public static Date formatCurrentDate(String formatDate) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
		try {
			return sdf.parse(sdf.format(cal.getTime()));
		} catch (ParseException e) {
			return new Date();
		}
	}

	/**
	 * 
	 * Tra ve chuoi string ngay thang da convert theo dinh dang formatDate
	 * 
	 * @author: Nhantd
	 * @param date
	 * @param formatDate
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String formatStringDateWithFormat(Date date, String formatDate) {
		if (!StringUtils.hasLength(formatDate)) {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STANDARD);
			return sdf.format(date);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
			return sdf.format(date);
		}
	}
	
	/**
	 * 
	 * Tra ve chuoi string ngay thang da convert theo dinh dang formatDate
	 * 
	 * @author: Nhantd
	 * @param date
	 * @param formatDate
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String formatStringDateWithFormat(String date, String formatDate) {
		if (!StringUtils.hasLength(formatDate)) {
			return date;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
			try {
				return sdf.format(sdf.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
				return date;
			}
			
		}
	}

	/**
	 * 
	 * Tra ve ngay thang duoc convert dinh dang theo formatDate
	 * 
	 * @author: Nhantd
	 * @param date
	 * @param formatDate
	 * @return
	 * @return: Date
	 * @throws:
	 */
	public static Date formatDateWithFormat(Date date, String formatDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
		try {
			date = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 
	 * Tra ve ngay thang convert tu chuoi string voi dinh dang formatDate
	 * 
	 * @author: Nhantd
	 * @param date
	 * @param formatDate
	 * @return
	 * @return: Date
	 * @throws:
	 */
	public static Date formatDateWithFormat(String date, String formatDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
		Date d = new Date();
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return d;
	}
	
	/**
	 * 
	 *  Tra ve ngay thang convert tu milliseconds voi dinh dang formatDate
	 *  @author: Nhantd
	 *  @param milliseconds
	 *  @param formatDate
	 *  @return
	 *  @return: Date
	 *  @throws:
	 */
	public static Date formatDateByMillisecondsWithFormat(long milliseconds, String formatDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(milliseconds);
		return formatDateWithFormat(cal.getTime(), formatDate);
	}

	/**
	 * 
	 * Tinh toan thoi gian giua hai ngay nhap vao
	 * 
	 * @author: Nhantd
	 * @param startDate
	 *            - Ngay bat dau
	 * @param endDate
	 *            - Ngay ket thuc
	 * @return
	 * @return: long - Tra ve thoi gian tinh bang milliseconds giua 2 ngay nhap
	 *          vao
	 * @throws:
	 */
	public static long calculateTimeBetweenTwoDate(Date startDate, Date endDate) {
		if (startDate.getTime() > endDate.getTime()) {
			return (long) ((startDate.getTime() - endDate.getTime()));
		}
		return (long) ((endDate.getTime() - startDate.getTime()));
	}

	/**
	 * 
	 * Tinh toan thoi gian va tra ve so ngay giua 2 ngay nhap vao
	 * 
	 * @author: Nhantd
	 * @param startDate
	 *            - Ngay bat dau
	 * @param endDate
	 *            - Ngay ket thuc
	 * @return
	 * @return: long - Tra ve so ngay giua 2 ngay nhap vao
	 * @throws:
	 */
	public static long calculateDaysBetweenTwoDate(Date startDate, Date endDate) {
		return (long) (calculateTimeBetweenTwoDate(startDate, endDate) / DAY);
	}

	/**
	 * 
	 * Tra ve so ngay lam viec giua 2 ngay nhap vao. Ngay lam viec la cac ngay
	 * trong tuan tru ngay chu nhat
	 * 
	 * @author: Nhantd
	 * @param startDate
	 *            - Ngay bat dau
	 * @param endDate
	 *            - Ngay ket thuc
	 * @return
	 * @return: long - Tra ve so ngay lam viec giua ngay bat dau va ngay ket
	 *          thuc
	 * @throws:
	 */
	public static long calculateWorkingDaysBetweenTwoDate(Date startDate,
			Date endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			startDate = sdf.parse(sdf.format(startDate));
			endDate = sdf.parse(sdf.format(endDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar startCal;
		Calendar endCal;
		startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		int workDays = 1;
		// Return 0 if start date and end date are the same
		if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
			startCal.setTime(endDate);
			endCal.setTime(startDate);
		}
		if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
			if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				return 0;
			} else {
				return 1;
			}
		}
		do {
			if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				++workDays;
			}
			startCal.add(Calendar.DAY_OF_MONTH, 1);
		} while (startCal.getTimeInMillis() < endCal.getTimeInMillis());
		return workDays;
	}

	/**
	 * 
	 * So sanh ngay nhap vao voi ngay hien tai
	 * 
	 * @author: Nhantd
	 * @param date
	 * @param formatDate
	 * @return
	 * @return: int - Tra ve < 0 neu ngay nhap vao nho hon ngay hien tai - Tra
	 *          ve = 0 neu ngay nhap vao bang ngay hien tai - Tra ve > 0 neu
	 *          ngay nhap vao lon hon ngay hien tai
	 * @throws:
	 */
	public static int compareDateWithCurrentDate(String date, String formatDate) {
		int result = -1;
		try {
			Date now = formatCurrentDate(formatDate);
			SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
			Date datecompare = sdf.parse(date);

			result = datecompare.compareTo(now);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * So sanh hai chuoi string ngay nhap vao voi 2 dinh dang nhap vao
	 * 
	 * @author: Nhantd
	 * @param date
	 * @param formatDate
	 * @return
	 * @return: int - Tra ve < 0 neu date1 < date2 - Tra ve = 0 neu date1 =
	 *          date2 - Tra ve > 0 neu date1 > date2
	 * @throws:
	 */
	public static int compareTwoDates(String date1, String formatDate1,
			String date2, String formatDate2) {
		int result = -1;
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat(formatDate1);
			SimpleDateFormat sdf2 = new SimpleDateFormat(formatDate1);
			
			Date datecompare1 = sdf1.parse(date1);
			Date datecompare2 = sdf2.parse(date2);
			
			result = datecompare1.compareTo(datecompare2);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 *  Kiem tra xem mot ngay co gia tri (ngay, thang, nam,...) nhu
	 *  gia tri mong muon hay khong
	 *  @author: Nhantd
	 *  @param date
	 *  		- Ngay muon kiem tra
	 *  @param dateType
	 *  		- Kieu ngay mong muon: Calendar.MONTH, Calendar.DAY_OF_WEEK,...
	 *  @param value
	 *  		- Gia tri mong muon: Calendar.JANUARY, Calendar.SUNDAY,...
	 *  @return
	 *  @return: boolean
	 *  @throws:
	 */
	public static boolean isDateHasValueOfDateType(Date date, int dateType, int value) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return (c.get(dateType) == value);
	}
		
	/**
	 * 
	 *  Them mot khoang thoi gian dang milliseconds vao date da nhap vao
	 *  @author: Nhantd
	 *  @param date
	 *  		- Thoi gian muon cong vao
	 *  @param milliseconds
	 *  		- Thoi gian muon duoc cong them dang milliseconds
	 *  @return
	 *  @return: Date
	 *  @throws:
	 */
	public static Date addDurationToDate(Date date, long milliseconds) {
		return getDateByMilliseconds(date.getTime() + milliseconds);
	}
}
