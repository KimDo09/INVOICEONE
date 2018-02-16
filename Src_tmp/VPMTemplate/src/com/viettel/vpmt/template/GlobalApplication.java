/*
 * @ClassName: GlobalApplication.java
 * @Date: Jan 9, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;

import com.viettel.invoice.one.R;
import com.viettel.lib.utils.FileUtils;
import com.viettel.lib.utils.StringUtils;
import com.viettel.vpmt.template.base.BaseActivity;
import com.viettel.vpmt.template.constant.ActionConstants;
import com.viettel.vpmt.template.constant.Constants;
import com.viettel.vpmt.template.dtoView.ProfileApp;
import com.viettel.vpmt.template.log.WriteExceptionLogToFile;

/**
 * Global Application cua du an
 * 
 * @author: nhantd
 * @version: 1.0
 * @since: Jan 9, 2014
 */

public class GlobalApplication extends Application {

	// bien dung de kiem tra ghi log - ko can luu khi bi reset
	public boolean isSendLogException = true;
	public static final String APP_CURRENT_TAG = "appCurrentTag";
	public static GlobalApplication instance = null;
	public final String VERSION = "1.1";
	@SuppressWarnings("deprecation")
	public final String PHONE_MODEL_SDK = android.os.Build.MODEL
			+ "_API Level " + android.os.Build.VERSION.SDK;
	public boolean isAppActive; // Check app active
	private Handler mHandler = new Handler();
	private Bitmap bmpData = null; // save data bitmap when enclosed image

	public static Activity currentActivity;
	public static boolean checkSendLogInDay;
	private ArrayList<String> listStackTag = new ArrayList<String>();

	private ProfileApp profile;

	/**
	 * 
	 * Constructor
	 * 
	 * @author: nhantd
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public GlobalApplication() {
		super();
	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Jan 9, 2014
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		configLog();
		instance = this;
	}

	/**
	 * configLog
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: void
	 * @Since: Jun 9, 2014
	 * @throws:
	 */
	private void configLog() {
		Thread.setDefaultUncaughtExceptionHandler(new WriteExceptionLogToFile(
				getApplicationContext()));
	}

	/**
	 * 
	 * get instance of class
	 * 
	 * @author: nhantd
	 * @return: GlobalApplication
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public static GlobalApplication getInstance() {
		if (instance == null) {
			instance = new GlobalApplication();
		}
		return instance;
	}

	/**
	 * 
	 * get PlatForm
	 * 
	 * @author: nhantd
	 * @return: TruongHN
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public String getPlatForm() {
		return Constants.STR_ANDROID;
	}

	/**
	 * 
	 * Set bitmap data
	 * 
	 * @author: BangHN
	 * @return: void
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public void setBitmapData(Bitmap data) {
		bmpData = data;
	}

	/**
	 * 
	 * Get bitmap data
	 * 
	 * @author: BangHN
	 * @return: Bitmap
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public Bitmap getBitmapData() {
		return bmpData;
	}

	/**
	 * 
	 * Huy bo doi tuong bitmap data
	 * 
	 * @author: BangHN
	 * @return: void
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public void rycycleBitmapData() {
		if (bmpData != null) {
			bmpData.recycle();
			bmpData = null;
		}
	}

	/**
	 * 
	 * Set application active
	 * 
	 * @author: HaiTC3
	 * @return: void
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public void setAppActive(boolean isActive) {
		this.isAppActive = isActive;
	}

	/**
	 * 
	 * Check application active
	 * 
	 * @author: HaiTC3
	 * @return: boolean
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public boolean isAppActive() {
		return isAppActive;
	}

	/**
	 * 
	 * Get Application handler
	 * 
	 * @author: nhantd
	 * @return: Handler
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public Handler getAppHandler() {
		return mHandler;
	}

	/**
	 * 
	 * return color from resource
	 * 
	 * @author: nhantd
	 * @return: int
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public int getColor(int resourceId) {
		return getApplicationContext().getResources().getColor(resourceId);
	}

	/**
	 * 
	 * show dialog confirm exit app
	 * 
	 * @param view
	 * @return: void
	 * @throws:
	 * @author: HaiTC3
	 * @date: Oct 16, 2012
	 */
	@SuppressWarnings("deprecation")
	public static void showDialogConfirmExitApp(final BaseActivity view) {
		String notice = StringUtils.getString(view,
				R.string.text_confirm_exit_app);
		String ok = StringUtils.getString(view, R.string.text_button_ok);
		String cancel = StringUtils.getString(view, R.string.text_button_no);
		if (view != null) {
			final AlertDialog alertDialog = new AlertDialog.Builder(view)
					.create();
			alertDialog.setMessage(notice);
			if (!StringUtils.isNullOrEmpty(ok)) {
				alertDialog.setButton2(ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								view.sendBroadcast(ActionConstants.EXIT_APP,
										new Bundle());
								alertDialog.dismiss();
							}
						});
			}
			if (!StringUtils.isNullOrEmpty(cancel)) {
				alertDialog.setButton(cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								alertDialog.dismiss();
							}
						});
			}
			alertDialog.show();
		}
	}

	/**
	 * Pop stack
	 */
	public void popCurrentTag() {
		if (this.listStackTag.size() > 0) {
			this.listStackTag.remove(this.listStackTag.size() - 1);
			FileUtils.saveObject(getApplicationContext(), this.listStackTag,
					APP_CURRENT_TAG);
		}
	}

	/**
	 * Pop all stack
	 */
	public void popAllTag() {
		this.listStackTag.clear();
		FileUtils.saveObject(getApplicationContext(), this.listStackTag,
				APP_CURRENT_TAG);
	}

	/**
	 * @return the checkSendLogInDay
	 */
	public boolean isCheckSendLogInDay() {
		return checkSendLogInDay;
	}

	/**
	 * @param checkSendLogInDay
	 *            the checkSendLogInDay to set
	 */
	public void setCheckSendLogInDay(boolean checkSendLogInDay) {
		GlobalApplication.checkSendLogInDay = checkSendLogInDay;
	}

	public ProfileApp getProfile() {
		if (profile == null) {
			profile = new ProfileApp();
		}
		return profile;
	}

	public void setProfile(ProfileApp profile) {
		this.profile = profile;
	}
}
