/*
 * @ClassName: OrmSqliteFragment.java
 * @Date: Feb 19, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.view.fragment.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.viettel.invoice.one.R;
import com.viettel.lib.commonobject.SenderAction;
import com.viettel.vpmt.template.base.BaseFragment;
import com.viettel.vpmt.template.presenter.UserPresenter;

/**
 * Mo ta Types
 * 
 * @author: thuan.trinhphuoc
 * @version: 1.0
 * @since: Feb 19, 2014
 */

public class OrmSqliteFragment extends BaseFragment implements OnClickListener {
	static OrmSqliteFragment instance;
	public Button cities_btn, employee_btn, filter_btn;

	/**
	 * noi dung sua
	 * 
	 * @author:thuan.tp
	 * @since:Feb 19, 2014
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}

	/**
	 * 
	 * Khoi tao fragment cung gia tri tu acc view khac gui qua
	 * 
	 * @author: thuan.tp
	 * @param:
	 * @return: OrmSqliteFragment
	 * @Since: Feb 12, 2014
	 * @throws:
	 */
	public static OrmSqliteFragment getInstance() {
		if (instance == null) {
			instance = new OrmSqliteFragment();
		}
		return instance;
	}

	/**
	 * noi dung sua
	 * 
	 * @author:thuantp
	 * @since:Feb 19, 2014
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup view = (ViewGroup) inflater.inflate(
				R.layout.layout_orm_sqlite_fragment, container, false);
		View v = super.onCreateView(inflater, view, savedInstanceState);

		return v;
	}

	/**
	 * noi dung sua
	 * 
	 * @author:thuan.trinhphuoc
	 * @since:Feb 19, 2014
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		cities_btn = (Button) getActivity().findViewById(R.id.cities_btn);
		employee_btn = (Button) getActivity().findViewById(R.id.employee_btn);
		filter_btn = (Button) getActivity().findViewById(R.id.filter_btn);

		cities_btn.setOnClickListener(OrmSqliteFragment.this);
		employee_btn.setOnClickListener(OrmSqliteFragment.this);
		filter_btn.setOnClickListener(OrmSqliteFragment.this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		Intent i = null;
		SenderAction objectSender;
		switch (v.getId()) {
		case R.id.cities_btn:
			objectSender = new SenderAction();
			objectSender.sender = this;
			objectSender.bundle = new Bundle();
			objectSender.startScreen = CitiesFragment.getInstance();
			UserPresenter.getInstance().onHandleSwitchFragment(objectSender,R.id.displayFragment,false);
			break;
		case R.id.employee_btn:
			objectSender = new SenderAction();
			objectSender.sender = this;
			objectSender.bundle = new Bundle();
			objectSender.startScreen = EmployeeFragment.getInstance();
			UserPresenter.getInstance().onHandleSwitchFragment(objectSender,R.id.displayFragment,false);
			break;
		case R.id.filter_btn:
			objectSender = new SenderAction();
			objectSender.sender = this;
			objectSender.bundle = new Bundle();
			objectSender.startScreen = FilterFragment.getInstance();
			UserPresenter.getInstance().onHandleSwitchFragment(objectSender,R.id.displayFragment,false);
			break;
		default:
			break;
		}

	}

}
