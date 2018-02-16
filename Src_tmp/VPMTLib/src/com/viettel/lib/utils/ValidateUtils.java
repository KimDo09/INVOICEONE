/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Lop chua cac ham kiem tra hop le cho cac kieu du lieu khac nhau nhu int,
 * float, string,...
 * 
 * @author: Nhantd
 * @version: 1.0
 * @since: 1.0
 */
public abstract class ValidateUtils {

	// ---------------------------------------------------------------------------
	// Cac bien dung chung trong ValidateUtil
	// ---------------------------------------------------------------------------

	private static final char charsInName[] = { 'à', 'á', 'ả', 'ã', 'ạ', 'ă',
			'ằ', 'ắ', 'ẳ', 'ẵ', 'ặ', 'â', 'ầ', 'ấ', 'ẩ', 'ẫ', 'ậ', 'À', 'Á',
			'Ả', 'Ã', 'Ạ', 'Ă', 'Ằ', 'Ắ', 'Ẳ', 'Ẵ', 'Ặ', 'Â', 'Ầ', 'Ấ', 'Ẩ',
			'Ẫ', 'Ậ', 'è', 'é', 'ẻ', 'ẽ', 'ẹ', 'ê', 'ề', 'ế', 'ể', 'ễ', 'ệ',
			'È', 'É', 'Ẻ', 'Ẽ', 'Ẹ', 'Ê', 'Ề', 'Ế', 'Ể', 'Ễ', 'Ệ', 'ì', 'í',
			'ỉ', 'ĩ', 'ị', 'Ì', 'Í', 'Ỉ', 'Ĩ', 'Ị', 'ò', 'ó', 'ỏ', 'õ', 'ọ',
			'ô', 'ồ', 'ố', 'ổ', 'ỗ', 'ộ', 'ơ', 'ờ', 'ớ', 'ở', 'ỡ', 'ợ', 'Ò',
			'Ó', 'Ỏ', 'Õ', 'Ọ', 'Ô', 'Ồ', 'Ố', 'Ổ', 'Ỗ', 'Ộ', 'Ơ', 'Ờ', 'Ớ',
			'Ở', 'Ỡ', 'Ợ', 'ù', 'ú', 'ủ', 'ũ', 'ụ', 'ư', 'ừ', 'ứ', 'ử', 'ữ',
			'ự', 'Ù', 'Ú', 'Ủ', 'Ũ', 'Ụ', 'Ư', 'Ừ', 'Ứ', 'Ử', 'Ữ', 'Ự', 'ỳ',
			'ý', 'ỷ', 'ỹ', 'ỵ', 'Ỳ', 'Ý', 'Ỷ', 'Ỹ', 'Ỵ', 'đ', 'Đ', 'a', 'A',
			'b', 'B', 'c', 'C', 'd', 'D', 'e', 'E', 'f', 'F', 'g', 'G', 'h',
			'H', 'i', 'I', 'j', 'J', 'k', 'K', 'l', 'L', 'm', 'M', 'n', 'N',
			'o', 'O', 'p', 'P', 'q', 'Q', 'r', 'R', 's', 'S', 't', 'T', 'u',
			'U', 'v', 'V', 'w', 'W', 'x', 'X', 'y', 'Y', 'z', 'Z', ' ' };

	private static final String PHONE_PATTERN = "([-,0-9,(,), ,.]*)?";

	private static final String VIETTEL_PHONE_PATTERN = "^84(98|97|96|163|164|165|166|167|168|169)[0-9]*$";

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static final String NUMBER_PATTERN = "(-|\\+)?[0-9]+(\\.[0-9]+)?";

	private static final String WEBSITE_ADDRESS_PATTERN = "(http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ;,./?%&=]*)?";

	// ---------------------------------------------------------------------------
	// Cac ham validate string
	// ---------------------------------------------------------------------------

	private ValidateUtils() {}
	
	/**
	 * 
	 * Kiem tra xem ten co chua cac ky tu hop le hay khong. Cac ky tu dac biet,
	 * ky tu so la khong hop le
	 * 
	 * @author: Nhantd
	 * @param name
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isNameContainValidChars(String name) {
		boolean isContain = false;
		for (int i = 0, m = name.length(); i < m; i++) {
			isContain = false;
			for (int j = 0, n = charsInName.length; j < n; j++) {
				if (name.charAt(i) == charsInName[j]) {
					isContain = true;
					break;
				}
			}
		}
		return isContain;
	}

	/**
	 * 
	 * Kiem tra xem chuoi nhap vao co dung dinh dang pattern cho truoc khong
	 * 
	 * @author: Nhantd
	 * @param str
	 * @param pattern
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isForMatch(String str, String pattern) {
		Pattern pattr = Pattern.compile(pattern);
		Matcher matcher = pattr.matcher(str);
		return matcher.matches();
	}

	/**
	 * 
	 * Kiem tra xem so dien thoai nhap vao co hop le hay khong
	 * 
	 * @author: Nhantd
	 * @param phonenumber
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isValidatePhonenumber(String phonenumber) {
		return isForMatch(phonenumber, PHONE_PATTERN);
	}

	/**
	 * 
	 * Kiem tra xem so dien thoai nhap vao co phai la so dien thoai cua Viettel
	 * hay khong
	 * 
	 * @author: Nhantd
	 * @param phonenumber
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isValidateViettelPhonenumber(String phonenumber) {
		if (isValidatePhonenumber(phonenumber)) {
			return isForMatch(phonenumber, VIETTEL_PHONE_PATTERN);
		}
		return false;
	}

	/**
	 * 
	 * Kiem tra xem mail nhap vao co hop le hay khong javacode@gmail.com.2j ->
	 * false. Khong cho phep co digit o level 2 tld cua mail
	 * java@java@oracle.com -> false. Khong co 2 '@' trong dia chi mail
	 * java!!!@example.com -> false. Khong co ky tu dac biet '!' trong dia chi
	 * mail mysite@.com -> false. tld sau @ khong bat dau boi '.' java.com ->
	 * false. Phai chua 1 ky tu '@' va 1 tld java..javacom@at.com -> false.
	 * Khong duoc co 2 '.' lien tiep trong dia chi .java.com@at.com -> false.
	 * Dia chi khong duoc bat dau bang '.' java@gmail.com, nhan+ntv@gmail.com,
	 * abc.edf-900@twitter.com -> true
	 * 
	 * @author: Nhantd
	 * @param mail
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isValidateMail(String mail) {
		return isForMatch(mail, EMAIL_PATTERN);
	}

	/**
	 * 
	 * Kiem tra xem chuoi dua vao co phai la kieu numberic (int, float, double)
	 * khong Valid : 1234, +1234, 1234.123, -123.001, 100.000 Invalid : 1234.,
	 * 32 3232, 123.123.123, -, +-123, any text
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isValidateNumeric(String str) {
		return isForMatch(str, NUMBER_PATTERN);
	}

	/**
	 * 
	 * Kiem tra xem chuoi dua vao co phai la dia chi 1 website hay khong
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isValidateWebsiteAddress(String str) {
		return isForMatch(str, WEBSITE_ADDRESS_PATTERN);
	}

	/**
	 * 
	 * Kiem tra xem chuoi dua vao co dinh dang theo ngay thang chuan khong. Ngay
	 * thang chuan co dinh dang : dd/mm/yyyy
	 * 
	 * @author: Nhantd
	 * @param date
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isValidateDateStandard(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd\\mm\\yyyy");
		try {
			formatter.parse(date);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 
	 * Kiem tra xem chuoi ngay thang dua vao co dinh dang theo pattern cho truoc
	 * hay khong
	 * 
	 * @author: Nhantd
	 * @param date
	 * @param pattern
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isValidateDate(String date, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		try {
			formatter.parse(date);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * Kiem tra xem do dai cua chuoi dua vao co nam trong dai [min,max] khong
	 * 
	 * @author: Nhantd
	 * @param text
	 * @param min
	 *            - do dai toi thieu cua chuoi dua vao
	 * @param max
	 *            - do dai toi da cua chuoi dua vao
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isValidateTextLength(String text, int min, int max) {
		int len = text.length();
		if ((len >= min) && (len <= max)) {
			return true;
		}
		return false;
	}

	// ---------------------------------------------------------------------------
	// Cac ham validate numberic (short, int, long, float, double,...)
	// ---------------------------------------------------------------------------

	/**
	 * 
	 * Kiem tra xem mot so kieu short co nam trong dai [low,high] hay khong
	 * 
	 * @author: Nhantd
	 * @param number
	 * @param low
	 * @param high
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isValidateShortInRange(short number, short low,
			short high) {
		if ((number >= low) && (number <= high)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * Kiem tra xem mot so kieu integer co nam trong dai [low,high] hay khong
	 * 
	 * @author: Nhantd
	 * @param number
	 * @param low
	 * @param high
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isValidateIntegerInRange(int number, int low, int high) {
		if ((number >= low) && (number <= high)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * Kiem tra xem mot so kieu long co nam trong dai [low,high] hay khong
	 * 
	 * @author: Nhantd
	 * @param number
	 * @param low
	 * @param high
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isValidateLongInRange(long number, long low, long high) {
		if ((number >= low) && (number <= high)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * Kiem tra xem mot so kieu float co nam trong dai [low,high] hay khong
	 * 
	 * @author: Nhantd
	 * @param number
	 * @param low
	 * @param high
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isValidateFloatInRange(float number, float low,
			float high) {
		if ((number >= low) && (number <= high)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * Kiem tra xem mot so kieu double co nam trong dai [low,high] hay khong
	 * 
	 * @author: Nhantd
	 * @param number
	 * @param low
	 * @param high
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isValidateDoubleInRange(double number, double low,
			double high) {
		if ((number >= low) && (number <= high)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * Kiem tra xem mot so co phai la so nguyen to hay khong
	 * 
	 * @author: Nhantd
	 * @param n
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean checkNumberIsPrimes(int n) {
		// check if n is a multiple of 2
		if (n % 2 == 0) {
			return false;
		}
		// if not, then just check the odds
		for (int i = 3; i * i < n; i += 2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}


	/**
	 * 
	 * Kiem tra mot so co phai la so Armstrong hay khong
	 * 
	 * @author: Nhantd
	 * @param n
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean checkNumberIsArmstrong(long n) {
		long temp = n;
		long r;
		long sum = 0;
		while (temp != 0) {
			r = temp % 10;
			sum = sum + r * r * r;
			temp = temp / 10;
		}
		if (n == sum) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 *  Kiem tra app da duoc cai tren thiet bi chua
	 *  @author: Hainm
	 *  @param context
	 *  @param uri
	 *  @return
	 *  @return: boolean
	 *  @throws:
	 */
	public static boolean isAppInstalled(Context context, String packageName) {
		PackageManager packageManager = context.getPackageManager();
		boolean appInstalled = false;
		try {
			packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			appInstalled = true;
		} catch (PackageManager.NameNotFoundException e) {
			appInstalled = false;
		}
		return appInstalled;
	}
}
