/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.log;

import android.os.Environment;

/**
 * ConfigLog
 * 
 * @author: vuonghv2
 * @version: 1.0
 * @since: Jun 6, 2014
 */

public class ConfigLog {
	// level Log
	public static final int TRACE = 1;
	public static final int DEBUG = 2;
	public static final int INFO = 3;
	public static final int WARN = 4;
	public static final int ERROR = 5;
	public static final int FATAL = 6;

	public static final String SERVER_SEND_LOG = "http://192.168.0.117/post_data_receiver.php";
	public static final String ACTION_SERVICE = "com.viettel.action.service";
	private static final String SDCARD_ROOT = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/";
	public static final String LogForder = "CRM_LOG";
	public static final String FILE_ROOT = SDCARD_ROOT + LogForder;
	public static int timeRepeat = 1000;
	public static int timeSendLogToServer = 9;
}
