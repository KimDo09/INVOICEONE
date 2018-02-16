/**
 * Copyright 2014 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.log;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import com.viettel.lib.utils.DateUtils;
import com.viettel.vpmt.template.GlobalApplication;

/**
 * SendLogService
 * 
 * @author: vuonghv2
 * @version: 1.0
 * @since: May 12, 2014
 */
public class SendLogService extends Service {

	private TimerTask timerTask;
	private Timer timer;
	private static Date dateSendLog = DateUtils.formatDateWithFormat(
			new Date(), "dd-MM-yyyy");

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {

		super.onCreate();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {

		super.onStart(intent, startId);
		if (intent != null) {
			if (intent.getAction().equals(ConfigLog.ACTION_SERVICE)) {
				timerTask = new TimerTask() {
					public void run() {
						int timeCurrent = DateUtils.getHour(new Date());

						if (dateSendLog.before(DateUtils.formatDateWithFormat(
								new Date(), "dd-MM-yyyy"))) {
							GlobalApplication.getInstance()
									.setCheckSendLogInDay(false);
						}
						// Kiểm tra ngày hiện tại đã send log tới server hay
						// chưa
						if (!GlobalApplication.getInstance()
								.isCheckSendLogInDay()
								&& isNetworkAvailable(GlobalApplication
										.getInstance().getApplicationContext())) {
							if ((timeCurrent >= ConfigLog.timeSendLogToServer)
									&& (getTotalFile() > 0)) {
								sendFileLogToServer();
							}
						}

					}
				};
			}
		}
		timer = new Timer();
		timer.schedule(timerTask, ConfigLog.timeRepeat, ConfigLog.timeRepeat);
	}

	/**
	 * isNetworkAvailable
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: boolean
	 * @Since: May 11, 2014
	 * @throws:
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED
							|| info[i].getState() == NetworkInfo.State.CONNECTING) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * getTotalFile
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: int
	 * @Since: Jun 05, 2014
	 * @throws:
	 */
	public int getTotalFile() {
		File f = new File(ConfigLog.FILE_ROOT);
		File listFile[] = f.listFiles();
		return listFile.length;
	}

	/**
	 * sendFileLogToServer
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: void
	 * @Since: Jun 05, 2014
	 * @throws:
	 */
	public void sendFileLogToServer() {
		File f = new File(ConfigLog.FILE_ROOT);
		File listFile[] = f.listFiles();
		for (int i = 0; i < listFile.length; i++) {
			new SendLogByAsyncTask(ConfigLog.FILE_ROOT + "/"
					+ listFile[i].getName(), new SendLogListener() {

				@Override
				public void finishSendLog(String path) {
					// TODO Auto-generated method stub
					// Xóa file nếu send hoàn thành
					File file = new File(path);
					delete(file);
				}
			}).execute();
			if (i == listFile.length - 1) {
				GlobalApplication.getInstance().setCheckSendLogInDay(true);
				dateSendLog = DateUtils.formatDateWithFormat(new Date(),
						"dd-MM-yyyy");
			}
		}
	}

	/**
	 * delete
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: boolean
	 * @Since: May 14, 2014
	 * @throws:
	 */
	public static boolean delete(File path) {

		boolean result = true;
		if (path.exists()) {
			if (path.isDirectory()) {
				for (File child : path.listFiles()) {
					result &= delete(child);
				}
				result &= path.delete(); // Delete empty directory.
			}
			if (path.isFile()) {
				result &= path.delete();
			}
			if (!result) {
				Log.e(null, "Delete failed;");
			}
			return result;
		} else {
			Log.e(null, "File does not exist.");
			return false;
		}
	}
}
