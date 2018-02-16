/*
 * @ClassName: BasePresenter.java
 * @Date: Feb 12, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.presenter;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.viettel.lib.commonobject.ReceiverAction;
import com.viettel.lib.commonobject.SenderAction;
import com.viettel.lib.eventlistener.ReceiverActionListener;
import com.viettel.vpmt.template.GlobalApplication;
import com.viettel.vpmt.template.base.BaseActivity;
import com.viettel.vpmt.template.base.BaseActivityNoSliding;
import com.viettel.vpmt.template.base.BaseFragment;
import com.viettel.vpmt.template.constant.ActivityConstants;
import com.viettel.vpmt.template.constant.Constants;

/**
 * Mo ta Types
 * 
 * @author: duchha
 * @version: 1.0
 * @since: Feb 12, 2014
 */

public abstract class BasePresenter {
	abstract public void onSendViewToModel(SenderAction ojectSender);

	abstract public void onHandleSwitchActivity(SenderAction objectSender,
			ActivityConstants activityMessage, int flagIntent);

	abstract public void onHandleSwitchFragment(SenderAction objectSender,
			int idScreen, boolean isAddToBackTask);

	/**
	 * 
	 * Chuyen activity voi doi so truyen vao
	 * 
	 * @author: duchha
	 * @param:
	 * @return: void
	 * @Since: Mar 21, 2014
	 * @throws:
	 */
	public void onHandleSwitchActivity(BaseActivity context,
			Class<?> startScreen, boolean isFinishActivity) {
		Intent intent = new Intent(context, startScreen);
		context.startActivity(intent);
		if (isFinishActivity) {
			context.finish();
		}
	}

	/**
	 * 
	 * chuyen ket qua thanh cong tra ve tu Model ve View
	 * 
	 * @author: duchha
	 * @return: void
	 * @Since: Jan 7, 2014
	 * @throws:
	 */
	public void onReceiverModelToViewSuccess(final ReceiverAction objectReceiver) {
		SenderAction senderAction = objectReceiver.getSenderAction();
		if (senderAction.sender != null) {
			if (senderAction.sender instanceof BaseActivity) {
				processResponseActivity(objectReceiver, Constants.SUCCESS);
			} else if (senderAction.sender instanceof BaseActivityNoSliding) {
				processResponseActivity(objectReceiver, Constants.SUCCESS);
			} else if (senderAction.sender instanceof BaseFragment) {
				processResponseFragment(objectReceiver, Constants.SUCCESS);
			} else if (senderAction.sender instanceof BroadcastReceiver) {
				processResponseBroadcast(objectReceiver, Constants.SUCCESS);
			} else if (senderAction.sender instanceof Service) {
				processResponseBroadcast(objectReceiver, Constants.SUCCESS);
			}
		}
	}

	/**
	 * 
	 * chuyen ket qua that bai tra ve tu Model ve View
	 * 
	 * @author: nhantd
	 * @return: void
	 * @Since: Jan 9, 2014
	 * @throws:
	 */
	public void onReceiverModelToViewError(final ReceiverAction objectReceiver) {
		SenderAction senderAction = (SenderAction) objectReceiver
				.getSenderAction();
		if (senderAction.sender != null) {
			if (senderAction.sender instanceof BaseActivity) {
				processResponseActivity(objectReceiver, Constants.ERROR);
			} else if (senderAction.sender instanceof BaseActivityNoSliding) {
				processResponseActivity(objectReceiver, Constants.ERROR);
			} else if (senderAction.sender instanceof BaseFragment) {
				processResponseFragment(objectReceiver, Constants.ERROR);
			}
		}
	}

	/**
	 * 
	 * Ham xu ly login
	 * 
	 * @author: nhantd
	 * @return: void
	 * @Since: Jan 10, 2014
	 * @throws:
	 */
	private void processResponseActivity(final ReceiverAction receiverObject,
			final int flagerror) {
		if (receiverObject.senderAction.sender instanceof BaseActivity) {
			final BaseActivity sender = (BaseActivity) receiverObject.senderAction.sender;
			if (sender.isFinishing()) {
				return;
			}
			sender.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (flagerror == 0) {
						sender.onHandlePresenterToViewError(receiverObject);
					} else {
						sender.onHandlePresenterToViewSuccess(receiverObject);
					}
				}
			});
		} else if (receiverObject.senderAction.sender instanceof BaseActivityNoSliding) {
			final BaseActivityNoSliding sender = (BaseActivityNoSliding) receiverObject.senderAction.sender;
			if (sender.isFinishing()) {
				return;
			}
			sender.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (flagerror == 0) {
						sender.onHandlePresenterToViewError(receiverObject);
					} else {
						sender.onHandlePresenterToViewSuccess(receiverObject);
					}
				}
			});
		}
	}

	/**
	 * @author:Quyl
	 * @since:Feb 20, 2014 Description: Change from BaseFragmentActivity to
	 *            BaseFragment
	 */
	private void processResponseFragment(final ReceiverAction receiverObject,
			final int flagerror) {

		final BaseFragment sender = (BaseFragment) receiverObject.senderAction.sender;
		if (sender.getActivity().isFinishing()) {
			return;
		}
		sender.getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (flagerror == 0) {
					sender.onHandlePresenterToViewError(receiverObject);
				} else {
					sender.onHandlePresenterToViewSuccess(receiverObject);
				}
			}
		});
	}

	/**
	 * @author:Quyl
	 * @since:Feb 20, 2014 Description: Change from BaseFragmentActivity to
	 *            BaseFragment
	 */
	private void processResponseBroadcast(final ReceiverAction receiverObject,
			final int flagerror) {

		if (receiverObject.senderAction.sender instanceof ReceiverActionListener) {
			final ReceiverActionListener sender = (ReceiverActionListener) receiverObject.senderAction.sender;
			if (flagerror == 0) {
				sender.onHandlePresenterToViewError(receiverObject);
			} else {
				sender.onHandlePresenterToViewSuccess(receiverObject);
			}
			return;
		}
	}

	/**
	 * remove all fragment in back stack Muc dich: cac trang o menu chinh khong
	 * can luu trong stack khi chuyen cac trang trong menu.
	 */
	public void removeAllInBackStack(FragmentManager fm) {
		for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
			fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
		GlobalApplication.getInstance().popAllTag();
	}
}
