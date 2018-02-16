/**
 * Copyright 2014 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.view.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.viettel.invoice.one.R;
import com.viettel.invoiceonemessage.dto.request.ListCustomerRequest;
import com.viettel.invoiceonemessage.dto.responses.CustomerResponse;
import com.viettel.invoiceonemessage.dto.responses.ListCustomerResponses;
import com.viettel.lib.commonobject.ReceiverAction;
import com.viettel.lib.commonobject.SenderAction;
import com.viettel.lib.eventlistener.OnEventControlListener;
import com.viettel.vpmt.template.GlobalApplication;
import com.viettel.vpmt.template.base.BaseActivity;
import com.viettel.vpmt.template.common.ServerPath;
import com.viettel.vpmt.template.constant.ActionConstants;
import com.viettel.vpmt.template.presenter.UserPresenter;
import com.viettel.vpmt.template.view.adapter.InvoiceAdapter;
import com.viettel.vpmt.template.view.control.ProgressBarTextView;

/**
 * MainActivity
 * 
 * @author: vuonghv2
 * @version: 1.0
 * @since: Jun 15, 2014
 */

public class MainActivity extends BaseActivity {

	// Listview chứa danh sách khách hàng
	ListView listviewDanhSachKhachHang;
	// EditText tìm kiếm hóa đơn theo ID Hóa đơn
	EditText timkiemHoaDon;
	// EditText tìm kiếm hóa đơn theo mã Khách Hàng
	EditText timkiemKhachHang;
	// InvoiceAdapter
	InvoiceAdapter invoiceAdapter;
	// Danh sách khách hàng
	private List<CustomerResponse> listCustomer;
	// Biến đăng ký lắng nghe sự kiện
	private static OnEventControlListener mListener;
	ProgressBarTextView progressBarTextView;

	/**
	 * onCreate
	 * 
	 * @author:vuonghv2
	 * @since:Jun 11, 2014
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main_activity);

		setConfigLayoutActivity(true, true, R.drawable.background_color);
		setTitleActionBar("Main Activity");
		disEnabledSlidingActionBar();

		initLayout();
		sendRequestGetListCustomer("15/06/2014", GlobalApplication
				.getInstance().getProfile().getUserName());
	}

	/**
	 * sendRequestGetListCustomer
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: void
	 * @Since: Jun 15, 2014
	 * @throws:
	 */
	private void sendRequestGetListCustomer(String currentDay, String username) {
		// TODO Auto-generated method stub
		ListCustomerRequest listCustomerRequest = new ListCustomerRequest();
		listCustomerRequest.setCurrentDay(currentDay);
		listCustomerRequest.setUsername(username);

		SenderAction senderAction = new SenderAction();
		senderAction.setAction(ActionConstants.ACTION_GET_LIST_CUSTOMER);
		senderAction.setMessRequest(listCustomerRequest);
		senderAction.setMethod(ServerPath.SERVER_PATH_OAUTH + "getListCustomer");
		senderAction.setMessResponses(ListCustomerResponses.class);
		senderAction.setSender(this);
		UserPresenter.getInstance().onSendViewToModel(senderAction);
	}

	/**
	 * initLayout
	 * 
	 * @author:vuonghv2
	 * @since:Jun 11, 2014
	 */
	void initLayout() {
		mListener = this;
		listviewDanhSachKhachHang = (ListView) findViewById(R.id.listviewDanhSachKhachHang);
		timkiemHoaDon = (EditText) findViewById(R.id.timkiemHoaDon);
		timkiemKhachHang = (EditText) findViewById(R.id.timkiemKhachHang);

		progressBarTextView = (ProgressBarTextView) findViewById(R.id.progressBarTextView);
		progressBarTextView.setVisibility(View.VISIBLE);

		listCustomer = new ArrayList<CustomerResponse>();
		invoiceAdapter = new InvoiceAdapter(this, listCustomer, mListener);
		listviewDanhSachKhachHang.setAdapter(invoiceAdapter);
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

		default:
			break;
		}
		super.onClick(v);
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
		switch (actionEvent) {
		default:
			super.onActionEventListener(actionEvent, actionType, data);
			break;
		}

	}

	/**
	 * onDestroy
	 * 
	 * @author:vuonghv2
	 * @since:Jun 11, 2014
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * onPause
	 * 
	 * @author:vuonghv2
	 * @since:Jun 11, 2014
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/**
	 * onHandlePresenterToViewError
	 * 
	 * @author:vuonghv2
	 * @since:Jun 11, 2014
	 */
	@Override
	public void onHandlePresenterToViewError(ReceiverAction objectRecevier) {
		// TODO Auto-generated method stub

		switch (objectRecevier.getSenderAction().action) {
		case ActionConstants.ACTION_GET_LIST_CUSTOMER:
			closeDialog();
			ListCustomerResponses listCustomerResponses = getListCustomer();
			setDataToView(listCustomerResponses.getListCustomer());
			break;
		default:
			super.onHandlePresenterToViewError(objectRecevier);
			break;
		}
	}

	/**
	 * onHandlePresenterToViewSuccess
	 * 
	 * @author:vuonghv2
	 * @since:Jun 11, 2014
	 */
	@Override
	public void onHandlePresenterToViewSuccess(ReceiverAction objectRecevier) {
		switch (objectRecevier.getSenderAction().action) {
		case ActionConstants.ACTION_GET_LIST_CUSTOMER:

			break;
		default:
			super.onHandlePresenterToViewSuccess(objectRecevier);
			break;
		}
	}

	/**
	 * setDataToView
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: void
	 * @Since: Jun 15, 2014
	 * @throws:
	 */
	private void setDataToView(ArrayList<CustomerResponse> listCustomertmp) {
		// TODO Auto-generated method stub
		for (int i = 0; i < listCustomertmp.size(); i++) {
			listCustomer.add(listCustomertmp.get(i));
			invoiceAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * onEvent
	 * 
	 * @author:vuonghv2
	 * @since:Jun 11, 2014
	 */
	@Override
	public void onEvent(int eventType, View control, Object data) {
		switch (eventType) {

		default:
			super.onEvent(eventType, control, data);
			break;
		}

	}

	/**
	 * getListCustomer - Du lieu gia
	 * 
	 * @author:vuonghv2
	 * @since:Jun 11, 2014
	 */
	public ListCustomerResponses getListCustomer() {
		ListCustomerResponses listCustomerResponses = new ListCustomerResponses();
		ArrayList<CustomerResponse> listCustomertmp = new ArrayList<CustomerResponse>();

		for (int i = 0; i < 100; i++) {
			CustomerResponse customerResponse = new CustomerResponse();
			customerResponse.setCustomerID("" + i);
			customerResponse.setCustomerAddress("Address " + i);
			customerResponse.setCustomerName("Customer " + i);
			customerResponse.setInvoiceMoney((float) 12456);
			customerResponse.setCustomerInvoiceType("TC " + i);
			customerResponse.setCustomerInvoiceTime("TD/120 " + i);
			listCustomertmp.add(customerResponse);
		}
		listCustomerResponses.setListCustomer(listCustomertmp);
		return listCustomerResponses;
	}

	/**
	 * closeDialog
	 * 
	 * @author:vuonghv2
	 * @since:Jun 11, 2014
	 */
	public void closeDialog() {
		if (progressBarTextView.isShowing()) {
			progressBarTextView.setVisibility(View.GONE);
		}
	}
}
