/**
 * Copyright 2014 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

import com.google.code.microlog4android.LoggerFactory;

/**
 * Mo ta muc dich cua lop (interface)
 * 
 * @author: Duchha
 * @version: 1.0
 * @since: 1.0
 */
public class LogUtils {
	static boolean isDebugMode = false;
	private static String URL_LOG_FILE = "log.txt";
	private static int i = 0;

	/**
	 * 
	 * co thiet lap tinh ghi log hay khong
	 * 
	 * @author: Duchha
	 * @param isDebugMode
	 * @return: void
	 * @throws:
	 */
	public static void setIsDebugMode(boolean isDebugMode) {
		LogUtils.isDebugMode = isDebugMode;
	}

	public static final com.google.code.microlog4android.Logger logger = LoggerFactory
			.getLogger();

	/**
	 * 
	 * log debug 2 tham so
	 * 
	 * @author: Duchha
	 * @param tag
	 * @param msg
	 * @return: void
	 * @throws:
	 */
	public static void d(String tag, String msg) {
		if (isDebugMode) {
			Log.d(tag, msg);
		}
	}

	/**
	 * 
	 * Log debug 3 tham so
	 * 
	 * @author: Duchha
	 * @param tag
	 * @param msg
	 * @param tr
	 * @return: void
	 * @throws:
	 */
	public static void d(String tag, String msg, Throwable tr) {
		if (isDebugMode) {
			Log.d(tag, msg, tr);
		}
	}

	/**
	 * 
	 * log error 2 tham so
	 * 
	 * @author: Duchha
	 * @param tag
	 * @param msg
	 * @return: void
	 * @throws:
	 */
	public static void e(String tag, String msg) {
		if (isDebugMode) {
			Log.e(tag, msg);
		}
	}

	/**
	 * 
	 * log error 3 tham so
	 * 
	 * @author: Duchha
	 * @param tag
	 * @param msg
	 * @param tr
	 * @return: void
	 * @throws:
	 */
	public static void e(String tag, String msg, Throwable tr) {
		if (isDebugMode) {
			Log.e(tag, msg, tr);
		}
	}

	/**
	 * 
	 * log info 2 tham so
	 * 
	 * @author: Duchha
	 * @param tag
	 * @param msg
	 * @return: void
	 * @throws:
	 */
	public static void i(String tag, String msg) {
		if (isDebugMode) {
			Log.i(tag, msg);
		}
	}

	/**
	 * 
	 * log info 3 tham so
	 * 
	 * @author: Duchha
	 * @param tag
	 * @param msg
	 * @param tr
	 * @return: void
	 * @throws:
	 */
	public static void i(String tag, String msg, Throwable tr) {
		if (isDebugMode) {
			Log.i(tag, msg, tr);
		}
	}

	/**
	 * 
	 * ghi log sang file
	 * 
	 * @author: Duchha
	 * @param title
	 * @param content
	 * @return: void
	 * @throws:
	 */
	public static synchronized void logToFile(String title, String content) {
		if (isDebugMode) {
			logger.debug(title + " : " + content + "\r\n");
			logger.debug("-------------------------------------");
		}

	}

	public static synchronized void logToFileInReleaseMode(String title,
			String content) {
		// if(isDebugMode){
		logger.debug(title + " : " + content + "\r\n");
		logger.debug("-------------------------------------");
		// }
	}

	/**
	 * 
	 * ghi log verbose 2 tham so
	 * 
	 * @author: Duchha
	 * @param tag
	 * @param msg
	 * @return: void
	 * @throws:
	 */
	public static void v(String tag, String msg) {
		if (isDebugMode) {
			Log.v(tag, msg);
		}
	}

	/**
	 * 
	 * ghi log verbose 3 tham so
	 * 
	 * @author: Duchha
	 * @param tag
	 * @param msg
	 * @return: void
	 * @throws:
	 */
	public static void v(String tag, String msg, Throwable tr) {
		if (isDebugMode) {
			Log.v(tag, msg, tr);
		}
	}

	/**
	 * 
	 * ghi log warn 2 tham so
	 * 
	 * @author: Duchha
	 * @param tag
	 * @param msg
	 * @return: void
	 * @throws:
	 */
	public static void w(String tag, String msg) {
		if (isDebugMode) {
			Log.w(tag, msg);
		}
	}

	/**
	 * 
	 * ghi log warn 3 tham so
	 * 
	 * @author: Duchha
	 * @param tag
	 * @param msg
	 * @return: void
	 * @throws:
	 */
	public static void w(String tag, String msg, Throwable tr) {
		if (isDebugMode) {
			Log.w(tag, msg, tr);
		}
	}

	/**
	 * 
	 * 
	 * luu file
	 * 
	 * @author: Duchha
	 * @return: void
	 * @throws: @param tag
	 * @throws: @param msg
	 * @since: Jul 22, 2013
	 */
	public static void saveFile(boolean isDebugMode, String tag, String msg) {
		if (isDebugMode) {
			File backupPath = Environment.getExternalStorageDirectory();
			backupPath = new File(backupPath.getPath() + URL_LOG_FILE);

			if (!backupPath.exists()) {
				try {
					backupPath.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				BufferedWriter buf = new BufferedWriter(new FileWriter(
						backupPath, true));
				buf.append("[ " + i + " ] " + tag + " : " + msg);
				buf.newLine();
				buf.append("\n");
				buf.newLine();
				buf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			i++;
		}
	}
}
