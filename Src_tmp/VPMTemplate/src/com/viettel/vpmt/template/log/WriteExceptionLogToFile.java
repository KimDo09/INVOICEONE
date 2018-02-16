/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SimpleTimeZone;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.viettel.lib.utils.PhysicalDeviceUtils;

/**
 * 
 * WriteExceptionLogToFile
 * 
 * @author: vuonghv2
 * @version: 1.0
 * @since: 1.0
 */
public class WriteExceptionLogToFile implements UncaughtExceptionHandler,
		Runnable {
	private Thread.UncaughtExceptionHandler unExceptionHandler;
	private static Context mContext;
	private static String URL_LOG_FILE = "";
	private static int widthPixels;
	private static int heightPixels;
	private static final String DATE_FORMAT_STANDARD = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_FORMAT_SHORT = "dd_MM_yyyy";

	public WriteExceptionLogToFile(Context context) {
		this.unExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		mContext = context;
	}

	@Override
	public void uncaughtException(Thread paramThread, Throwable e) {

		// LogUtils.d("Error Crash", getContentError(paramThread, e));
		saveFile(true, "", getContentError(paramThread, e));
		unExceptionHandler.uncaughtException(paramThread, e);
	}

	/**
	 * 
	 * 
	 * getContentError
	 * 
	 * @author: vuonghv2
	 * @return: void
	 * @throws: @param tag
	 * @throws: @param msg
	 * @since: June 04, 2014
	 */
	public String getContentError(Thread thread, Throwable e) {
		StackTraceElement[] arr = e.getStackTrace();
		String report = "";

		Throwable cause = e.getCause();
		if (cause != null) {
			report += cause.toString() + "\n\n";
			arr = cause.getStackTrace();
			for (int i = 0; i < arr.length; i++) {
				report += "    " + arr[i].toString() + "\n";
			}
		}
		return report;
	}

	@Override
	public void run() {

	}

	/**
	 * 
	 * 
	 * Save File
	 * 
	 * @author: vuonghv2
	 * @return: void
	 * @throws: @param tag
	 * @throws: @param msg
	 * @since: June 04, 2014
	 */
	public static void saveFile(boolean isDebugMode, String tag, String msg) {
		if (isDebugMode) {
			int idUser = 1235;

			File backupPath = Environment.getExternalStorageDirectory();
			URL_LOG_FILE = "/" + ConfigLog.LogForder + "_"
					+ getStringCurrentDateShort(true) + ".txt";

			backupPath = new File(ConfigLog.FILE_ROOT + URL_LOG_FILE);
			if (!backupPath.exists()) {
				try {
					backupPath.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			getResolution();
			String imei = PhysicalDeviceUtils.getDeviceIMEI(mContext);
			String deviceName = android.os.Build.MODEL;
			String deviceManufacurer = android.os.Build.MANUFACTURER;
			String version = android.os.Build.VERSION.RELEASE;
			String timeCurrent = getStringCurrentDate(true);

			try {
				BufferedWriter buf = new BufferedWriter(new FileWriter(
						backupPath, true));
				buf.append("============START=================\n");
				buf.append("USER ID " + idUser + "\n");
				buf.append("LOẠI THIẾT BỊ: " + deviceName + "\n");
				buf.append("IMEI THIẾT BỊ: " + imei + "\n");
				buf.append("HÃNG SẢN SUẤT: " + deviceManufacurer + "\n");
				buf.append("PHIÊN BẢN HĐH: " + version + "\n");
				buf.append("ĐỘ PHÂN GIẢI MÀN HÌNH: (" + widthPixels + ","
						+ heightPixels + ")\n");

				buf.append("MẬT ĐỘ ĐIỂM ẢNH: " + getdensityDpi() + "\n");
				buf.append("THỜI GIAN LỖI: " + timeCurrent + "\n");
				buf.append("NGUYÊN NHÂN LỖI: \n");
				buf.append("" + msg);
				buf.newLine();
				buf.append("================END=================\n");
				buf.newLine();
				buf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * 
	 * getdensityDpi
	 * 
	 * @author: vuonghv2
	 * @return: void
	 * @throws: @param tag
	 * @throws: @param msg
	 * @since: June 04, 2014
	 */
	public static int getdensityDpi() {
		DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
		return dm.densityDpi;
	}

	/**
	 * 
	 * 
	 * getResolution Device
	 * 
	 * @author: vuonghv2
	 * @return: void
	 * @throws: @param tag
	 * @throws: @param msg
	 * @since: June 04, 2014
	 */
	public static void getResolution() {
		WindowManager w = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		Display d = w.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		d.getMetrics(metrics);
		// since SDK_INT = 1;
		widthPixels = metrics.widthPixels;
		heightPixels = metrics.heightPixels;
		// includes window decorations (statusbar bar/menu bar)
		if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
			try {
				widthPixels = (Integer) Display.class.getMethod("getRawWidth")
						.invoke(d);
				heightPixels = (Integer) Display.class
						.getMethod("getRawHeight").invoke(d);
			} catch (Exception ignored) {
			}
		// includes window decorations (statusbar bar/menu bar)
		if (Build.VERSION.SDK_INT >= 17)
			try {
				Point realSize = new Point();
				Display.class.getMethod("getRealSize", Point.class).invoke(d,
						realSize);
				widthPixels = realSize.x;
				heightPixels = realSize.y;
			} catch (Exception ignored) {
			}
	}

	/**
	 * 
	 * Lay chuoi string thoi gian hien tai theo dinh dang chuan : dd-MM-yyyy
	 * HH:mm:ss
	 * 
	 * @author: Nhantd
	 * @param getLocalTime
	 *            - false : default khong get timezone "GMT" local - true : get
	 *            timezone "GMT" local
	 * @return
	 * @return: String
	 * @throws:
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
	 * Lay chuoi string thoi gian hien tai theo dinh dang chuan : dd-MM-yyyy
	 * HH:mm:ss
	 * 
	 * @author: Nhantd
	 * @param getLocalTime
	 *            - false : default khong get timezone "GMT" local - true : get
	 *            timezone "GMT" local
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String getStringCurrentDateShort(boolean getLocalTime) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_SHORT);
		if (getLocalTime) {
			sdf.setTimeZone(new SimpleTimeZone(0, "GMT"));
		}
		return sdf.format(cal.getTime());
	}

}
