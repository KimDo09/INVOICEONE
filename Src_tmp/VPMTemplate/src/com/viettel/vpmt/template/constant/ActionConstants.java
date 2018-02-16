/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.constant;

/**
 * 
 * Mo ta Types :Chua cac hang so Action
 * 
 * @author: duchha
 * @version: 1.0
 * @since: Jan 7, 2014
 */
public class ActionConstants {

	// Broad cast recevie

	public static final String ACTION_BROADCAST = "com.viettel.action.broadcast";
	public static final String PERMISSION_BROADCAST = "com.viettel.project.common.permission.BROADCAST";
	// su kien get location broadcast
	public static final int ACTION_TRACKER_LOCATION = 200;
	// su kien dang nhap- dang xuat
	public static final int ACTION_LOGIN_HTTP = 1;
	public static final int ACTION_LOGIN_HTTPS = ACTION_LOGIN_HTTP + 1;
	public static final int ACTION_LOGOUT = ACTION_LOGIN_HTTPS + 1;

	// su kien chuyen activity
	public static final int ACTION_MOVE_SIGNIN = ACTION_LOGOUT + 1;
	public static final int ACTION_MOVE_MAIN_HOME_VIEW = ACTION_MOVE_SIGNIN + 1;
	public static final int ACTION_OK = ACTION_MOVE_MAIN_HOME_VIEW + 1;
	public static final int ACTION_CANCEL = ACTION_OK + 1;
	public static final int ACTION_SINGE_CHOICE = ACTION_CANCEL + 1;
	public static final int ACTION_MULTI_CHOICE = ACTION_SINGE_CHOICE + 1;
	public static final int ACTION_REQUEST_DATA_FROM_SERVER = ACTION_SINGE_CHOICE + 1;
	public static final int ACTION_POST_DATA_TO_SERVER = ACTION_REQUEST_DATA_FROM_SERVER + 1;

	public static final int EXIT_APP = ACTION_MULTI_CHOICE + 1;

	// su kien start app convert doc to pdf
	public static final int ACTION_START_CONVERT_APP = EXIT_APP + 1;

	// Su kien lay du lieu tu database
	public static final int ACTION_GET_ALL_CITIES = ACTION_START_CONVERT_APP + 1;
	public static final int ACTION_ADD_NEW_CITY = ACTION_GET_ALL_CITIES + 1;
	public static final int ACTION_DELETE_ALL_CITY = ACTION_ADD_NEW_CITY + 1;
	public static final int ACTION_GET_ALL_EMPLOYEES = ACTION_DELETE_ALL_CITY + 1;
	public static final int ACTION_ADD_NEW_EMPLOYEE = ACTION_GET_ALL_EMPLOYEES + 1;

	// phan biet request tu view len db hay webservice
	public static final boolean ACTION_IS_REQUEST_TO_DB = true;

	public static final int ACTION_SEARCH_EMPLOYEE_BY_NAME = ACTION_ADD_NEW_EMPLOYEE + 1;
	public static final int ACTION_SEARCH_EMPLOYEE_BY_CITY = ACTION_SEARCH_EMPLOYEE_BY_NAME + 1;
	public static final int ACTION_SEARCH_EMPLOYEE_BY_NAME_AND_CITYID = ACTION_SEARCH_EMPLOYEE_BY_CITY + 1;
	// su kien open app doc to pdf tren google play
	// INVOICE.ONE Actions
	public static final int ACTION_CHECK_LOGIN = ACTION_SEARCH_EMPLOYEE_BY_NAME_AND_CITYID + 1;
	public static final int ACTION_GET_LIST_CUSTOMER = ACTION_CHECK_LOGIN + 1;
	public static final int ACTION_GOTO_DETAIL_CUSTOMER = ACTION_GET_LIST_CUSTOMER + 1;

}
