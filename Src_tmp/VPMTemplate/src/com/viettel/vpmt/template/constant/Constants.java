/*
 * @ClassName: Constants.java
 * @Date: Jan 7, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.constant;

/**
 * Mo ta Types: Chua cac hang so trong project [Int-String]
 * 
 * @author: duchha
 * @version: 1.0
 * @since: Jan 7, 2014
 */

public class Constants {
	// Contanst
	public static final String STR_ANDROID = "Android";

	public static final String URL_LOG_FILE = "/touristone_log.txt";

	// key viettel map
	public static final String keyViettelMap = "TTUDCNTT_KEY_TEST";
	public static final String idViettelMap = "vtmap@2012";
	// thoi gain hien thị Toast
	public static final int TIME_TOAST = 10000;
	// Khoang cach de yeu cau lay lai location (1km)
	public static final int DISTANCE_WAKEUP_SERVICE_UPLOAD_LOCATION = 10 * 1;
	// dinh dang font chu
	public static final int NORMAL = 0;
	public static final int BOLD = 1;
	public static final int ITALIC = 2;
	public static final int UNDERLINE = 4;
	public static final int SUCCESS = 1;
	public static final int ERROR = 0;
	// db name
	public static final String DATABASE_NAME = "vt_template.db";
	public static final String DATABASE_PATH = "/data/data/com.viettel.vpmt.template/databases/";
	// db version
	public static final int DATABASE_VERSION = 1;

	// calendar type
	public static final int CALENDAR_VIEW_SINGLE = 1;
	public static final int CALENDAR_VIEW_MULTI = 2;
	public static final int CALENDAR_VIEW_RANGE = 3;

	public static final int ACTION_DELETE = 0;
	public static final int ACTION_INSERT = 1;
	public static final int ACTION_UPDATE = 2;
}
