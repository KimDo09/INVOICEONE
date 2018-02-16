/**
 * Copyright ${year} Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.service;

import com.viettel.lib.utils.location.PositionManager;
import com.viettel.vpmt.template.constant.KeyConstants;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Class service tracking location
 * 
 * @author: nhantd
 * @version: 1.0
 * @since: Feb 7, 2014
 */

public class TrackerLocationService extends Service implements LocationListener {

	// Check GPS status
	boolean isGPSEnable = false;
	// Check network status
	boolean isNetworkEnable = false;
	// check get location
	boolean canGetLocation = false;

	Location location; // location
	Double latitude; // latitude
	Double longitude; // longitude

	// Declaring a location manager
	protected LocationManager locationManager;

	// The minimum distance to change Updates in meters
	private long min_distance_change_for_update = 1000; // 1000
														// meters
	// The minimum time between updates in milliseconds
	private long min_time_bw_update = 1000 * 60; // 1 minute

	public TrackerLocationService() {
		getLocation();
	}

	/**
	 * 
	 * Method get location
	 * 
	 * @author: nhantd
	 * @return: Location
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public Location getLocation() {
		try {
			if (locationManager == null) {
				locationManager = (LocationManager) getApplicationContext()
						.getSystemService(LOCATION_SERVICE);
			}

			// ---gettings GPS status
			isGPSEnable = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// ---getting network status
			isNetworkEnable = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnable) {
				// ---No GPS Enable
				if (!isNetworkEnable) {
					// ---No network
				} else {
					Log.d("service", "network location");
					this.canGetLocation = true;
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.NETWORK_PROVIDER,
								min_time_bw_update,
								min_distance_change_for_update, this);
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							} else {
								latitude = 0.0;
								longitude = 0.0;
							}
						}
					}
				}
			} else {
				Log.d("service", "GPS location");
				this.canGetLocation = true;
				// ---First get location from GPS if has
				locationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER, min_time_bw_update,
						min_distance_change_for_update, this);
				if (locationManager != null) {
					location = locationManager
							.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					if (location != null) {
						latitude = location.getLatitude();
						longitude = location.getLongitude();
					} else {
						latitude = 0.0;
						longitude = 0.0;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return location;
	}

	/**
	 * 
	 * Method stop using GPS listener. Calling this function will stop GPS in
	 * application
	 * 
	 * @author: nhantd
	 * @return: void
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public void stopTrackerLocation() {
		if (locationManager != null) {
			locationManager.removeUpdates(TrackerLocationService.this);
		}
	}

	/**
	 * 
	 * Set min distance for updating location
	 * 
	 * @author: nhantd
	 * @return: void
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public void setMinDistanceUpdate(long minDistance) {
		min_distance_change_for_update = minDistance;
	}

	/**
	 * 
	 * Set min time for updating location
	 * 
	 * @author: nhantd
	 * @return: void
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public void setMinTimeUpdate(long minTime) {
		min_time_bw_update = minTime;
	}

	/**
	 * 
	 * Method to get latitude
	 * 
	 * @author: nhantd
	 * @return: double
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public double getLatitude() {
		if (location != null) {
			latitude = location.getLatitude();
		}
		return latitude;
	}

	/**
	 * 
	 * method to get longitude
	 * 
	 * @author: nhantd
	 * @return: double
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public double getLongitude() {
		if (location != null) {
			longitude = location.getLongitude();
		}
		return longitude;
	}

	/**
	 * 
	 * method to check GPS/network enabled
	 * 
	 * @author: nhantd
	 * @return: boolean
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 7, 2014
	 */
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Log.d("service", "on location change");
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		Bundle bundle = new Bundle();
		bundle.putDouble(KeyConstants.KEY_TRACKER_LOCATION_LAT, latitude);
		bundle.putDouble(KeyConstants.KEY_TRACKER_LOCATION_LON, longitude);
		Toast.makeText(getBaseContext(), "Lat : " + latitude + "; Lng : "
				+ longitude, Toast.LENGTH_SHORT).show();
	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 7, 2014
	 */
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 7, 2014
	 */
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 7, 2014
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 7, 2014
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		double latitude = getLatitude();
		double longitude = getLongitude();
		Toast.makeText(getBaseContext(), "Lat : " + latitude + "; Lng : "
				+ longitude, Toast.LENGTH_SHORT).show();
		return null;
	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 7, 2014
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d("service", "start command");
		getLocation();
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 7, 2014
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.d("service", "start oncreate");
	}
	

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 7, 2014
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
