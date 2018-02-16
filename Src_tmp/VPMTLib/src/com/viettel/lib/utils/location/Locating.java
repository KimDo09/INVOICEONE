/**
 * Copyright 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.utils.location;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * Lay toa do hien hanh, ho tro timeout
 * 
 * @author: AnhND, BangHN
 * @version: 1.0
 * @since: 1.0
 */
public class Locating implements LocationListener {
	static final String TAG = Locating.class.toString();
	// thong tin dinh vi lan gan day
	private static Location lastLoc= new Location(LocationManager.NETWORK_PROVIDER);
	// The maximum time that should pass before the user gets a location update.
	public static long MAX_TIME_UPDATE = 180000;
	public static long MAX_TIME_RESET = 600000;
	// accuracy reset dinh vi (m)
	public static final int BEST_ACCURACY = 50;

	String providerName = "";// ten provider dinh vi(gps, lbs)
	Timer timeOutTimer = null;// quan ly timeout
	LocatingListener listener;// listener
	// thoi gian thuc hien request
	long minTime=0;
	// khoan cach toi thieu thuc hien request
	float minDistance=0.0f;
	TimeOutTask timeOutTask;
	boolean isRuning = false;
	Location location = null;

	// handler
	private Handler mHandler = new Handler();
	// aplication context
	private Context appContext;

	class TimeOutTask extends TimerTask {
		public boolean isCancel = false;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (!isCancel) {
						LocationManager locManager = (LocationManager) appContext
								.getSystemService(Context.LOCATION_SERVICE);
						locManager.removeUpdates(Locating.this);
						Locating.this.resetTimer();
						listener.onTimeout(Locating.this);
					}
				}
			});
		}
	}

	/**
	 * Lay toa do cached vi tri dinh vi lan gan day
	 * 
	 * @author: BangHN
	 * @return: Location (Thong tin vi tri, thoi gian,...)
	 * @throws:
	 */
	public static Location getLastLocation() {
		return lastLoc;
	}

	/**
	 * Set vi tri dinh vi (cached location)
	 * 
	 * @author: BangHN
	 * @return: Location (Thong tin vi tri, thoi gian,...)
	 * @throws:
	 */
	public static void setLastLocation(Location loc) {
		lastLoc = loc;
	}

	public Locating(Context appContext, String locationProviderName,
			LocatingListener listener, long minTime, float minDistance) {
		this.appContext = appContext;
		this.providerName = locationProviderName;
		this.listener = listener;
		this.minTime=minTime;
		this.minDistance=minDistance;
	}

	public String getProviderName() {
		return providerName;
	}

	public void resetTimer() {
		try {
			if (timeOutTimer != null) {
				timeOutTimer.cancel();
				timeOutTimer = null;

				timeOutTask.isCancel = true;
				timeOutTask.cancel();
				timeOutTask = null;

				LocationManager locManager = (LocationManager) appContext
						.getSystemService(Context.LOCATION_SERVICE);
				locManager.removeUpdates(Locating.this);
			}
		} catch (Throwable th) {
			Log.d(TAG, "Msg exception resetTimer: " + th.getMessage());
			Log.d(TAG, "Cause exception resetTimer: " + th.getCause());
		}
	}

	/**
	 * Kiem tra location provider (gps, network) co enable hay khong
	 * 
	 * @author: BANGHN
	 * @return (true||false)
	 */
	public boolean isEnableProvider() {
		try {
			if (appContext != null) {
				LocationManager locManager = (LocationManager) appContext
						.getSystemService(Context.LOCATION_SERVICE);
				if (locManager.isProviderEnabled(providerName)) {
					return true;
				}
			}
		} catch (Throwable th) {
			Log.d(TAG, "Msg exception isEnableProvider: " + th.getMessage());
			Log.d(TAG, "Cause exception isEnableProvider: " + th.getCause());
			return false;
		}
		return false;
	}

	/**
	 * thuc hien lay toa do
	 * 
	 * @author: AnhND
	 * @param timeout
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public boolean requestLocating(long timeout) {
		// TODO Auto-generated method stub
		boolean result = false;
		LocationManager locManager = (LocationManager) appContext
				.getSystemService(Context.LOCATION_SERVICE);
		if (locManager.isProviderEnabled(providerName)) {
			locManager.requestLocationUpdates(providerName, minTime,minDistance,
					Locating.this);
			timeOutTimer = new Timer();
			timeOutTask = new TimeOutTask();
			timeOutTimer.schedule(timeOutTask, timeout);
			result = true;
		} else {
			result = false;
		}
		isRuning = result;
		return result;
	}

	@Override
	public void onLocationChanged(Location loc) {
		// bussiness N minus ago
		long minTime = System.currentTimeMillis() - MAX_TIME_UPDATE;

		Location oldLoc = lastLoc;
		Location bestResult = null;
		float bestAccuracy = Float.MAX_VALUE;
		long bestTime = Long.MIN_VALUE;

		if (oldLoc != null && oldLoc.getTime() > minTime) {
			bestResult = oldLoc;
			bestTime = oldLoc.getTime();
			bestAccuracy = oldLoc.getAccuracy();
		}
		LocationManager locationManager = (LocationManager) appContext
				.getSystemService(Context.LOCATION_SERVICE);

		List<String> matchingProviders = locationManager.getAllProviders();
		for (String provider : matchingProviders) {
			Location location = locationManager.getLastKnownLocation(provider);
			if (location != null) {
				float accuracy = location.getAccuracy();
				long time = location.getTime();

				if ((time > minTime && accuracy < bestAccuracy)) {
					bestResult = location;
					bestAccuracy = accuracy;
					bestTime = time;
				} else if (time < minTime && bestAccuracy == Float.MAX_VALUE
						&& time > bestTime) {
					bestResult = location;
					bestTime = time;
				}
			}
		}
		Log.e("Locating", "  Provider :  "
				+ bestResult.getProvider().toString() + "  -  [lat-lng]: ("
				+ bestResult.getLatitude() + " , " + bestResult.getLongitude()
				+ ")" + "  -  Accuracy :  " + bestAccuracy);

		if (locationManager != null && (bestTime > minTime)) {
			if (bestAccuracy < BEST_ACCURACY) {
				resetTimer();
			}
			if (listener != null) {
				listener.onLocationChanged(bestResult);
			}
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		resetTimer();
		if (listener != null) {
			listener.onProviderDisabled(provider);
		}
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		if (listener != null) {
			listener.onProviderEnabled(provider);
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		if (listener != null) {
			listener.onStatusChanged(provider, status, extras);
		}
	}
}
