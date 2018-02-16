/**
 * Copyright 2014 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

/**
 * @Description: tinh cac chieu dai, rong cua man hinh
 * @author:duchha
 * @since:Feb 7, 2014 5:21:58 PM
 * @version: 1.0
 * @since: 1.0
 * 
 */
public class PhysicalDeviceUtils {
	static String DEVICE_IMEI;

	/**
	 * 
	 * getWidth
	 * 
	 * @author: Duchha
	 * @param mContext
	 * @return
	 * @return: int
	 * @throws:
	 */
	@SuppressWarnings("deprecation")
	public static int getWidth(Context mContext) {
		int width = 0;
		WindowManager wm = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		width = display.getWidth(); // deprecated
		return width;
	}

	/**
	 * 
	 * getHeight
	 * 
	 * @author: Duchha
	 * @param mContext
	 * @return
	 * @return: int
	 * @throws:
	 */
	@SuppressWarnings("deprecation")
	public static int getHeight(Context mContext) {
		int height = 0;
		WindowManager wm = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		height = display.getHeight(); // deprecated
		return height;
	}

	/**
	 * 
	 * convertDPToPixels
	 * 
	 * @author: Duchha
	 * @param mContext
	 * @param dp
	 * @return
	 * @return: int
	 * @throws:
	 */
	public static int convertDPToPixels(Context mContext, int dp) {
		float density = mContext.getResources().getDisplayMetrics().density;
		return (int) (dp * density);
	}

	/**
	 * 
	 * Get device imei
	 * 
	 * @author: HaiTC3
	 * @return: String
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public static String getDeviceIMEI(Context context) {
		if (!StringUtils.hasLength(DEVICE_IMEI)) {
			TelephonyManager telephoneManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			DEVICE_IMEI = telephoneManager.getDeviceId();
		}
		if (!StringUtils.hasLength(DEVICE_IMEI)) {
			DEVICE_IMEI = "deviceImei";
		}
		return DEVICE_IMEI;
	}
}
