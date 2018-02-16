/**
 * Copyright 2014 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Mo ta muc dich cua lop (interface)
 * 
 * @author: Duchha
 * @version: 1.0
 * @since: 1.0
 */
public class ExceptionUtils {
	private static final String LOG_TAG = "ExceptionUtils";
	public static final int IOException = 1001;
	public static final int MalformedURLException = 1002;
	public static final int FileNotFoundException = 1003;
	public static final int SocketTimeoutException = 1004;
	public static final int UnknownHostException = 1005;
	public static final int Throwable = 1006;

	public static void showException(int keyException, Object excepWhat) {
		switch (keyException) {
		case IOException:
			IOException e = (java.io.IOException) excepWhat;
			LogUtils.d(LOG_TAG + " IOException", e.getMessage());
			break;
		case MalformedURLException:
			MalformedURLException me = (java.net.MalformedURLException) excepWhat;
			LogUtils.d(LOG_TAG + " MalformedURLException", me.getMessage());
			break;
		case FileNotFoundException:
			FileNotFoundException fe = (java.io.FileNotFoundException) excepWhat;
			LogUtils.d(LOG_TAG + " FileNotFoundException", fe.getMessage());

			break;
		case SocketTimeoutException:
			SocketTimeoutException se = (java.net.SocketTimeoutException) excepWhat;
			LogUtils.d(LOG_TAG + " SocketTimeoutException", se.getMessage());

			break;
		case UnknownHostException:
			UnknownHostException ue = (java.net.UnknownHostException) excepWhat;
			LogUtils.d(LOG_TAG + " UnknownHostException", ue.getMessage());

			break;
		case Throwable:
			Throwable te = (java.lang.Throwable) excepWhat;
			LogUtils.d(LOG_TAG + " Throwable", te.getMessage());

			break;

		default:
			break;
		}

	}

}
