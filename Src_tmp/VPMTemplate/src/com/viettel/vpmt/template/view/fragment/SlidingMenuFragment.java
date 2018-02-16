/**
 * Copyright 2014 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.viettel.lib.utils.StringUtils;
import com.viettel.lib.utils.ToastMessageUtils;
import com.viettel.invoice.one.R;
import com.viettel.vpmt.template.base.BaseFragment;

/**
 * SlidingMenuFragment
 * 
 * @author: vuonghv3
 * @version: 1.0
 * @since: Jun 11, 2014
 */

public class SlidingMenuFragment extends BaseFragment {
	public static final String TAG = SlidingMenuFragment.class.getName();
	static SlidingMenuFragment instance;
	Bundle bundle;
	LinearLayout laysolieugiaothu;
	LinearLayout chamnohoadon;
	LinearLayout chamnohoadonoffline;
	LinearLayout cauhinhhethong;
	LinearLayout solieuthutheongay;
	LinearLayout lichsuthutien;
	LinearLayout doimatkhau;
	LinearLayout huygachnohoadon;
	LinearLayout danhsachhuygachno;
	LinearLayout huysolieutrongdienthoai;
	LinearLayout thoatkhoihethong;

	/**
	 * 
	 * Khoi tao fragment cung gia tri tu acc view khac gui qua
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: FragmentDemo
	 * @Since: Jun 12, 2014
	 * @throws:
	 */
	public static SlidingMenuFragment newInstance(Bundle bundle) {
		if (instance == null) {
			instance = new SlidingMenuFragment();
		}
		instance.bundle = bundle;
		return instance;
	}

	/**
	 * Tao layout
	 * 
	 * vuonghv2
	 * 
	 * @since:Jun 12, 2014
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ViewGroup view = (ViewGroup) inflater.inflate(
				R.layout.layout_slidingmenu_fragment, container, false);
		View v = super.onCreateView(inflater, view, savedInstanceState);
		initLayout(v);
		return v;
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
	private void initLayout(View v) {
		// TODO Auto-generated method stub
		laysolieugiaothu = (LinearLayout) v.findViewById(R.id.laysolieugiaothu);
		chamnohoadon = (LinearLayout) v.findViewById(R.id.chamnohoadon);
		chamnohoadonoffline = (LinearLayout) v
				.findViewById(R.id.chamnohoadonoffline);
		cauhinhhethong = (LinearLayout) v.findViewById(R.id.cauhinhhethong);
		solieuthutheongay = (LinearLayout) v
				.findViewById(R.id.solieuthutheongay);
		lichsuthutien = (LinearLayout) v.findViewById(R.id.lichsuthutien);
		doimatkhau = (LinearLayout) v.findViewById(R.id.doimatkhau);
		huygachnohoadon = (LinearLayout) v.findViewById(R.id.huygachnohoadon);
		danhsachhuygachno = (LinearLayout) v
				.findViewById(R.id.danhsachhuygachno);
		huysolieutrongdienthoai = (LinearLayout) v
				.findViewById(R.id.huysolieutrongdienthoai);
		thoatkhoihethong = (LinearLayout) v.findViewById(R.id.thoatkhoihethong);

		laysolieugiaothu.setOnClickListener(this);
		chamnohoadon.setOnClickListener(this);
		chamnohoadonoffline.setOnClickListener(this);
		cauhinhhethong.setOnClickListener(this);
		solieuthutheongay.setOnClickListener(this);
		lichsuthutien.setOnClickListener(this);
		doimatkhau.setOnClickListener(this);
		huygachnohoadon.setOnClickListener(this);
		danhsachhuygachno.setOnClickListener(this);
		huysolieutrongdienthoai.setOnClickListener(this);
		thoatkhoihethong.setOnClickListener(this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		isFinished = false;

		if (!StringUtils.isNullOrEmpty(savedInstanceState)) {
			bundle = savedInstanceState;
		}
		super.onActivityCreated(savedInstanceState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState = bundle;
		super.onSaveInstanceState(outState);
	}

	/**
	 * onClick
	 * 
	 * @author:vuonghv2
	 * @since:Jun 11, 2014
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.laysolieugiaothu:
			ToastMessageUtils.showToastLong(getActivity(),
					"Lay so lieu giao thu");
			break;
		case R.id.chamnohoadon:
			ToastMessageUtils.showToastShort(getActivity(), "Cham no hoa don");
			break;
		case R.id.chamnohoadonoffline:
			ToastMessageUtils.showToastShort(getActivity(),
					"Cham no hoa don offline");
			break;
		case R.id.cauhinhhethong:
			ToastMessageUtils
					.showToastShort(getActivity(), "Cau hinh he thong");
			break;
		case R.id.solieuthutheongay:
			ToastMessageUtils.showToastShort(getActivity(),
					"So lieu thu theo ngay");
			break;
		case R.id.lichsuthutien:
			ToastMessageUtils.showToastShort(getActivity(), "Lich su thu tien");
			break;
		case R.id.doimatkhau:
			ToastMessageUtils.showToastShort(getActivity(), "Doi mat khau");
			break;
		case R.id.huygachnohoadon:
			ToastMessageUtils.showToastShort(getActivity(),
					"Huy gach no hoa don");
			break;
		case R.id.danhsachhuygachno:
			ToastMessageUtils.showToastShort(getActivity(),
					"Danh sach huy gach no");
			break;
		case R.id.huysolieutrongdienthoai:
			ToastMessageUtils.showToastShort(getActivity(),
					"Huy so lieu trong dien thoai");
			break;
		case R.id.thoatkhoihethong:
			ToastMessageUtils.showToastShort(getActivity(),
					"Thoat khoi he thong");
			break;

		default:
			super.onClick(v);
			break;
		}
	}
}
