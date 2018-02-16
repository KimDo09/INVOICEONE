/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.common;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;

import com.viettel.lib.commonobject.SenderAction;
import com.viettel.lib.logs.SendLogMessRequest;
import com.viettel.lib.utils.DateUtils;
import com.viettel.lib.utils.PhysicalDeviceUtils;
import com.viettel.vpmt.template.GlobalApplication;
import com.viettel.vpmt.template.model.UserModel;

/**
 * 
 * log to server
 * 
 * @author: YenNTH
 * @version: 1.0
 * @since: 1.0
 */
public class TraceUnexceptionLog implements UncaughtExceptionHandler, Runnable {
	private Thread.UncaughtExceptionHandler unExceptionHandler;

	private Activity activity;

	public TraceUnexceptionLog(Activity app) {
		this.unExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		activity = app;
	}

	@Override
	public void uncaughtException(Thread paramThread, Throwable e) {

		// send loi len server
		SendLogMessRequest sendLog = new SendLogMessRequest();
		sendLog.setErrorCode("220");// loi force_colse
		sendLog.setImei(PhysicalDeviceUtils.getDeviceIMEI(activity));
		sendLog.setContentError(getContentError(paramThread, e));
		sendLog.setTimeSendLog(DateUtils.getStringCurrentDate(true));

		SenderAction senderhttp = new SenderAction();
		senderhttp.sender = activity;
		senderhttp.messRequest = sendLog;
		// senderhttp.action = ActionConstants.ACTION_SEND_LOG_ERROR;
		senderhttp.method = ServerPath.SERVER_PATH + "logerror";
		// senderhttp.messResponses = LogErrorRBResponseMessage.class;
		if (GlobalApplication.getInstance().isSendLogException) {
			UserModel.getInstance().sendLogServer(senderhttp, true);
		}

		unExceptionHandler.uncaughtException(paramThread, e);
	}

	String getContentError(Thread thread, Throwable e) {
		StackTraceElement[] arr = e.getStackTrace();
		String report = e.toString() + "\n\n";
		report += "------------------ Stack trace error----------------------\n";
		for (int i = 0; i < arr.length; i++) {
			report += "    " + arr[i].toString() + "\n";
		}
		report += "-------------------------------------------------------\n\n";

		// If the exception was thrown in a background thread inside
		// AsyncTask, then the actual exception can be found with getCause
		report += "----------------------------- Cause ---------------------\n\n";
		Throwable cause = e.getCause();
		if (cause != null) {
			report += cause.toString() + "\n\n";
			arr = cause.getStackTrace();
			for (int i = 0; i < arr.length; i++) {
				report += "    " + arr[i].toString() + "\n";
			}
		}
		report += "-------------------------------\n\n";
		return report;
	}

	@Override
	public void run() {

	}

}
