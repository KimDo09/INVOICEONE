/*
 * @ClassName: UserPresenter.java
 * @Date: Jan 7, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.viettel.lib.commonobject.SenderAction;
import com.viettel.lib.utils.KeyBoardUtils;
import com.viettel.lib.utils.LogUtils;
import com.viettel.vpmt.template.base.BaseActivity;
import com.viettel.vpmt.template.base.BaseFragment;
import com.viettel.vpmt.template.constant.ActivityConstants;
import com.viettel.vpmt.template.log.LogUtilsGeneral;
import com.viettel.vpmt.template.model.UserModel;

/**
 * Mo ta Types
 * 
 * @author: duchha
 * @version: 1.0
 * @since: Jan 7, 2014
 */

public class UserPresenter extends BasePresenter {

	public static UserPresenter instance; // Static instance su dung o tat ca
											// moi noi trong du an

	protected UserPresenter() {
	}

	/**
	 * 
	 * Ham lay UserPresenter de su dung
	 * 
	 * @author: nhantd
	 * @return: UserPresenter
	 * @Since: Jan 9, 2014
	 * @throws:
	 */
	public static UserPresenter getInstance() {
		if (instance == null) {
			instance = new UserPresenter();
		}
		return instance;
	}

	/**
	 * 
	 * chuyen yeu cau tu view xuong model
	 * 
	 * @author: duchha
	 * @return: void
	 * @Since: Jan 7, 2014
	 * @throws:
	 */
	@Override
	public void onSendViewToModel(SenderAction ojectSender) {
		if (ojectSender.isRequestToDb) {
			UserModel.getInstance().executeToDatabase(ojectSender);
		} else {
			UserModel.getInstance().excecute(ojectSender);
		}

	}

	/**
	 * 
	 * chuyen man hinh activity
	 * 
	 * @author: duchha
	 * @variables : objectSender chua doi tuong gui di flag xac dinh startScreen
	 *            hay startScreenForResult flagIntent xac dinh flag cua Intent
	 * @return: void
	 * @param: SenderAction objectSender
	 * @param: ActivityConstants activityMessage
	 * @param: int flagIntent| requestCode (neu la START_ACTIVITY_FOR_RESULT)
	 * @Since: Jan 7, 2014
	 * @throws:
	 */
	@Override
	public void onHandleSwitchActivity(SenderAction objectSender,
			ActivityConstants activityMessage, int flagIntent) {
		try {
			BaseActivity base = (BaseActivity) objectSender.sender;

			Intent intent;
			Bundle bundle = (Bundle) objectSender.bundle;

			intent = new Intent(base, (Class<?>) objectSender.startScreen);
			intent.putExtras(bundle);
			intent.addFlags(flagIntent);
			switch (activityMessage) {
			case START_ACTIVITY:
				base.startActivity(intent);
				break;
			case START_ACTIVITY_FOR_RESULT:
				base.startActivityForResult(intent, objectSender.requestCode);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			LogUtilsGeneral.getInstance().d("TAG",
					LogUtilsGeneral.getContentError(e));
		}
	}

	/**
	 * 
	 * chuyen man hinh Fragment
	 * 
	 * @author: duchha
	 * @return: void
	 * @Since: Jan 7, 2014
	 * @throws:
	 */
	public void onHandleSwitchFragment(SenderAction objectSender, int idScreen,
			boolean isAddToBackTask) {
		BaseActivity base = null;
		if (objectSender.sender instanceof BaseActivity) {
			base = (BaseActivity) objectSender.sender;
		} else if (objectSender.sender instanceof BaseFragment) {
			base = (BaseActivity) ((BaseFragment) objectSender.sender)
					.getActivity();
		}
		if (base != null) {
			KeyBoardUtils.forceHideKeyboard(base);
		}

		FragmentTransaction ft = base.getSupportFragmentManager()
				.beginTransaction();
		removeAllInBackStack(base.getSupportFragmentManager());
		Fragment reviewFrag = (Fragment) base.getSupportFragmentManager()
				.findFragmentByTag((objectSender.startScreen).toString());

		if (reviewFrag != null) {
			ft.remove(reviewFrag);
		}
		((Fragment) objectSender.startScreen)
				.setArguments((Bundle) objectSender.bundle);
		ft.replace(idScreen, (Fragment) objectSender.startScreen,
				(objectSender.startScreen).toString());

		if (isAddToBackTask) {
			ft.addToBackStack((objectSender.startScreen).toString());
		}
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
		LogUtils.i("Fragment", "Num fragment in stack : "
				+ base.getSupportFragmentManager().getBackStackEntryCount());

	}

}
