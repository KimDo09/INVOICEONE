/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.base;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.viettel.lib.commonobject.ReceiverAction;
import com.viettel.lib.commonobject.SenderAction;
import com.viettel.lib.utils.LogUtils;
import com.viettel.lib.utils.StringUtils;
import com.viettel.vpmt.template.constant.ActionConstants;
import com.viettel.vpmt.template.constant.ActivityConstants;
import com.viettel.vpmt.template.constant.KeyConstants;
import com.viettel.vpmt.template.presenter.UserPresenter;

public class BaseActivityNoSliding extends Activity implements BaseFunction {

	// dialog hien thi khi request
	public ProgressDialog progressDlg;
	private boolean isActive = false;
	BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			int action = intent.getExtras().getInt(
					KeyConstants.KEY_BROADCAST_ACTION);
			int hasCode = intent.getExtras().getInt(
					KeyConstants.KEY_BROADCAST_HASHCODE);
			if (hasCode != BaseActivityNoSliding.this.hashCode()) {
				receiveBroadcast(action, intent.getExtras());
			}
		}
	};
	private int mTitleRes;

	public void onCreate(Bundle savedInstanceState, boolean isBroadcast) {
		super.onCreate(savedInstanceState);
		setTitle(mTitleRes);
		if (isBroadcast) {
			try {
				IntentFilter filter = new IntentFilter(
						ActionConstants.ACTION_BROADCAST);
				registerReceiver(receiver, filter);
			} catch (Exception e) {
			}
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			IntentFilter filter = new IntentFilter(
					ActionConstants.ACTION_BROADCAST);
			registerReceiver(receiver, filter);
		} catch (Exception e) {
		}
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
	}

	NotificationManager myNotificationManager;
	int numMessagesOne = 0;

	/**
	 * 
	 * nhan broadcast messenger
	 * 
	 * @author: duchha
	 * @return: void
	 * @Since: Jan 7, 2014
	 * @throws:
	 */
	public void receiveBroadcast(int action, Bundle bundle) {
		switch (action) {
		case ActionConstants.ACTION_LOGOUT:
			// code here
			break;
		case ActionConstants.ACTION_TRACKER_LOCATION:
			// get latitude and longitude
			double lat = bundle
					.getDouble(KeyConstants.KEY_TRACKER_LOCATION_LAT);
			double lng = bundle
					.getDouble(KeyConstants.KEY_TRACKER_LOCATION_LON);
			Toast.makeText(this, "Lat : " + lat + ";Lng : " + lng,
					Toast.LENGTH_SHORT).show();
			break;
		case ActionConstants.EXIT_APP:
			// Thiet lap lại token
			this.finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * sendBroadcast
	 * 
	 * @author: duchha
	 * @return: void
	 * @Since: Jan 7, 2014
	 * @throws:
	 */
	public void sendBroadcast(int action, Bundle bundle) {
		Intent intent = new Intent(ActionConstants.ACTION_BROADCAST);
		bundle.putInt(KeyConstants.KEY_BROADCAST_ACTION, action);
		bundle.putInt(KeyConstants.KEY_BROADCAST_HASHCODE, intent.getClass()
				.hashCode());
		intent.putExtras(bundle);
		sendBroadcast(intent, ActionConstants.PERMISSION_BROADCAST);
	}

	/**
	 * noi dung sua
	 * 
	 * @author:duchha
	 * @since:Jan 7, 2014
	 */
	@Override
	protected void onDestroy() {
		try {
			unregisterReceiver(receiver);
		} catch (Exception e) {
		}
		System.gc();
		System.runFinalization();
		super.onDestroy();
	}

	/**
	 * noi dung sua
	 * 
	 * @author:duchha
	 * @since:Feb 11, 2014
	 */
	@Override
	public void onClick(View v) {

	}

	/**
	 * noi dung sua
	 * 
	 * @author:duchha
	 * @since:Feb 11, 2014
	 */
	@Override
	public void onHandlePresenterToViewSuccess(ReceiverAction objectRecevier) {

		SenderAction sender = objectRecevier.getSenderAction();
		switch (sender.getAction()) {
		default:
			break;
		}

	}

	/**
	 * noi dung sua
	 * 
	 * @author:duchha
	 * @since:Feb 11, 2014
	 */
	@Override
	public void onHandlePresenterToViewError(ReceiverAction objectRecevier) {
		SenderAction sender = objectRecevier.getSenderAction();
		switch (sender.getAction()) {

		default:
			break;
		}

	}

	/**
	 * noi dung sua
	 * 
	 * @author:duchha
	 * @since:Feb 11, 2014
	 */
	@Override
	public void onEvent(int eventType, View control, Object data) {

	}

	/**
	 * xu ly su kien khi bam nut back
	 * 
	 * @author:duchha
	 * @since:Feb 12, 2014
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		/*
		 * if (this instanceof MainActivity) {
		 * GlobalApplication.showDialogConfirmExitApp(this); } else {
		 * super.onBackPressed(); }
		 */
	}

	/**
	 * noi dung sua
	 * 
	 * @author:duchha
	 * @since:Feb 12, 2014
	 */
	@Override
	public void onActionEventListener(int actionEvent, int actionType,
			Object data) {

	}

	/**
	 * 
	 * show progress dialog
	 * 
	 * @author: AnhND
	 * @param content
	 * @param cancleable
	 * @return: void
	 * @throws:
	 */
	public void showProgressDialog(String content, boolean cancleable) {
		if (progressDlg != null && progressDlg.isShowing()) {
			closeProgressDialog();
		}
		progressDlg = ProgressDialog.show(this, StringUtils.EMPTY_STRING,
				content, true, true);
		progressDlg.setCancelable(cancleable);
		progressDlg.setCanceledOnTouchOutside(false);
		progressDlg.setOnCancelListener(this);
	}

	public void closeProgressDialog() {
		if (progressDlg != null) {
			try {
				progressDlg.dismiss();
				progressDlg = null;
			} catch (Exception e) {
				LogUtils.i("Exception", e.toString());
			}
		}
	}

	/**
	 * thuc hien calcel cac request dang xu ly
	 * 
	 * @author:duchha
	 * @since:Feb 17, 2014
	 */
	@Override
	public void onCancel(DialogInterface dialog) {
		if (dialog == progressDlg) {
			// thuc hien calcel cac request dang xu ly
		}
	}

	/**
	 * noi dung sua
	 * 
	 * @author:Administrator
	 * @since:Apr 13, 2014
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive
	 *            the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	protected void onResume() {
		setActive(true);
		super.onResume();
	}

	/**
	 * noi dung sua
	 * 
	 * @author:duchha
	 * @since:May 16, 2014
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		setActive(false);
		super.onPause();
	}
}
