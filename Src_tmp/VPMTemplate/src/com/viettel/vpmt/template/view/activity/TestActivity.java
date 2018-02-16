/*
 * @ClassName: TestActivity.java
 * @Date: Feb 11, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.invoice.one.R;
import com.viettel.lib.commonobject.ReceiverAction;
import com.viettel.lib.commonobject.SenderAction;
import com.viettel.lib.utils.StringUtils;
import com.viettel.lib.utils.ToastMessageUtils;
import com.viettel.vpmt.template.base.BaseActivity;
import com.viettel.vpmt.template.constant.ActionConstants;
import com.viettel.vpmt.template.constant.ActivityConstants;
import com.viettel.vpmt.template.presenter.UserPresenter;
import com.viettel.vpmt.template.service.TrackerLocationService;
import com.viettel.vpmt.template.view.activity.demo.OrmSqliteActivity;

/**
 * Mo ta Types
 * 
 * @author: duchha
 * @version: 1.0
 * @since: Feb 11, 2014
 */

public class TestActivity extends BaseActivity {

	ImageView ivAvatar;
	TextView tvDis;
	Button btnAction1;
	Button btnAction2;
	Button btnAction3;

	Button btnOrmSql;

	ListView lvDs;
	SenderAction objectSender;
	String imageUrl = "http://3.bp.blogspot.com/-SX3PTPY_Rog/UrvV4dWMypI/AAAAAAAAJGw/sq_Grp7gymo/s1600/anh-dep-valentine-2014-1.jpg";

	/**
	 * noi dung sua
	 * 
	 * @author:duchha
	 * @since:Feb 11, 2014
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_test_activity);

		setConfigLayoutActivity(true, true, R.drawable.background_color);
		setTitleActionBar("Test Activity");

		disEnabledSlidingActionBar();
		initLayout();
	}

	void initLayout() {
		btnAction1 = (Button) this.findViewById(R.id.btnShowDialog);
		btnAction1.setOnClickListener(this);
		btnAction2 = (Button) this.findViewById(R.id.btnShowDialogWarning);
		btnAction2.setOnClickListener(this);
		btnAction3 = (Button) this.findViewById(R.id.btnShowDialogConfirm);
		btnAction3.setOnClickListener(this);

		btnOrmSql = (Button) this.findViewById(R.id.btnOrmSql);
		btnOrmSql.setOnClickListener(this);

	}

	/**
	 * Suwr lys su kien onClick
	 * 
	 * @author:duchha
	 * @since:Feb 12, 2014
	 */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnShowDialog:
			ToastMessageUtils.showDialog(-1, "", "Hi, Show Dialog", "Ok",
					ActionConstants.ACTION_OK, ActionConstants.ACTION_CANCEL,
					null, true, true, this);
			break;
		case R.id.btnShowDialogWarning:
			ToastMessageUtils.showDialogWarning(this,
					R.drawable.avatar_standard,
					StringUtils.getString(this, R.string.warning),
					"Hi, Show dialog warning", "ok");
			break;
		case R.id.btnShowDialogConfirm:
			ToastMessageUtils.showDialogConfirm(R.drawable.avatar_standard,
					StringUtils.getString(this, R.string.warning),
					"Hi, Show dialog Confirm", "ok", ActionConstants.ACTION_OK,
					"Cancel", ActionConstants.ACTION_CANCEL, null, true, false,
					this);
			break;

		case R.id.btnOrmSql:
			goToOrmSqlDemo();
			break;

		default:
			break;
		}
		super.onClick(v);
	}

	/**
	 * OrmSql Demo
	 * 
	 * @author: Thuantp
	 * @param:
	 * @return: void
	 * @Since: Mar 3, 2014
	 * @throws:
	 */
	public void goToOrmSqlDemo() {
		objectSender = new SenderAction();
		objectSender.sender = this;
		objectSender.bundle = new Bundle();
		objectSender.startScreen = OrmSqliteActivity.class;
		UserPresenter.getInstance().onHandleSwitchActivity(objectSender,
				ActivityConstants.START_ACTIVITY,
				Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
		switch (actionEvent) {
		case ActionConstants.ACTION_OK:
			ToastMessageUtils.showToastShort(this, "Ban Chon ok");
			break;

		case ActionConstants.ACTION_CANCEL:
			ToastMessageUtils.showToastShort(this, "Ban Chon Cancel");
			break;

		case ActionConstants.ACTION_MULTI_CHOICE:
			ToastMessageUtils.showToastShort(this, "ACTION_MULTI_CHOICE");
			break;
		default:
			break;
		}
		super.onActionEventListener(actionEvent, actionType, data);
	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 17, 2014
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 17, 2014
	 */
	@Override
	protected void onDestroy() {
		stopService(new Intent(this, TrackerLocationService.class));
		super.onDestroy();
	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 18, 2014
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		stopService(new Intent(this, TrackerLocationService.class));
		super.onPause();
	}

	/**
	 * noi dung sua
	 * 
	 * @author:duchha
	 * @since:Feb 19, 2014
	 */
	@Override
	public void onHandlePresenterToViewError(ReceiverAction objectRecevier) {
		// TODO Auto-generated method stub
		switch (objectRecevier.getSenderAction().action) {

		default:
			super.onHandlePresenterToViewError(objectRecevier);
			break;
		}
	}

	/**
	 * noi dung sua
	 * 
	 * @author:duchha
	 * @since:Feb 19, 2014
	 */
	@Override
	public void onHandlePresenterToViewSuccess(ReceiverAction objectRecevier) {
		switch (objectRecevier.getSenderAction().action) {

		default:
			super.onHandlePresenterToViewSuccess(objectRecevier);
			break;
		}
	}

	/**
	 * noi dung sua
	 * 
	 * @author:duchha
	 * @since:Feb 20, 2014
	 */
	@Override
	public void onEvent(int eventType, View control, Object data) {
		switch (eventType) {

		default:
			break;
		}
		super.onEvent(eventType, control, data);
	}

}
