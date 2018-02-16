/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.base;

import java.io.File;
import java.io.IOException;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.LayoutParams;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.viettel.invoice.one.R;
import com.viettel.lib.commonobject.ReceiverAction;
import com.viettel.lib.commonobject.SenderAction;
import com.viettel.lib.utils.LogUtils;
import com.viettel.lib.utils.PhysicalDeviceUtils;
import com.viettel.lib.utils.StringUtils;
import com.viettel.vpmt.template.GlobalApplication;
import com.viettel.vpmt.template.constant.ActionConstants;
import com.viettel.vpmt.template.constant.ActivityConstants;
import com.viettel.vpmt.template.constant.KeyConstants;
import com.viettel.vpmt.template.log.ConfigLog;
import com.viettel.vpmt.template.presenter.UserPresenter;
import com.viettel.vpmt.template.view.activity.TestActivity;
import com.viettel.vpmt.template.view.fragment.SlidingMenuFragment;

public class BaseActivity extends SlidingFragmentActivity implements
		BaseFunction {
	public SlidingMenu slidingMenu;
	// layout root_parent
	private LinearLayout rootView;
	// hien thi hinh anh

	// dialog hien thi khi request
	public ProgressDialog progressDlg;
	// broadcast receiver, nhan broadcast
	BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			int action = intent.getExtras().getInt(
					KeyConstants.KEY_BROADCAST_ACTION);
			int hasCode = intent.getExtras().getInt(
					KeyConstants.KEY_BROADCAST_HASHCODE);
			if (hasCode != BaseActivity.this.hashCode()) {
				receiveBroadcast(action, intent.getExtras());
			}
		}
	};
	private int mTitleRes;
	// TextView hien thi title
	TextView tvTitile;

	public BaseActivity() {
		this.mTitleRes = R.string.app_name;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBehindContentView(R.layout.menu_frame);
		getSupportActionBar().setIcon(R.drawable.ic_launcher_actionbar);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		LayoutInflater li = LayoutInflater.from(this);
		View customView = li.inflate(R.layout.layout_upro_header, null);
		LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		getSupportActionBar().setCustomView(customView, layout);

		tvTitile = (TextView) findViewById(R.id.tvTitile);
		tvTitile.setWidth(PhysicalDeviceUtils.getWidth(this) / 2);
		tvTitile.setSelected(true);
		tvTitile.setText(mTitleRes);
		if (PhysicalDeviceUtils.getWidth(this) == 600) {
			tvTitile.setTextSize(24);
		}
		slidingMenu = getSlidingMenu();
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		try {
			IntentFilter filter = new IntentFilter(
					ActionConstants.ACTION_BROADCAST);
			registerReceiver(receiver, filter);
		} catch (Exception e) {
		}
		SlidingMenuFragment menu1 = new SlidingMenuFragment();
		setMenuFirstByFragment(menu1, SlidingMenu.TOUCHMODE_MARGIN);
		// vuonghv2 start service download
		startServiceSendLog();
		getSupportActionBar().setDisplayShowHomeEnabled(true);
	}

	public void onCreate(Bundle savedInstanceState, boolean isBroadcast) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_Sherlock_Light);
		setTitle(mTitleRes);
		if (isBroadcast) {
			try {
				IntentFilter filter = new IntentFilter(
						ActionConstants.ACTION_BROADCAST);
				registerReceiver(receiver, filter);
			} catch (Exception e) {
			}
		}

		getSupportActionBar().setDisplayShowHomeEnabled(true);

	}

	/**
	 * startServiceSendLog
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: void
	 * @Since: May 13, 2014
	 * @throws:
	 */
	private void startServiceSendLog() {
		Intent downloadIntent = new Intent(ConfigLog.ACTION_SERVICE);
		startService(downloadIntent);

		try {
			mkdirDirectory();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * mkdirDirectory
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: void
	 * @Since: May 14, 2014
	 * @throws:
	 */
	public static void mkdirDirectory() throws IOException {

		File file = new File(ConfigLog.FILE_ROOT);
		if (!file.exists() || !file.isDirectory())
			file.mkdir();
	}

	/**
	 * 
	 * cau hinh actionbarsherlock cho layout activity Mac dinh actionbar hien
	 * thi icon_home ko hien thi icon_homeAsUp
	 * 
	 * @author: duchha
	 * @return: void
	 * @Since: Feb 12, 2014
	 * @throws:
	 */
	protected void setConfigLayoutActivity(boolean showHomeAsUp,
			boolean showIconApp, int bg) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
		getSupportActionBar().setDisplayShowHomeEnabled(showIconApp);
		if (bg != -1) {
			getSupportActionBar().setBackgroundDrawable(
					getResources().getDrawable(bg));
		}
		getSupportActionBar().setTitle(
				StringUtils.getString(this, R.string.app));
	}

	protected void setTitleActionBar(String title) {
		// getSupportActionBar().setTitle(title);
		tvTitile.setText(title);
	}

	/**
	 * 
	 * Hien thi header customer
	 * 
	 * @author: duchha
	 * @return: void
	 * @Since: Feb 12, 2014
	 * @throws:
	 */
	protected void setCustomerHeader(View view) {
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(view);

	}

	/**
	 * 
	 * khong cho actionbar truot theo
	 * 
	 * @author: duchha
	 * @return: void
	 * @Since: Feb 12, 2014
	 * @throws:
	 */
	protected void disEnabledSlidingActionBar() {
		setSlidingActionBarEnabled(false);
	}

	/**
	 * 
	 * thiet lap them menu thu nhat thuc hien by Fragmet
	 * 
	 * @author: duchha
	 * @return: void
	 * @Since: Feb 12, 2014
	 * @throws:
	 */
	protected void setMenuFirstByFragment(BaseFragment fragment,
			int setTouchMode) {
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenu.setFadeDegree(0.0f);
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setBehindScrollScale(0);
		slidingMenu.setTouchModeAbove(setTouchMode);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, fragment).commit();
	}

	/**
	 * 
	 * thiet lap them menu thu nhat thuc hien add view
	 * 
	 * @author: duchha
	 * @return: void
	 * @Since: Feb 12, 2014
	 * @throws:
	 */
	protected void setMenuFirst(View v, int setTouchMode) {
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenu.setFadeDegree(0.0f);
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setBehindScrollScale(0);
		slidingMenu.setTouchModeAbove(setTouchMode);
		setBehindContentView(v);
	}

	/**
	 * 
	 * thiet lap them menu thu 2
	 * 
	 * @author: duchha
	 * @return: void
	 * @Since: Feb 12, 2014
	 * @throws:
	 */
	protected void setMenuSecondary(View v) {
		slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
		slidingMenu.setSecondaryMenu(R.layout.menu_frame_two);
		slidingMenu.setSecondaryShadowDrawable(R.drawable.shadowright);
		slidingMenu.setSecondaryMenu(v);
	}

	/**
	 * 
	 * thiet lap them menu thu 2 bang Fragment
	 * 
	 * @author: duchha
	 * @return: void
	 * @Since: Feb 12, 2014
	 * @throws:
	 */
	protected void setMenuSecondaryByFragment(BaseFragment fragment) {
		slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
		slidingMenu.setSecondaryMenu(R.layout.menu_frame_two);
		slidingMenu.setSecondaryShadowDrawable(R.drawable.shadowright);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame_two, fragment).commit();
	}

	/**
	 * 
	 * setTouchModeMargin
	 * 
	 * @author: duchha
	 * @return: void
	 * @Since: Feb 12, 2014
	 * @throws:
	 */
	protected void setTouchModeMargin() {
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	}

	/**
	 * 
	 * setTouchModeFullScreen
	 * 
	 * @author: duchha
	 * @return: void
	 * @Since: Feb 12, 2014
	 * @throws:
	 */
	protected void setTouchModeFullScreen() {
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	/**
	 * noi dung sua
	 * 
	 * @author:duchha
	 * @since:Feb 12, 2014
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * noi dung sua
	 * 
	 * @author:duchha
	 * @since:Feb 12, 2014
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getSupportMenuInflater().inflate(R.menu.activity_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(R.layout.layout_activity_base);

		rootView = (LinearLayout) findViewById(R.id.llScreenView);

		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(layoutResID, rootView);
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
	}

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
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * noi dung sua
	 * 
	 * @author:duchha
	 * @since:Feb 11, 2014
	 */
	@Override
	public void onHandlePresenterToViewSuccess(ReceiverAction objectRecevier) {
		// TODO Auto-generated method stub

	}

	/**
	 * noi dung sua
	 * 
	 * @author:duchha
	 * @since:Feb 11, 2014
	 */
	@Override
	public void onHandlePresenterToViewError(ReceiverAction objectRecevier) {
		// TODO Auto-generated method stub

	}

	/**
	 * noi dung sua
	 * 
	 * @author:duchha
	 * @since:Feb 11, 2014
	 */
	@Override
	public void onEvent(int eventType, View control, Object data) {
		// TODO Auto-generated method stub

	}

	/**
	 * xu ly su kien khi bam nut back
	 * 
	 * @author:duchha
	 * @since:Feb 12, 2014
	 */
	@Override
	public void onBackPressed() {
		if (this instanceof TestActivity) {
			GlobalApplication.showDialogConfirmExitApp(this);
		} else {
			super.onBackPressed();
		}
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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		if (dialog == progressDlg) {
			// thuc hien calcel cac request dang xu ly
		}
	}

	/** 
	  * noi dung sua
	  * @author:vuonghv2
	  *  @since:Jun 11, 2014
	  */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Start activity
	 * 
	 * @author: vuongh2
	 * @param:
	 * @return: void
	 * @Since: Apr 15, 2014
	 * @throws:
	 */
	public void startScreen(Object objActivity, Bundle bunlde, boolean finish) {
		SenderAction objectSender = new SenderAction();
		// objectSender.action = ActionConstants.ACTION_GO_TO_GAME_SCREEN;
		objectSender.sender = this;
		objectSender.bundle = bunlde;
		objectSender.startScreen = objActivity;
		objectSender.isfinish = finish;
		UserPresenter.getInstance().onHandleSwitchActivity(objectSender,
				ActivityConstants.START_ACTIVITY,
				Intent.FLAG_ACTIVITY_CLEAR_TOP);
	}
}
