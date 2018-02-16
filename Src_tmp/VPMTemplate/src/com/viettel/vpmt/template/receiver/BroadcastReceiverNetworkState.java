/*
 * @ClassName: BroadcastReceiverNetworkState.java
 * @Date: Feb 8, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.receiver;

import com.viettel.vpmt.template.GlobalApplication;
import com.viettel.invoice.one.R;
import com.viettel.vpmt.template.constant.KeyConstants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Mo ta Types
 * 
 * @author: nhantd
 * @version: 1.0
 * @since: Feb 8, 2014
 */

public class BroadcastReceiverNetworkState extends BroadcastReceiver {

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 8, 2014
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// Process when network state change
		String status = getConnectivityStatusString(context);
		Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
	}

	private int getConnectivityStatus(Context context) {
		ConnectivityManager cnManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cnManager.getActiveNetworkInfo();
		if (activeNetwork != null) {
			if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
				if (activeNetwork.isAvailable() && activeNetwork.isConnected()) {
					return KeyConstants.NETWORK_STATE_WIFI;
				}
			} else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
				if (activeNetwork.isAvailable() && activeNetwork.isConnected()) {
					return KeyConstants.NETWORK_STATE_MOBILE; 
				}
			} else if (activeNetwork.getType() == ConnectivityManager.TYPE_ETHERNET) {
				if (activeNetwork.isAvailable() && activeNetwork.isConnected()) {
					return KeyConstants.NETWORK_STATE_ETHERNET;
				}
			} else if (activeNetwork.getType() == ConnectivityManager.TYPE_BLUETOOTH) {
				if (activeNetwork.isAvailable() && activeNetwork.isConnected()) {
					return KeyConstants.NETWORK_STATE_BLUETOOTH;
				}
			}
		}
		return KeyConstants.NETWORK_STATE_NOT_CONNECT;
	}

	private String getConnectivityStatusString(Context context) {
		int conn = getConnectivityStatus(context);
		String status = null;
		if (conn == KeyConstants.NETWORK_STATE_WIFI) {
			status = GlobalApplication.getInstance().getApplicationContext()
					.getString(R.string.network_state_wifi);
		} else if (conn == KeyConstants.NETWORK_STATE_MOBILE) {
			status = GlobalApplication.getInstance().getApplicationContext()
					.getString(R.string.network_state_mobile);
		} else if (conn == KeyConstants.NETWORK_STATE_ETHERNET) {
			status = GlobalApplication.getInstance().getApplicationContext()
					.getString(R.string.network_state_ethernet);
		} else if (conn == KeyConstants.NETWORK_STATE_BLUETOOTH) {
			status = GlobalApplication.getInstance().getApplicationContext()
					.getString(R.string.network_state_bluetooth);
		} else if (conn == KeyConstants.NETWORK_STATE_NOT_CONNECT) {
			status = GlobalApplication.getInstance().getApplicationContext()
					.getString(R.string.network_state_not_connect);
		}
		return status;
	}
}
