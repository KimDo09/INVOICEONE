/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import android.content.Context;

/**
 * Lop chua cac ham xu ly chung cho String, String array
 * 
 * @author: Nhantd
 * @version: 1.0
 * @since: 1.0
 */
public abstract class StringUtils {

	// ---------------------------------------------------------------------------
	// Cac bien dung chung trong StringUtil
	// ---------------------------------------------------------------------------

	public static final String FOLDER_SEPARATOR = "/";

	public static final String WINDOWS_FOLDER_SEPARATOR = "\\";

	public static final String TOP_PATH = "..";

	public static final String CURRENT_PATH = ".";

	public static final char EXTENSION_SEPARATOR = '.';

	public static final String EMPTY_STRING = "";

	public static final String NULL_STRING = "null";

	private static final String ARRAY_START = "{";

	private static final String ARRAY_END = "}";

	private static final String EMPTY_ARRAY = ARRAY_START + ARRAY_END;

	private static final char[] marked = { 'à', 'á', 'ả', 'ã', 'ạ', 'ă', 'ằ',
			'ắ', 'ẳ', 'ẵ', 'ặ', 'â', 'ầ', 'ấ', 'ẩ', 'ẫ', 'ậ', 'À', 'Á', 'Ả',
			'Ã', 'Ạ', 'Ă', 'Ằ', 'Ắ', 'Ẳ', 'Ẵ', 'Ặ', 'Â', 'Ầ', 'Ấ', 'Ẩ', 'Ẫ',
			'Ậ', 'è', 'é', 'ẻ', 'ẽ', 'ẹ', 'ê', 'ề', 'ế', 'ể', 'ễ', 'ệ', 'È',
			'É', 'Ẻ', 'Ẽ', 'Ẹ', 'Ê', 'Ề', 'Ế', 'Ể', 'Ễ', 'Ệ', 'ì', 'í', 'ỉ',
			'ĩ', 'ị', 'Ì', 'Í', 'Ỉ', 'Ĩ', 'Ị', 'ò', 'ó', 'ỏ', 'õ', 'ọ', 'ô',
			'ồ', 'ố', 'ổ', 'ỗ', 'ộ', 'ơ', 'ờ', 'ớ', 'ở', 'ỡ', 'ợ', 'Ò', 'Ó',
			'Ỏ', 'Õ', 'Ọ', 'Ô', 'Ồ', 'Ố', 'Ổ', 'Ỗ', 'Ộ', 'Ơ', 'Ờ', 'Ớ', 'Ở',
			'Ỡ', 'Ợ', 'ù', 'ú', 'ủ', 'ũ', 'ụ', 'ư', 'ừ', 'ứ', 'ử', 'ữ', 'ự',
			'Ù', 'Ú', 'Ủ', 'Ũ', 'Ụ', 'ỳ', 'ý', 'ỷ', 'ỹ', 'ỵ', 'Ỳ', 'Ý', 'Ỷ',
			'Ỹ', 'Ỵ', 'đ', 'Đ', 'Ư', 'Ừ', 'Ử', 'Ữ', 'Ứ', 'Ự' };

	private static final char[] notmarked = { 'a', 'a', 'a', 'a', 'a', 'a',
			'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'A', 'A',
			'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A',
			'A', 'A', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e',
			'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'i', 'i',
			'i', 'i', 'i', 'I', 'I', 'I', 'I', 'I', 'o', 'o', 'o', 'o', 'o',
			'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'O',
			'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O',
			'O', 'O', 'O', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u',
			'u', 'U', 'U', 'U', 'U', 'U', 'y', 'y', 'y', 'y', 'y', 'Y', 'Y',
			'Y', 'Y', 'Y', 'd', 'D' };

	// ---------------------------------------------------------------------------
	// Cac phuong thuc lam viec chung voi Strings
	// ---------------------------------------------------------------------------

	private StringUtils() {
	}

	/**
	 * 
	 * Kiem tra xem mot CharSequence co null hay do dai la 0
	 * StringUtil.hasLength(null) = false; StringUtil.hasLength("") = false
	 * StringUtil.hasLength(" ") = true; StringUtil.hasLength("Hi") = true
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * 
	 * Kiem tra xem mot chuoi String co null hay do dai la 0
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean hasLength(String str) {
		return hasLength((CharSequence) str);
	}

	/**
	 * 
	 * Kiem tra xem mot CharSequence co chua text hay khong
	 * StringUtil.hasText(null) = false; StringUtil.hasText("") = false
	 * StringUtil.hasText(" ") = false; StringUtil.hasText("12345") = true
	 * StringUtil.hasText(" 12 34 ") = true
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}

		int strLength = str.length();
		for (int i = 0; i < strLength; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * Kiem tra xem mot chuoi String co chua text hay khong.Co rong hay khong
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean hasText(String str) {
		return hasText((CharSequence) str);
	}

	/**
	 * 
	 * Kiem tra xem mot CharSequence co chua khoang trang " " hay khong
	 * StringUtil.containsWhitespace("12345") = false
	 * StringUtil.containsWhitespace("12 345") = true
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean containsWhitespace(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLength = str.length();
		for (int i = 0; i < strLength; i++) {
			if (Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * Kiem tra xem mot chuoi String co chua khoang trang " " hay khong
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean containsWhitespace(String str) {
		return containsWhitespace((CharSequence) str);
	}

	/**
	 * 
	 * Trim whitespace o dau va cuoi cua chuoi String
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String trimWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
			sb.deleteCharAt(0);
		}
		while (sb.length() > 0
				&& Character.isWhitespace(sb.charAt(sb.length() - 1))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 
	 * Xoa cac ky tu khoang trang trong chuoi String sao cho cach giua moi tu
	 * chi la mot khoang trang duy nhat
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String trimOnlyOneWhitespaceBetween(String str) {
		if (!hasLength(str)) {
			return str;
		}
		return str.replaceAll("\\b\\s{2,}\\b", " ");
	}

	/**
	 * 
	 * Trim tat ca whitespace cua chuoi String
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String trimAllWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		int index = 0;
		while (sb.length() > index) {
			if (Character.isWhitespace(sb.charAt(index))) {
				sb.deleteCharAt(index);
			} else {
				index++;
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * Trim whitespace o dau (starting) cua chuoi string
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String trimStartingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

	/**
	 * 
	 * Trim whitespace o cuoi (ending) cua chuoi string
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String trimEndingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0
				&& Character.isWhitespace(sb.charAt(sb.length() - 1))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 
	 * Trim tat ca cac ky tu startingCharacter o dau cua chuoi string
	 * 
	 * @author: Nhantd
	 * @param str
	 * @param startingCharacter
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String trimStartingCharacter(String str,
			char startingCharacter) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && sb.charAt(0) == startingCharacter) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

	/**
	 * 
	 * Trim tat ca cac ky tu endingCharacter o cuoi cua chuoi string
	 * 
	 * @author: Nhantd
	 * @param str
	 * @param endingCharacter
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String trimEndingCharacter(String str, char endingCharacter) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && sb.charAt(sb.length() - 1) == endingCharacter) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 
	 * Kiem tra xem mot chuoi string co bat dau bang mot chuoi prefix hay khong,
	 * khong phan biet HOA thuong
	 * 
	 * @author: Nhantd
	 * @param str
	 * @param prefix
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null) {
			return false;
		}
		if (str.startsWith(prefix)) {
			return true;
		}
		if (str.length() < prefix.length()) {
			return false;
		}
		String lcStr = str.substring(0, prefix.length()).toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}

	/**
	 * 
	 * Kiem tra xem mot chuoi string co ket thuc bang mot chuoi suffix hay
	 * khong, khong phan biet HOA thuong
	 * 
	 * @author: Nhantd
	 * @param str
	 * @param suffix
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean endsWithIgnoreCase(String str, String suffix) {
		if (str == null || suffix == null) {
			return false;
		}
		if (str.endsWith(suffix)) {
			return true;
		}
		if (str.length() < suffix.length()) {
			return false;
		}
		String lcStr = str.substring(str.length() - suffix.length())
				.toLowerCase();
		String lcPrefix = suffix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}

	/**
	 * 
	 * Kiem tra xem chuoi string co chua chuoi substring bat dau tai index duoc
	 * chi dinh hay khong
	 * 
	 * @author: Nhantd
	 * @param str
	 * @param index
	 * @param substring
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean substringMatch(CharSequence str, int index,
			CharSequence substring) {
		for (int i = 0; i < substring.length(); i++) {
			int j = index + i;
			if (j >= str.length() || str.charAt(j) != substring.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * Dem so lan xuat hien cua chuoi substring trong chuoi tring
	 * 
	 * @author: Nhantd
	 * @param str
	 * @param substring
	 * @return
	 * @return: int
	 * @throws:
	 */
	public static int countOccurrencesOf(String str, String substring) {
		if (str == null || substring == null || str.length() == 0
				|| substring.length() == 0 || str.length() < substring.length()) {
			return 0;
		}
		int count = 0;
		int pos = 0;
		int idx;
		while ((idx = str.indexOf(substring, pos)) != -1) {
			++count;
			pos = idx + substring.length();
		}
		return count;
	}

	/**
	 * 
	 * Thay the tat ca cac lan xuat hien cua mot chuoi con oldPattern trong
	 * chuoi inString bang chuoi con newPattern
	 * 
	 * @author: Nhantd
	 * @param inString
	 * @param oldPattern
	 * @param newPattern
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String replacesAllOccurrenceOf(String inString,
			String oldPattern, String newPattern) {
		if (!hasLength(inString) || !hasLength(oldPattern)
				|| newPattern == null) {
			return inString;
		}
		StringBuilder sb = new StringBuilder();
		int pos = 0; // position in the old string
		// the index of an occurrence we've found, or -1 if not found
		int index = inString.indexOf(oldPattern);
		int patLen = oldPattern.length();
		while (index >= 0) {
			sb.append(inString).substring(pos, index);
			sb.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		// Remember to append any characters to the right of a match
		sb.append(inString.substring(pos));
		return sb.toString();
	}

	/**
	 * 
	 * Thay the tat ca cac khoang trang ban %20 theo ma Html
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String replacesWhitespaceByHtmlCode(String str) {
		return replacesAllOccurrenceOf(str, " ", "%20");
	}

	/**
	 * 
	 * Xoa tat ca cac lan xuat hien cua chuoi con pattern trong chuoi inString
	 * 
	 * @author: Nhantd
	 * @param inString
	 * @param pattern
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String deletesAllOccurrenceOf(String inString, String pattern) {
		return replacesAllOccurrenceOf(inString, pattern, "");
	}

	/**
	 * 
	 * Xoa tat ca cac character trong charsToDelete xuat hien trong chuoi
	 * inString Ex: String str =
	 * StringUtil.deleteAnyCharacterOccurrenceOf("aabbccad","ad"); Result : str
	 * = "bbcc"
	 * 
	 * @author: Nhantd
	 * @param inString
	 * @param charsToDelete
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String deleteAnyCharacterOccurrenceOf(String inString,
			String charsToDelete) {
		if (!hasLength(inString) || !hasLength(charsToDelete)) {
			return inString;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < inString.length(); i++) {
			char c = inString.charAt(i);
			if (charsToDelete.indexOf(c) == -1) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	// ---------------------------------------------------------------------------
	// Cac phuong thuc lam viec chung voi formatted Strings
	// ---------------------------------------------------------------------------

	/**
	 * 
	 * Trich dan chuoi string voi dau nhay don Ex : String str =
	 * StringUtil.quote("myString"); Result : str = "'myString'"
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String quote(String str) {
		return (str != null ? "'" + str + "'" : null);
	}

	/**
	 * 
	 * Chuyen mot Object thanh String voi dau nhay don Neu no la mot String, giu
	 * nguyen object neu no khong phai la mot string
	 * 
	 * @author: Nhantd
	 * @param obj
	 * @return
	 * @return: Object
	 * @throws:
	 */
	public static Object quoteIfString(Object obj) {
		return (obj instanceof String ? quote((String) obj) : obj);
	}

	/**
	 * 
	 * Tach chuoi string du dieu kien boi mot character phan cach. Ex : String
	 * str = StringUtil.unqualify("this:name:is:qualified",":"); Result : str =
	 * "qualified"
	 * 
	 * @author: Nhantd
	 * @param qualifiedName
	 * @param separator
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String unqualify(String qualifiedName, char separator) {
		return qualifiedName
				.substring(qualifiedName.lastIndexOf(separator) + 1);
	}

	/**
	 * 
	 * Thay doi viet Hoa-thuong ky tu dau tien cua chuoi string
	 * 
	 * @author: Nhantd
	 * @param str
	 * @param capitalize
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length());
		if (capitalize) {
			sb.append(Character.toUpperCase(str.charAt(0)));
		} else {
			sb.append(Character.toLowerCase(str.charAt(0)));
		}
		sb.append(str.substring(1));
		return sb.toString();
	}

	/**
	 * 
	 * Viet hoa ky tu dau tien cua chuoi string
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String captalizeFirstCharacter(String str) {
		return changeFirstCharacterCase(str, true);
	}

	/**
	 * 
	 * Viet thuong ky tu dau tien cua chuoi string
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String uncaptalizeFirstCharacter(String str) {
		return changeFirstCharacterCase(str, false);
	}

	/**
	 * 
	 * Chuyen tieng viet co dau (unicode) thanh tieng anh (eng)
	 * 
	 * @author: Nhantd
	 * @param unicodeString
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String convertUnicodeToEngString(String unicodeString) {
		if (!hasLength(unicodeString)) {
			return "";
		}
		unicodeString = trimWhitespace(unicodeString);

		for (int i = 0; i < marked.length; i++) {
			unicodeString = unicodeString.replace(marked[i], notmarked[i]);
		}
		return unicodeString;
	}

	/**
	 * 
	 * Chuyen doi char thanh ma ASCII
	 * 
	 * @author: Nhantd
	 * @param character
	 * @return
	 * @return: int
	 * @throws:
	 */
	public static int convertCharToASCII(final char character) {
		return (int) character;
	}

	/**
	 * 
	 * Chuyen doi ma ASCII thanh char
	 * 
	 * @author: Nhantd
	 * @param ascii
	 * @return
	 * @return: char
	 * @throws:
	 */
	public static char convertASCIIToChar(final int ascii) {
		return (char) ascii;
	}

	/**
	 * 
	 * Chuyen doi byte[] sang kieu Hex String
	 * 
	 * @author: Nhantd
	 * @param bytes
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String convertBytesToHex(byte[] bytes) {
		final char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] hexChars = new char[bytes.length * 2];
		int v;
		for (int i = 0; i < bytes.length; i++) {
			v = bytes[i] & 0xFF;
			hexChars[i * 2] = hexArray[v >>> 4];
			hexChars[i * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	/**
	 * 
	 * Chuyen phonenumber ve dinh dang so Ex : +84978323434 -> 0978323434
	 * 
	 * @author: BangHN
	 * @param phonestring
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String convertPhonenumberStringToHeader0109(String phonestring) {
		if (!hasLength(phonestring)) {
			return "";
		}
		if (phonestring.startsWith("84")) {
			phonestring = phonestring.substring(2);
			phonestring = "0" + phonestring;
		} else if (phonestring.startsWith("+84")) {
			phonestring = phonestring.substring(3);
			phonestring = "0" + phonestring;
		}
		return phonestring;
	}

	/**
	 * 
	 * Chuyen doi double thanh currency string
	 * 
	 * @author: Nhantd
	 * @param currency
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String convertDoubleToCurrency(double currency) {
		return NumberFormat.getCurrencyInstance().format(currency);
	}

	/**
	 * 
	 * Extract filename tu duong dan path cho truoc Ex: "mypath/myfile.txt" ->
	 * "myfile.txt"
	 * 
	 * @author: Nhantd
	 * @param path
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String getFilename(String path) {
		if (path == null) {
			return null;
		}
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		return (separatorIndex != -1 ? path.substring(separatorIndex + 1)
				: path);
	}

	/**
	 * 
	 * Extract the filename extension Ex: "mypath/myfile.txt" -> "txt"
	 * 
	 * @author: Nhantd
	 * @param path
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String getFilenameExtension(String path) {
		if (path == null) {
			return null;
		}
		int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
		return (sepIndex != -1 ? path.substring(sepIndex + 1) : null);
	}

	/**
	 * 
	 * Bo phan extension ra khoi duong dan path cho truoc
	 * 
	 * @author: Nhantd
	 * @param path
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String stripFilenameExtension(String path) {
		if (path == null) {
			return null;
		}
		int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
		return (sepIndex != -1 ? path.substring(0, sepIndex) : path);
	}

	/**
	 * 
	 * Them duong dan tuong doi vao duong dan cho truoc duoc ngan cach boi java
	 * folder separation ("/")
	 * 
	 * @author: Nhantd
	 * @param path
	 * @param relativePath
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String addRelativePath(String path, String relativePath) {
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		if (separatorIndex != -1) {
			String newPath = path.substring(0, separatorIndex);
			if (!relativePath.startsWith(FOLDER_SEPARATOR)) {
				newPath += FOLDER_SEPARATOR;
			}
			return newPath + relativePath;
		} else {
			return relativePath;
		}
	}

	/**
	 * 
	 * Don gian hoa duong dan thanh "path/.." voi cac dau cham don gian ben
	 * trong Ex : "file:core/name/class/extension/base/activity/main.class" ->
	 * "file:core/../base/activity/main.class"
	 * 
	 * @author: Nhantd
	 * @param path
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String cleanPath(String path) {
		if (path == null) {
			return null;
		}
		String pathToUse = replacesAllOccurrenceOf(path,
				WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);

		// Strip prefix from path to analyze, to not treat it as part of the
		// first path element. This is necessary to correctly parse paths like
		// "file:core/../core/io/Resource.class", where the ".." should just
		// strip the first "core" directory while keeping the "file:" prefix.
		int prefixIndex = pathToUse.indexOf(":");
		String prefix = "";
		if (prefixIndex != -1) {
			prefix = pathToUse.substring(0, prefixIndex + 1);
			pathToUse = pathToUse.substring(prefixIndex + 1);
		}
		if (pathToUse.startsWith(FOLDER_SEPARATOR)) {
			prefix = prefix + FOLDER_SEPARATOR;
			pathToUse = pathToUse.substring(1);
		}
		String[] pathArray = delimitedListToStringArray(pathToUse,
				FOLDER_SEPARATOR);
		List<String> pathElements = new LinkedList<String>();
		int tops = 0;

		for (int i = pathArray.length - 1; i >= 0; i--) {
			String element = pathArray[i];
			if (CURRENT_PATH.equals(element)) {
				// Points to current directory - drop it.
			} else if (TOP_PATH.equals(element)) {
				// Registering top path found.
				tops++;
			} else {
				if (tops > 0) {
					// Merging path element with element corresponding to top
					// path.
					tops--;
				} else {
					// Normal path element found.
					pathElements.add(0, element);
				}
			}
		}
		// Remaining top paths need to be retained.
		for (int i = 0; i < tops; i++) {
			pathElements.add(0, TOP_PATH);
		}

		return prefix
				+ collectionToDelimitedString(pathElements, FOLDER_SEPARATOR);
	}

	/**
	 * 
	 * So sanh hai duong dan sau khi da don gian hoa chung.
	 * 
	 * @author: Nhantd
	 * @param path1
	 *            first path for comparison
	 * @param path2
	 *            second path for comparison
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean pathEquals(String path1, String path2) {
		return cleanPath(path1).equals(cleanPath(path2));
	}

	/**
	 * 
	 * Parse mot chuoi localeString thanh link locale. format ("en", "en_UK",
	 * etc).
	 * 
	 * @author: Nhantd
	 * @param localeString
	 * @return
	 * @return: Locale
	 * @throws:
	 */
	public static Locale parseLocaleString(String localeString) {
		String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
		String language = (parts.length > 0 ? parts[0] : "");
		String country = (parts.length > 1 ? parts[1] : "");
		String variant = "";
		if (parts.length >= 2) {
			// There is definitely a variant, and it is everything after the
			// country
			// code sans the separator between the country code and the variant
			int endIndexOfCountryCode = localeString.indexOf(country)
					+ country.length();
			// Strip off any leading '_' and whitespace, what's left is the
			// variant.
			variant = trimStartingWhitespace(localeString).substring(
					endIndexOfCountryCode);
			if (variant.startsWith("_")) {
				variant = trimStartingCharacter(variant, '_');
			}
		}
		return (language.length() > 0 ? new Locale(language, country, variant)
				: null);
	}

	/**
	 * 
	 * Chuyen chuoi string dang tien thanh chuoi currency string ngan cach boi
	 * dau '.' Ex : 1.600.000
	 * 
	 * @author: Nhantd
	 * @param money
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String parseAmountMoneyWithDot(String money) {
		String result = "";
		if (!hasLength(money) || !ValidateUtils.isValidateNumeric(money)) {
			return "";
		}
		for (int i = money.length() - 1; i >= 0; i--) {
			int offsetLast = money.length() - 1 - i;
			if ((offsetLast > 0) && (offsetLast % 3) == 0
					&& (money.charAt(i) != '+') && (money.charAt(i) != '-')) {
				result = "." + result;
			}
			result = money.charAt(i) + result;
		}
		return result;
	}

	/**
	 * 
	 * Chuyen chuoi string dang tien thanh chuoi currency string ngan cach boi
	 * dau ',' Ex : 1,600,000
	 * 
	 * @author: Nhantd
	 * @param money
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String parseAmountMoneyWithComma(String money) {
		String result = "";
		if (!hasLength(money) || !ValidateUtils.isValidateNumeric(money)) {
			return "";
		}
		for (int i = money.length() - 1; i >= 0; i--) {
			int offsetLast = money.length() - 1 - i;
			if ((offsetLast > 0) && (offsetLast % 3) == 0
					&& (money.charAt(i) != '+') && (money.charAt(i) != '-')) {
				result = "." + result;
			}
			result = money.charAt(i) + result;
		}
		return result;
	}

	/**
	 * 
	 * Xac dinh language tag RFC 3066 thich hop, duoc dung cho HTTP
	 * "Accept-Language" header.
	 * 
	 * @author: Nhantd
	 * @param locale
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String toLanguageTag(Locale locale) {
		return locale.getLanguage()
				+ (hasText(locale.getCountry()) ? "-" + locale.getCountry()
						: "");
	}

	/**
	 * 
	 * Parse dau thap phan va dam gom nhom du : 1,600,000.05
	 * 
	 * @author: Nhantd
	 * @param formatString
	 * @param str
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String decimalFormatSymbols(String formatString, Double str) {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(
				Locale.getDefault());
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(',');
		DecimalFormat df = new DecimalFormat(formatString, otherSymbols);
		return df.format(str);
	}

	/**
	 * 
	 * Parse dau thap phan va dam gom nhom du : 1.600.000,05
	 * 
	 * @author: Duchha
	 * @param formatString
	 * @param str
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String decimalFormatSymbolsVN(String formatString, Double str) {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(
				Locale.getDefault());
		otherSymbols.setDecimalSeparator(',');
		otherSymbols.setGroupingSeparator('.');
		DecimalFormat df = new DecimalFormat(formatString, otherSymbols);
		return df.format(str);
	}

	// ---------------------------------------------------------------------------
	// Cac phuong thuc lam viec chung voi Strings array
	// ---------------------------------------------------------------------------

	/**
	 * 
	 * Them chuoi string vao mang string da cho, tra ve mot mang moi bao gom
	 * mang cho truoc cong voi string moi them vao
	 * 
	 * @author: Nhantd
	 * @param array
	 * @param str
	 * @return
	 * @return: String[]
	 * @throws:
	 */
	public static String[] addStringToArray(String[] array, String str) {
		if (array.length == 0 || array == null) {
			return new String[] { str };
		}
		String[] newArr = new String[array.length + 1];
		System.arraycopy(array, 0, newArr, 0, array.length);
		newArr[array.length] = str;
		return newArr;
	}

	/**
	 * 
	 * Kiem tra xem mot chuoi String co thuoc mot mang String cho truoc hay
	 * khong
	 * 
	 * @author: Nhantd
	 * @param str
	 * @param array
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean containsInArray(String[] array, String str) {
		for (String element : array) {
			if (str.equals(element)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * Ghep 2 mang string thanh mot mang duy nhat khong phan biet giua cac phan
	 * tu, voi cac phan tu xep chong tu 2 mang da cho
	 * 
	 * @author: Nhantd
	 * @param array1
	 * @param array2
	 * @return
	 * @return: String[]
	 * @throws:
	 */
	public static String[] concatenateStringArrays(String[] array1,
			String[] array2) {
		if (array1 == null || array1.length == 0) {
			return array2;
		}
		if (array2 == null || array2.length == 0) {
			return array1;
		}
		String[] newArr = new String[array1.length + array2.length];
		System.arraycopy(array1, 0, newArr, 0, array1.length);
		System.arraycopy(array2, 0, newArr, array1.length, array2.length);
		return newArr;
	}

	/**
	 * 
	 * Merge 2 mang string da cho thanh 1, voi cac phan tu xep chong tu 2 mang
	 * da cho. Thu tu cac phan tu duoc bao toan va bo di cac phan tu trung nhau
	 * (chi tinh lan xuat hien dau tien)
	 * 
	 * @author: Nhantd
	 * @param array1
	 * @param array2
	 * @return
	 * @return: String[]
	 * @throws:
	 */
	public static String[] mergeStringArrays(String[] array1, String[] array2) {
		if (array1 == null || array1.length == 0) {
			return array2;
		}
		if (array2 == null || array2.length == 0) {
			return array1;
		}
		List<String> result = new ArrayList<String>();
		result.addAll(Arrays.asList(array1));
		for (String str : array2) {
			if (!result.contains(str)) {
				result.add(str);
			}
		}
		return toStringArray(result);
	}

	/**
	 * 
	 * Tim giao cua 2 mang string Ex : array1 = {"0","1","2","3"}, array2 =
	 * {"2","3","4","5"} -> result = {"2","3"}
	 * 
	 * @author: Nhantd
	 * @param array1
	 * @param array2
	 * @return
	 * @return: String[]
	 * @throws:
	 */
	public static String[] intersectionStringArrays(String[] array1,
			String[] array2) {
		if (array1 == null || array2 == null) {
			return null;
		}
		List<String> result = new ArrayList<String>();
		for (String str : array1) {
			if (Arrays.asList(array2).contains(str)) {
				result.add(str);
			}
		}
		return toStringArray(result);
	}

	/**
	 * 
	 * Sort mang string theo ascending
	 * 
	 * @author: Nhantd
	 * @param array
	 * @return
	 * @return: String[]
	 * @throws:
	 */
	public static String[] sortAscendingStringArray(String[] array) {
		if (array == null || array.length == 0) {
			return new String[0];
		}
		Arrays.sort(array);
		return array;
	}

	/**
	 * 
	 * Sort mang string theo descending
	 * 
	 * @author: Nhantd
	 * @param array
	 * @return
	 * @return: String[]
	 * @throws:
	 */
	public static String[] sortDescendingStringArray(String[] array) {
		if (array == null || array.length == 0) {
			return new String[0];
		}
		Arrays.sort(array, Collections.reverseOrder());
		return array;
	}

	/**
	 * 
	 * Sao chep Collection cho truoc vao mot mang String. Collection phai chi
	 * chua cac phan tu String. Ex : List<String> -> String[]
	 * 
	 * @author: Nhantd
	 * @param collection
	 * @return
	 * @return: String[]
	 * @throws:
	 */
	public static String[] toStringArray(Collection<String> collection) {
		if (collection == null) {
			return null;
		}
		return collection.toArray(new String[collection.size()]);
	}

	/**
	 * 
	 * Sao chep Enumeration vao mot mang String. Enumeration phai chi chua cac
	 * phan tu String.
	 * 
	 * @author: Nhantd
	 * @param enumeration
	 * @return
	 * @return: String[]
	 * @throws:
	 */
	public static String[] toStringArray(Enumeration<String> enumeration) {
		if (enumeration == null) {
			return null;
		}
		List<String> list = Collections.list(enumeration);
		return list.toArray(new String[list.size()]);
	}

	/**
	 * 
	 * Trim cac phan tu cua mang String cho truoc, goi ham String.trim() cho moi
	 * phan tu.
	 * 
	 * @author: Nhantd
	 * @param array
	 * @return
	 * @return: String[]
	 * @throws:
	 */
	public static String[] trimArrayElements(String[] array) {
		if (array == null || array.length == 0) {
			return new String[0];
		}
		String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			String element = array[i];
			result[i] = (element != null ? trimWhitespace(element) : null);
		}
		return result;
	}

	/**
	 * 
	 * Loai bo cac chuoi String trung lap trong mang String. Dong thoi sap xep
	 * mang bang cach su dung mot TreeSet
	 * 
	 * @author: Nhantd
	 * @param array
	 * @return
	 * @return: String[]
	 * @throws:
	 */
	public static String[] removeDuplicateAndSortStrings(String[] array) {
		if (array == null || array.length == 0) {
			return array;
		}
		Set<String> set = new TreeSet<String>();
		for (String element : array) {
			set.add(element);
		}
		return toStringArray(set);
	}

	/**
	 * 
	 * Tach mot chuoi String xuat hien dau tien boi delimiter trong chuoi da
	 * cho. Khong bao gom delimiter trong ket qua.
	 * 
	 * @author: Nhantd
	 * @param toSplit
	 * @param delimiter
	 * @return
	 * @return: String[] voi phan tu dau tien la chuoi tach va phan tu thu hai
	 *          la delimiter.
	 * @throws:
	 */
	public static String[] split(String toSplit, String delimiter) {
		if (!hasLength(toSplit) || !hasLength(delimiter)) {
			return null;
		}
		int offset = toSplit.indexOf(delimiter);
		if (offset < 0) {
			return null;
		}
		String beforeDelimiter = toSplit.substring(0, offset);
		String afterDelimiter = toSplit.substring(offset + delimiter.length());
		return new String[] { beforeDelimiter, afterDelimiter };
	}

	/**
	 * 
	 * Tach mot chuoi string xuat hien dau tien boi delimiter va xoa cac ky tu
	 * charToDelete co trong chuoi String, voi chuoi String nam trong mang
	 * String da cho
	 * 
	 * @author: Nhantd
	 * @param array
	 * @param delimiter
	 * @param charsToDelete
	 * @return
	 * @return: Properties chua key la chuoi string va value la delimiter
	 * @throws:
	 */
	public static Properties splitArrayElementsIntoProperties(String[] array,
			String delimiter, String charsToDelete) {
		if (array == null || array.length == 0) {
			return null;
		}
		Properties result = new Properties();
		for (String element : array) {
			if (charsToDelete != null) {
				element = deleteAnyCharacterOccurrenceOf(element, charsToDelete);
			}
			String[] splittedElement = split(element, delimiter);
			if (splittedElement == null) {
				continue;
			}
			result.setProperty(splittedElement[0].trim(),
					splittedElement[1].trim());
		}
		return result;
	}

	/**
	 * 
	 * Tach mot chuoi string xuat hien dau tien boi delimiter trong chuoi
	 * String, voi chuoi String nam trong mang String da cho
	 * 
	 * @author: Nhantd
	 * @param array
	 * @param delimiter
	 * @return
	 * @return: Properties chua key la chuoi string va value la delimiter
	 * @throws:
	 */
	public static Properties splitArrayElementsIntoProperties(String[] array,
			String delimiter) {
		return splitArrayElementsIntoProperties(array, delimiter, null);
	}

	/**
	 * 
	 * Tach chuoi String da cho thanh mot mang String voi mot StringTokenizer.
	 * Cac ky tu phan cach cho truoc bao gom bat ky so luong dau phan cach. Moi
	 * ky tu co the duoc dung de tach biet cac token. Mot delimiter luon luon la
	 * mot character duy nhat.
	 * 
	 * @author: Nhantd
	 * @param str
	 * @param delimiters
	 * @param trimTokens
	 * @param ignoreEmptyTokens
	 * @return
	 * @return: String[]
	 * @throws:
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters,
			boolean trimTokens, boolean ignoreEmptyTokens) {
		if (str == null) {
			return null;
		}
		StringTokenizer st = new StringTokenizer(str, delimiters);
		List<String> tokens = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() > 0) {
				tokens.add(token);
			}
		}
		return toStringArray(tokens);
	}

	/**
	 * 
	 * Tach chuoi String cho truoc thanh mot mang String thong qua mot
	 * StringTokenizer. Trims cac token va bo qua cac empty token.
	 * 
	 * @author: Nhantd
	 * @param str
	 * @param delimiters
	 * @return
	 * @return: String[]
	 * @throws:
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters) {
		return tokenizeToStringArray(str, delimiters, true, true);
	}

	/**
	 * 
	 * Tach mot chuoi String voi mot danh sach delimited va chuyen doi no thanh
	 * mot mang String. No van duoc coi la mot chuoi phan cach duy nhat, chu
	 * khong phai la mot tap cac ky tu phan cach - khac voi
	 * tokenizeToStringArray
	 * 
	 * @author: Nhantd
	 * @param str
	 * @param delimiter
	 * @param charsToDelete
	 * @return
	 * @return: String[]
	 * @throws:
	 */
	public static String[] delimitedListToStringArray(String str,
			String delimiter, String charsToDelete) {
		if (str == null) {
			return new String[0];
		}
		if (delimiter == null) {
			return new String[] { str };
		}
		List<String> result = new ArrayList<String>();
		if ("".equals(delimiter)) {
			for (int i = 0; i < str.length(); i++) {
				result.add(deleteAnyCharacterOccurrenceOf(
						str.substring(i, i + 1), charsToDelete));
			}
		} else {
			int pos = 0;
			int delPos;
			while ((delPos = str.indexOf(delimiter, pos)) != -1) {
				result.add(deleteAnyCharacterOccurrenceOf(
						str.substring(pos, delPos), charsToDelete));
				pos = delPos + delimiter.length();
			}
			if (str.length() > 0 && pos <= str.length()) {
				// Add rest of String, but not in case of empty input.
				result.add(deleteAnyCharacterOccurrenceOf(str.substring(pos),
						charsToDelete));
			}
		}
		return toStringArray(result);
	}

	/**
	 * 
	 * Tach mot chuoi String voi mot danh sach delimited va chuyen doi no thanh
	 * mot mang String. No van duoc coi la mot chuoi phan cach duy nhat, chu
	 * khong phai la mot tap cac ky tu phan cach - khac voi
	 * tokenizeToStringArray
	 * 
	 * @author: Nhantd
	 * @param str
	 * @param delimiter
	 * @return
	 * @return: String[]
	 * @throws:
	 */
	public static String[] delimitedListToStringArray(String str,
			String delimiter) {
		return delimitedListToStringArray(str, delimiter, null);
	}

	/**
	 * 
	 * Phuong thuc thuan tien de chuyen doi mot danh sach CSV thanh mot mang
	 * String
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: String[]
	 * @throws:
	 */
	public static String[] commaDelimitedListToStringArray(String str) {
		return delimitedListToStringArray(str, ",");
	}

	/**
	 * 
	 * Phuong thuc thuan tien de chuyen doi mot danh sach CSV string thanh mot
	 * set. Loai bo cac phan tu trung lap va sap xep mang vi dung mot treeset.
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: Set<String>
	 * @throws:
	 */
	public static Set<String> commaDelimitedListToSet(String str) {
		Set<String> set = new TreeSet<String>();
		String[] tokens = commaDelimitedListToStringArray(str);
		for (String token : tokens) {
			set.add(token);
		}
		return set;
	}

	/**
	 * 
	 * Phuong thuc thuan tien de tra ve mot Collection nhu mot delimited (CSV)
	 * String.
	 * 
	 * @author: Nhantd
	 * @param coll
	 * @param delim
	 * @param prefix
	 * @param suffix
	 * @return
	 * @return: String
	 * @throws:
	 */
	@SuppressWarnings("rawtypes")
	public static String collectionToDelimitedString(Collection coll,
			String delim, String prefix, String suffix) {
		if (coll == null || coll.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Iterator<?> it = coll.iterator();
		while (it.hasNext()) {
			sb.append(prefix).append(it.next()).append(suffix);
			if (it.hasNext()) {
				sb.append(delim);
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * Phuong thuc thuan tien de tra ve mot Collection nhu mot delimited (CSV)
	 * String.
	 * 
	 * @author: Nhantd
	 * @param coll
	 * @param delim
	 * @return
	 * @return: String
	 * @throws:
	 */
	@SuppressWarnings("rawtypes")
	public static String collectionToDelimitedString(Collection coll,
			String delim) {
		return collectionToDelimitedString(coll, delim, "", "");
	}

	/**
	 * 
	 * Phuong thuc thuan tien de tra ve mot Collection nhu mot CSV String.
	 * 
	 * @author: Nhantd
	 * @param coll
	 * @return
	 * @return: String
	 * @throws:
	 */
	@SuppressWarnings("rawtypes")
	public static String collectionToDelimitedCSVString(Collection coll) {
		return collectionToDelimitedString(coll, ",");
	}

	/**
	 * 
	 * Phuong thuc thuan tien de tra ve mot mang String nhu mot delimited (CSV)
	 * String.
	 * 
	 * @author: Nhantd
	 * @param array
	 * @param delim
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String arrayToDelimitedString(Object[] array, String delim) {
		if (array == null || array.length == 0) {
			return "";
		}
		if (array.length == 1) {
			return nullSafeToString(array[0]);
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(array[i]);
		}
		return sb.toString();
	}

	/**
	 * 
	 * Phuong thuc thuan tien de tra ve mot mang String nhu mot CSV String
	 * 
	 * @author: Nhantd
	 * @param array
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String arrayToDelimitedCSVString(Object[] array) {
		return arrayToDelimitedString(array, ",");
	}

	// ---------------------------------------------------------------------------
	// Cac phuong thuc lam viec chung voi Strings dang Object
	// ---------------------------------------------------------------------------

	/**
	 * 
	 * Tra ve mot chuoi String dai dien cho cac phan tu cua mot mang object cu
	 * the cho truoc. Ex: "{abc,def,123}"
	 * 
	 * @author: Nhantd
	 * @param array
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String nullSafeToTring(Object[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			} else {
				sb.append(",");
			}
			sb.append(String.valueOf(array[i]));
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * 
	 * Tra ve mot chuoi String dai dien cho cac phan tu cua mot mang boolean cu
	 * the cho truoc.
	 * 
	 * @author: Nhantd
	 * @param array
	 * @return: void
	 * @throws:
	 */
	public static String nullSafeToString(boolean[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			} else {
				sb.append(",");
			}
			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * 
	 * Tra ve mot chuoi String dai dien cho cac phan tu cua mot mang byte cu the
	 * cho truoc.
	 * 
	 * @author: Nhantd
	 * @param array
	 * @return: void
	 * @throws:
	 */
	public static String nullSafeToString(byte[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			} else {
				sb.append(",");
			}
			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * 
	 * Tra ve mot chuoi String dai dien cho cac phan tu cua mot mang char cu the
	 * cho truoc.
	 * 
	 * @author: Nhantd
	 * @param array
	 * @return: void
	 * @throws:
	 */
	public static String nullSafeToString(char[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			} else {
				sb.append(",");
			}
			sb.append("'").append(array[i]).append("'");
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * 
	 * Tra ve mot chuoi String dai dien cho cac phan tu cua mot mang double cu
	 * the cho truoc.
	 * 
	 * @author: Nhantd
	 * @param array
	 * @return: void
	 * @throws:
	 */
	public static String nullSafeToString(double[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			} else {
				sb.append(",");
			}
			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * 
	 * Tra ve mot chuoi String dai dien cho cac phan tu cua mot mang float cu
	 * the cho truoc.
	 * 
	 * @author: Nhantd
	 * @param array
	 * @return: void
	 * @throws:
	 */
	public static String nullSafeToString(float[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			} else {
				sb.append(",");
			}
			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * 
	 * Tra ve mot chuoi String dai dien cho cac phan tu cua mot mang int cu the
	 * cho truoc.
	 * 
	 * @author: Nhantd
	 * @param array
	 * @return: void
	 * @throws:
	 */
	public static String nullSafeToString(int[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			} else {
				sb.append(",");
			}
			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * 
	 * Tra ve mot chuoi String dai dien cho cac phan tu cua mot mang long cu the
	 * cho truoc.
	 * 
	 * @author: Nhantd
	 * @param array
	 * @return: void
	 * @throws:
	 */
	public static String nullSafeToString(long[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			} else {
				sb.append(",");
			}
			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * 
	 * Tra ve mot chuoi String dai dien cho cac phan tu cua mot mang short cu
	 * the cho truoc.
	 * 
	 * @author: Nhantd
	 * @param array
	 * @return: void
	 * @throws:
	 */
	public static String nullSafeToString(short[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			} else {
				sb.append(",");
			}
			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	public static String nullSafeToString(Object obj) {
		if (obj == null) {
			return NULL_STRING;
		}
		if (obj instanceof String) {
			return (String) obj;
		}
		if (obj instanceof Object[]) {
			return nullSafeToTring((Object[]) obj);
		}
		if (obj instanceof boolean[]) {
			return nullSafeToString((boolean[]) obj);
		}
		if (obj instanceof byte[]) {
			return nullSafeToString((byte[]) obj);
		}
		if (obj instanceof char[]) {
			return nullSafeToString((char[]) obj);
		}
		if (obj instanceof double[]) {
			return nullSafeToString((double[]) obj);
		}
		if (obj instanceof float[]) {
			return nullSafeToString((float[]) obj);
		}
		if (obj instanceof int[]) {
			return nullSafeToString((int[]) obj);
		}
		if (obj instanceof long[]) {
			return nullSafeToString((long[]) obj);
		}
		if (obj instanceof short[]) {
			return nullSafeToString((short[]) obj);
		}
		String str = obj.toString();
		return (str != null ? str : EMPTY_STRING);
	}

	/**
	 * 
	 * Tra ve mot dinh dang hex String cua hash code dinh danh mot object
	 * 
	 * @author: Nhantd
	 * @param obj
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String getIdentityHexString(Object obj) {
		return Integer.toHexString(System.identityHashCode(obj));
	}

	// ---------------------------------------------------------------------------
	// Cac phuong thuc ma hoa Strings
	// ---------------------------------------------------------------------------

	/**
	 * 
	 * Ma hoa md5 (Message-Digest algorithm 5) gia tri Hash 128-bit cho chuoi
	 * string
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String md5Encrypt(String str) {
		try {
			byte[] defaultBytes = str.getBytes("UTF-8");
			MessageDigest algorithm;
			algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < messageDigest.length; i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]);
				if (hex.length() == 1) {
					sb.append('0');
				}
				sb.append(hex);
			}
			return sb.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * Sinh ma md5 (code moble server) voi salt
	 * 
	 * @author: Nhantd
	 * @param str
	 *            - mat khau hoac chuoi muon sinh ma md5
	 * @param salt
	 *            - chuoi salt ma hoa, co the la ten dang nhap (lowercase) - quy
	 *            dinh giong voi server
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @return: String
	 * @throws:
	 */
	public static String md5GenerateHash(String str, String salt)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		String hash = "";
		if (null == salt && "".equals(salt)) {
			salt = "";
		}

		str += salt;
		byte[] data = str.getBytes("US-ASCII");
		md.update(data);
		byte[] digest = md.digest();
		for (int i = 0; i < digest.length; i++) {
			String hex = Integer.toHexString(digest[i]);
			if (hex.length() == 1) {
				hex = "0" + hex;
			}
			hex = hex.substring(hex.length() - 2);
			hash += hex;
		}
		return hash;
	}

	/**
	 * 
	 * Sinh ma sha1 (code moble server) voi salt
	 * 
	 * @author: Nhantd
	 * @param str
	 *            - mat khau hoac chuoi muon sinh ma sha1
	 * @param salt
	 *            - chuoi salt ma hoa, co the la ten dang nhap (lowercase) - quy
	 *            dinh giong voi server
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @return: String
	 * @throws:
	 */
	public static String sha1GenerateHash(String str, String salt)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		String hash = "";
		if (null == salt && "".equals(salt)) {
			salt = "";
		}

		str += salt;
		byte[] data = str.getBytes("US-ASCII");
		md.update(data);
		byte[] digest = md.digest();
		for (int i = 0; i < digest.length; i++) {
			String hex = Integer.toHexString(digest[i]);
			if (hex.length() == 1) {
				hex = "0" + hex;
			}
			hex = hex.substring(hex.length() - 2);
			hash += hex;
		}
		return hash;
	}

	/**
	 * 
	 * Ma hoa sha1 (Secure Hash Algorithm 1) bam an toan cho chuoi
	 * 
	 * @author: Nhantd
	 * @param str
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String sha1hashEncrypt(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes("UTF-8"), 0, str.length());
			byte[] sha1hash = md.digest();
			return convertBytesToHex(sha1hash);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
	}

	public static byte[] hexToBytes(String hex) {
		return hexToBytes(hex.toCharArray());
	}

	public static byte[] hexToBytes(char[] hex) {
		int length = hex.length / 2;
		byte[] raw = new byte[length];
		for (int i = 0; i < length; i++) {
			int high = Character.digit(hex[i * 2], 16);
			int low = Character.digit(hex[i * 2 + 1], 16);
			int value = (high << 4) | low;
			if (value > 127) {
				value -= 256;
			}
			raw[i] = (byte) value;
		}
		return raw;
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static String byteToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;

			do {
				if ((0 <= halfbyte) && (halfbyte <= 9)) {
					buf.append((char) ('0' + halfbyte));
				} else {
					buf.append((char) ('a' + (halfbyte - 10)));
				}
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}

		return buf.toString();
	}

	public static String getString(Context context, int id) {
		return context.getResources().getString(id);
	}

	public static boolean isNullOrEmpty(Object object) {
		return (object == null) || (EMPTY_STRING.equals(object));
	}

	/**
	 * 
	 * Định dạng số tiền
	 * 
	 * @author: vuonghv2
	 * @param: price
	 * @return: String
	 */
	public static String decimalFormatSymbols(String formatString, float str) {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(
				Locale.getDefault());
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator('.');
		DecimalFormat df = new DecimalFormat(formatString, otherSymbols);
		return df.format(str);
	}

}
