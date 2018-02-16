/*
 * @ClassName: ActivityLogin.java
 * @Date: Apr 2, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.viettel.invoice.one.R;
import com.viettel.invoiceonemessage.dto.request.LoginMessageRequest;
import com.viettel.invoiceonemessage.dto.responses.LoginMessageResponses;
import com.viettel.lib.commonobject.ReceiverAction;
import com.viettel.lib.commonobject.SenderAction;
import com.viettel.lib.utils.KeyBoardUtils;
import com.viettel.lib.utils.PhysicalDeviceUtils;
import com.viettel.lib.utils.StringUtils;
import com.viettel.vpmt.template.GlobalApplication;
import com.viettel.vpmt.template.base.BaseActivityNoSliding;
import com.viettel.vpmt.template.common.ServerPath;
import com.viettel.vpmt.template.constant.ActionConstants;
import com.viettel.vpmt.template.presenter.UserPresenter;

/**
 * ActivityLogin
 * 
 * @author:vuonghv2
 * @version: 1.0
 * @since: Apr 2, 2014
 */

public class ActivityLogin extends BaseActivityNoSliding implements
		OnClickListener, OnTouchListener {

	// Define All control
	private Button btnLogin;
	private EditText txtUser;
	private EditText txtPass;

	/**
	 * On Create activity
	 * 
	 * @author:vuonghv2
	 * @since:Apr 2, 2014
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.layout_login);
		initLayout();

	}

	/**
	 * onResume
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: void
	 * @Since: Jun 11, 2014
	 * @throws:
	 */
	@Override
	protected void onResume() {
		super.onResume();
	}

	/**
	 * onClick
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: void
	 * @Since: Jun 11, 2014
	 * @throws:
	 */
	@Override
	public void onClick(View v) {
		String user = txtUser.getText().toString();
		String pass = txtPass.getText().toString();
		switch (v.getId()) {
		case R.id.btnLogin:
			checkValidation(user, pass);
			break;
		default:
			break;
		}
	}

	/**
	 * initLayout
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: void
	 * @Since: Jun 11, 2014
	 * @throws:
	 */
	private void initLayout() {
		// Get control on View
		btnLogin = (Button) findViewById(R.id.btnLogin);
		txtUser = (EditText) findViewById(R.id.txtUserName);
		txtPass = (EditText) findViewById(R.id.txtPassword);

		btnLogin.setOnClickListener(this);
		txtUser.requestFocus();
	}

	/**
	 * checkValidation
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: void
	 * @Since: Jun 11, 2014
	 * @throws:
	 */
	private void checkValidation(String username, String pass) {
		if (username.trim().length() == 0) {
			// If username is null
			txtUser.setError("Nhập tên đăng nhập");
			txtUser.requestFocus();
		} else if (pass.length() == 0) {
			// If password is null
			txtPass.setError("Nhập mật khẩu");
			txtPass.requestFocus();
		} else {
			pass = StringUtils.md5Encrypt(pass);
			sendRequestCheckLogin(username, pass,
					PhysicalDeviceUtils.getDeviceIMEI(this));
		}
	}

	/**
	 * sendRequestCheckLogin
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: void
	 * @Since: Jun 15, 2014
	 * @throws:
	 */
	private void sendRequestCheckLogin(String userName, String password,
			String imei) {
		// TODO Auto-generated method stub
		LoginMessageRequest loginMessageRequest = new LoginMessageRequest();
		loginMessageRequest.setUsername(userName);
		loginMessageRequest.setPassword(password);
		loginMessageRequest.setImei(imei);

		SenderAction senderAction = new SenderAction();
		senderAction.setAction(ActionConstants.ACTION_CHECK_LOGIN);
		senderAction.setMessRequest(loginMessageRequest);
		senderAction.setMethod(ServerPath.SERVER_PATH_OAUTH + "/checkLogin");
		senderAction.setMessResponses(LoginMessageResponses.class);
		senderAction.setSender(this);
		UserPresenter.getInstance().onSendViewToModel(senderAction);
	}

	/**
	 * onHandlePresenterToViewSuccess
	 * 
	 * @author:vuonghv2
	 * @since:Jun 11, 2014
	 */
	@Override
	public void onHandlePresenterToViewSuccess(ReceiverAction objectRecevier) {
		// Close dialog

		switch (objectRecevier.getSenderAction().action) {
		case ActionConstants.ACTION_CHECK_LOGIN:
			LoginMessageResponses loginMessageResponses = (LoginMessageResponses) objectRecevier
					.getBundle();
			GlobalApplication.getInstance().getProfile()
					.setMaThuNganVien(loginMessageResponses.getMaTNV());
			GlobalApplication.getInstance().getProfile()
					.setTenThuNganVien(loginMessageResponses.getTenTNV());

			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			break;
		default:
			super.onHandlePresenterToViewSuccess(objectRecevier);
			break;
		}
	}

	/**
	 * onHandlePresenterToViewError
	 * 
	 * @author:vuonghv2
	 * @since:Jun 11, 2014
	 */
	@Override
	public void onHandlePresenterToViewError(ReceiverAction objectRecevier) {

		switch (objectRecevier.getSenderAction().action) {
		case ActionConstants.ACTION_CHECK_LOGIN:
			GlobalApplication.getInstance().getProfile()
					.setMaThuNganVien("TNV-01");
			GlobalApplication.getInstance().getProfile()
					.setTenThuNganVien("HUYNH VAN VUONG");
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			break;
		default:
			super.onHandlePresenterToViewError(objectRecevier);
			break;
		}
	}

	/**
	 * onActionEventListener
	 * 
	 * @author:vuonghv2
	 * @since:Jun 11, 2014
	 */
	@Override
	public void onActionEventListener(int actionEvent, int actionType,
			Object data) {
		// TODO Auto-generated method stub
		switch (actionEvent) {

		default:
			break;
		}
		super.onActionEventListener(actionEvent, actionType, data);
	}

	/**
	 * onTouch
	 * 
	 * @author:vuonghv2
	 * @since:Jun 11, 2014
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		KeyBoardUtils.forceHideKeyboards(this);
		return false;
	}

}
