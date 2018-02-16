package com.viettel.vpmt.template.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.viettel.lib.commonobject.ReceiverAction;
import com.viettel.lib.utils.LogUtils;
import com.viettel.lib.utils.StringUtils;
import com.viettel.invoice.one.R;
import com.viettel.vpmt.template.constant.ActionConstants;
import com.viettel.vpmt.template.constant.KeyConstants;

public class BaseFragment extends Fragment implements BaseFunction {

	BaseActivity activity;
	public boolean isFinished = false; // Check fragment is finished or destroy
	private LinearLayout viewRoot; // view root
	// dialog hien thi khi request
	public ProgressDialog progressDlg;
	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int action = intent.getExtras().getInt(
					KeyConstants.KEY_BROADCAST_ACTION);
			int hasCode = intent.getExtras().getInt(
					KeyConstants.KEY_BROADCAST_HASHCODE);
			if (hasCode != BaseFragment.this.hashCode()) {
				receiveBroadcast(action, intent.getExtras());
			}
		}
	};

	/**
	 * 
	 * Send broadcast toi cac fragment khac
	 * 
	 * @author: Duchha
	 * @return: void
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public void sendBroadcast(int action, Bundle bundle) {
		activity.sendBroadcast(action, bundle);
	}

	/**
	 * 
	 * Nhan broadcast tu cac view fragment khac
	 * 
	 * @author: Duchha
	 * @return: void
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public void receiveBroadcast(int action, Bundle extra) {
		switch (action) {
		case ActionConstants.ACTION_LOGOUT:
			// code here
			break;

		default:
			break;
		}
	}

	/**
	 * 
	 * Remove fragment from backstack
	 * 
	 * @author: nhantd
	 * @return: void
	 * @Since: Feb 7, 2014
	 * @throws:
	 */
	public void removeFragmentFromBackStack() {
		FragmentManager fm = this.getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		fm.popBackStack(this.getClass().getName(),
				FragmentManager.POP_BACK_STACK_INCLUSIVE);
		ft.commit();
	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 7, 2014
	 */
	@Override
	public void onAttach(Activity activity) {
		this.activity = (BaseActivity) activity;
		super.onAttach(activity);
	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 7, 2014
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 7, 2014
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		try {
			IntentFilter filter = new IntentFilter(
					ActionConstants.ACTION_BROADCAST);
			activity.registerReceiver(receiver, filter);
		} catch (Exception e) {
		}
	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 7, 2014
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.layout_fragment_base, null, false);
		viewRoot = (LinearLayout) view.findViewById(R.id.llfragmentbase);
		viewRoot.addView(container);
		isFinished = false;
		return view;
	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 7, 2014
	 */
	@Override
	public void onDestroy() {
		isFinished = true;
		try {
			getActivity().unregisterReceiver(receiver);
		} catch (Exception e) {
		}
		super.onDestroy();
	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 7, 2014
	 */
	@Override
	public void onDestroyView() {
		try {
			getActivity().unregisterReceiver(receiver);
		} catch (Exception e) {
		}
		super.onDestroyView();
	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 7, 2014
	 */
	@Override
	public void onResume() {
		isFinished = false;
		super.onResume();
	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 7, 2014
	 */
	@Override
	public void onHandlePresenterToViewSuccess(ReceiverAction objectRecevier) {
		// TODO Auto-generated method stub

	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 7, 2014
	 */
	@Override
	public void onHandlePresenterToViewError(ReceiverAction objectRecevier) {
		// TODO Auto-generated method stub

	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Feb 7, 2014
	 */
	@Override
	public void onClick(View v) {
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
		progressDlg = ProgressDialog.show(this.getActivity(), StringUtils.EMPTY_STRING,
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

}
