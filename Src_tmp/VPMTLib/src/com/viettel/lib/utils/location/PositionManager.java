/**
 * Copyright 2012 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.utils.location;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * Xu ly luong dinh vi, request goi dinh vi va nhan ket qua
 * 
 * @author: TruongHN, BangHN
 * @version: 1.0
 * @since: 1.0
 */
public class PositionManager extends Activity implements LocatingListener {
	final String LOG_TAG = "PositionManager";
	final String LBS = "NETWORK";
	// thoi gian time out lay gps
	public static long TIME_OUT_GPS = 90000;// thoi gian timeout 1p30s
	// 7s thoi gian time out lay lbs
	public static long TIME_OUT_LBS = 7000;
	// thoi gian dinh ki chay luong dinh vi 5p
	public static long TIME_LOC_PERIOD = 300000;
	// accuracy tot nhat cho phep reset luong dinh vi.
	public static long BEST_ACCURACY = 50;
	// accuracy cho phep lay sai so trong qua trinh dinh vi
	public static long MAX_ACCURACY_RADIUS = 300;

	static final int LOCATING_NONE = 0;// trang thai none
	static final int LOCATING_GPS_START = 1;// trang thai start gps
	static final int LOCATING_GPS_ON_PROGRESS = 2;// trang thai dang lay gps
	static final int LOCATING_GPS_FAILED = 3;// trang thai lay gps that bai
	static final int LOCATING_LBS_START = 4;// trang thai start lbs
	static final int LOCATING_LBS_ON_PROGRESS = 5;// trang thai dang lay lbs
	static final int LOCATING_LBS_FAILED = 6;// trang thai lay lbs that bai
	// static final int LOCATING_VIETTEL = 7;// trang thai dinh vi viettel
	int locatingState = LOCATING_NONE;// trang thai dinh vi

	private boolean isStart = false;
	// co su dung lbs cua google khong, mac dinh la co
	public boolean isUseGoogleLBS = true;
	Locating gpsLocating;// dinh vi gps
	Locating lbsLocating;// dinh vi lbs

	// timer dinh thoi lay toa do
	private Timer locTimer = new Timer();
	// task lay toa do dung kem voi timer
	private PositionTimerTask locTask;
	// bat luong LBS truoc
	private boolean isFirstLBS;
	// thoi gian thuc hien request
	long minTime = 1000;
	// khoan cach toi thieu thuc hien request
	float minDistance = 10.0f;
	// aplication context
	private Context context;

	private Handler mHandler = new Handler();

	private static PositionManager instance;

	@SuppressLint("Registered")
	public static PositionManager getInstance() {
		if (instance == null) {
			instance = new PositionManager();
		}
		return instance;
	}

	public PositionManager() {
		// TODO Auto-generated constructor stub
		instance = this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = getApplicationContext();
		super.onCreate(savedInstanceState);
	}

	public void setContext(Context appContext) {
		this.context = appContext;
	}

	public Context getContext() {
		return this.context;
	}

	class PositionTimerTask extends TimerTask {
		public boolean isCancled = false;

		@Override
		public void run() {
			// TODO Auto-generated method stub
		}
	}

	/**
	 * khoi dong lay toa do
	 * 
	 * @author: AnhND
	 * @return: void
	 * @throws:
	 */
	public void start() {
		start(TIME_LOC_PERIOD);
	}

	public void start(long timePeriod) {
		// TODO Auto-generated method stub
		isStart = true;
		isFirstLBS = true;
		isUseGoogleLBS = isEnableGoogleLBS();
		// run 1 luong lbs song song voi luong dinh vi
		if (isEnableGoogleLBS()) {
			// lay lbs google
			getLBSGoogle();
		} else {
			// lay luong lbs truoc, google sau
			onLocationChanged(null);
		}
		locTimer = new Timer();
		locTask = new PositionTimerTask() {
			@Override
			public void run() {
				if (!locTask.isCancled) {
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							locatingState = LOCATING_NONE;
							// co mo lbs hoac gps moi hoat dong dinh vi dc
							if (isEnableGoogleLBS() || isEnableGPS()) {
								requestLocationUpdates();
							}
						}
					});
				}
			}
		};
		locTimer.schedule(locTask, 0, timePeriod);
	}

	/**
	 * stop luong lay toa do, huy cac doi tuong timer, locating
	 * 
	 * @author: BangHN
	 * @return: void
	 * @throws:
	 */
	public void stop() {
		// clear cached toa do dinh vi
		if (Locating.getLastLocation() != null
				&& Locating.getLastLocation().getTime() < System
						.currentTimeMillis() - Locating.MAX_TIME_RESET) {
			Locating.setLastLocation(null);
		}

		isStart = false;
		if (locTask != null) {
			locTask.isCancled = true;
			locTask.cancel();
		}
		if (locTimer != null) {
			locTimer.cancel();
		}
		if (gpsLocating != null) {
			gpsLocating.resetTimer();
		}
		if (lbsLocating != null) {
			lbsLocating.resetTimer();
		}
	}

	/**
	 * quan ly trang thai va goi lay toa do
	 * 
	 * @author: AnhND
	 * @return: void
	 * @throws:
	 */
	private void requestLocationUpdates() {
		synchronized (this) {
			// mo ton tai enable gps hoac lbs moi thuc hien
			if (isEnableGoogleLBS() || isEnableGPS()) {
				switch (locatingState) {
				case LOCATING_NONE:
					Log.e(LOG_TAG, "requestLocationUpdates() - LOCATING_NONE");
					locatingState = LOCATING_GPS_START;
					requestLocationUpdates();
					break;

				case LOCATING_GPS_START:// xem tiep o day
					Log.e(LOG_TAG,
							"requestLocationUpdates() - LOCATING_GPS_START");
					if (gpsLocating == null) {
						gpsLocating = new Locating(context,
								LocationManager.GPS_PROVIDER, this,
								minTime, minDistance);
					}
					if (!gpsLocating.requestLocating(TIME_OUT_GPS)) {
						locatingState = LOCATING_GPS_FAILED;
						requestLocationUpdates();
					} else {
						locatingState = LOCATING_GPS_ON_PROGRESS;
					}
					break;

				case LOCATING_GPS_ON_PROGRESS:
					Log.e(LOG_TAG,
							"requestLocationUpdates() - LOCATING_GPS_ON_PROGRESS");
					break;

				case LOCATING_GPS_FAILED:
					Log.e(LOG_TAG,
							"requestLocationUpdates() - LOCATING_GPS_FAILED");
					if (isUseGoogleLBS) {
						locatingState = LOCATING_LBS_START;
					} else {
						locatingState = LOCATING_NONE;
						// locatingState = LOCATING_VIETTEL;
					}
					requestLocationUpdates();
					break;

				case LOCATING_LBS_START:
					Log.e(LOG_TAG,
							"requestLocationUpdates() - LOCATING_LBS_START");
					if (lbsLocating == null) {
						lbsLocating = new Locating(context,
								LocationManager.NETWORK_PROVIDER, this,
								minTime, minDistance);
					}
					// dinh vi lbs
					if (!lbsLocating.requestLocating(TIME_OUT_LBS)) {
						locatingState = LOCATING_LBS_FAILED;
						requestLocationUpdates();
					} else {
						locatingState = LOCATING_LBS_ON_PROGRESS;
					}
					break;

				case LOCATING_LBS_ON_PROGRESS:
					Log.e(LOG_TAG,
							"requestLocationUpdates() - LOCATING_LBS_ON_PROGRESS");
					break;

				case LOCATING_LBS_FAILED:
					Log.e(LOG_TAG,
							"requestLocationUpdates() - LOCATING_LBS_FAILED");
					locatingState = LOCATING_NONE;
					break;
				}
			}
		}
	}

	/**
	 * Thuc hien goi dinh vi google lbs
	 * 
	 * @author: BangHN
	 * @return: void
	 * @throws:
	 */
	private void getLBSGoogle() {
		(mHandler).post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Locating lbs = new Locating(context,
						LocationManager.NETWORK_PROVIDER, PositionManager.this,
						minTime, minDistance);
				lbs.requestLocating(TIME_OUT_LBS);
			}
		});
	}

	/**
	 * chuyen trang thai khi dinh vi that bai
	 * 
	 * @author: BangHN
	 * @return: void
	 * @throws:
	 */
	private void handleLocatingFailed() {
		synchronized (this) {
			switch (locatingState) {
			case LOCATING_GPS_ON_PROGRESS:
				Log.e(LOG_TAG, "handleLocatingFailed() - GPS TIME OUT");
				locatingState = LOCATING_GPS_FAILED;
				requestLocationUpdates();
				break;
			case LOCATING_LBS_ON_PROGRESS:
				Log.e(LOG_TAG, "handleLocatingFailed() - LBS TIME OUT");
				locatingState = LOCATING_LBS_FAILED;
				requestLocationUpdates();
				break;
			}
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		if (location != null) {
			if (isFirstLBS && LBS.equals(location.getProvider().toString())) {
				isFirstLBS = false;
			}
			Log.e(location.getProvider().toString(),
					"(X,Y)= (" + location.getLatitude() + " , "
							+ location.getLongitude() + " )");

			// broadcast thay doi vi tri
			updatePosition(location.getLongitude(), location.getLatitude(),
					location);
			// neu co 1 truong hop gps success thi chay xong gps se dung tien
			// trinh khong lam gi
			if (locatingState == LOCATING_GPS_ON_PROGRESS) {
				locatingState = LOCATING_NONE;
			}
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		handleLocatingFailed();
		if (provider.toUpperCase().contains("GPS")) {
			// handle your messages
		} else if (provider.toUpperCase().contains("network")) {
			// handle your messages
		}
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTimeout(Locating locating) {
		// TODO Auto-generated method stub
		if (!isFirstLBS) {
			handleLocatingFailed();
		}
		// LBS google luon timeout truoc GPS
		isFirstLBS = false;
	}

	private void updatePosition(double lng, double lat, Location loc) {
		Locating.setLastLocation(loc);
	}

	public boolean getIsStart() {
		return isStart;
	}

	public int getLocatingState() {
		return locatingState;
	}

	public boolean getIsFirstLBS() {
		return isFirstLBS;
	}

	/**
	 * @return the enableGPS
	 */
	public boolean isEnableGPS() {
		return new Locating(context, LocationManager.GPS_PROVIDER, this,
				minTime, minDistance)
				.isEnableProvider();
	}

	public boolean isEnableGoogleLBS() {
		return new Locating(context, LocationManager.NETWORK_PROVIDER, this,
				minTime, minDistance)
				.isEnableProvider();
	}

	/**
	 * Thoi gian time-out cho moi lan dinh vi GPS
	 * 
	 * @author: BangHN
	 * @param time
	 *            : (Long) miliseconds
	 * @return: void
	 */
	public static void setTimeOutGSP(long time) {
		TIME_OUT_GPS = time;
	}

	/**
	 * Thoi gian time-out cho moi lan dinh vi google LBS
	 * 
	 * @author: BangHN
	 * @param time
	 *            : (Long) miliseconds
	 * @return: void
	 */
	public static void setTimeOutGoogleLBS(long time) {
		TIME_OUT_LBS = time;
	}

	/**
	 * Thoi gian ti cho luong dinh vi dinh ky
	 * 
	 * @author: BangHN
	 * @param time
	 *            : (Long) miliseconds
	 * @return: void
	 */
	public static void setTimeOutLocating(long time) {
		TIME_LOC_PERIOD = time;
	}

	/**
	 * Khoang cach tot de reset luong dinh vi Khoang cach chap nhan lay khong
	 * can dinh vi nua
	 * 
	 * @author: BangHN
	 * @param accuracy
	 *            : (Long) don vi met
	 * @return: void
	 */
	public static void setAccuracy(long accuracy) {
		BEST_ACCURACY = accuracy;
	}

	/**
	 * Ban kinh cho phep lay dinh vi, (sai so trong pham vi ban kinh)
	 * 
	 * @author: BangHN
	 * @param radiusAccuracy
	 *            : (Long) sai so ban kinh cho phep (don vi: met)
	 * @return: void
	 * @throws:
	 */
	public static void setRadiusAccuracy(long radiusAccuracy) {
		MAX_ACCURACY_RADIUS = radiusAccuracy;
	}
}
