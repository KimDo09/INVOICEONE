/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.viettel.invoice.one.R;
import com.viettel.invoiceonemessage.dto.responses.CustomerResponse;
import com.viettel.lib.eventlistener.OnEventControlListener;
import com.viettel.vpmt.template.constant.ActionConstants;

/**
 * MainItemControlApp
 * 
 * @author: vuonghv2
 * @version: 1.0
 * @since: Apr 4, 2014
 */
public class CustomerItemControl extends LinearLayout implements
		OnClickListener {

	// Đối tượng context
	private Context mContext;
	// Đối tượng lắng nghe sự kiện
	private OnEventControlListener evenSendDataBackFragment;
	// View chứa tất các các đối tượng
	private RelativeLayout rlCustomer;

	private TextView customerName;
	private TextView customerAddress;
	private TextView customerID;
	private TextView customerInvoiceMoney;
	private TextView customerSTT;

	public CustomerItemControl(Context context, View v,
			OnEventControlListener onEvent) {
		super(context);
		this.mContext = context;
		this.rlCustomer = (RelativeLayout) v;
		this.evenSendDataBackFragment = onEvent;
		initLayout();
	}

	/**
	 * Khởi tạo dữ liệu
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: void
	 * @Since: Apr 25, 2014
	 * @throws:
	 */
	private void initLayout() {
		customerName = (TextView) rlCustomer.findViewById(R.id.customerName);
		customerAddress = (TextView) rlCustomer
				.findViewById(R.id.customerAddress);
		customerID = (TextView) rlCustomer.findViewById(R.id.customerID);
		customerInvoiceMoney = (TextView) rlCustomer
				.findViewById(R.id.customerInvoiceMoney);
		customerSTT = (TextView) rlCustomer.findViewById(R.id.customerSTT);
		rlCustomer.setOnClickListener(this);
	}

	/**
	 * Reset các giá trị
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: void
	 * @Since: Apr 25, 2014
	 * @throws:
	 */
	public void refreshControl() {

	}

	/**
	 * Thiết lập giá trị
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: void
	 * @Since: Apr 25, 2014
	 * @throws:
	 */
	public void setValues(CustomerResponse customerResponse, int pos) {
		refreshControl();

	}

	/**
	 * Hảm xử lý sự kiện OnClick
	 * 
	 * @author:vuonghv2
	 * @since:Apr 10, 2014
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rlCustomer:
			evenSendDataBackFragment.onEvent(
					ActionConstants.ACTION_GOTO_DETAIL_CUSTOMER, null,
					v.getTag());
			break;
		}

	}
}
