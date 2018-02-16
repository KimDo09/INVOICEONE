/**
 * Copyright 2014 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.viettel.invoice.one.R;
import com.viettel.invoiceonemessage.dto.responses.CustomerResponse;
import com.viettel.lib.eventlistener.OnEventControlListener;

/**
 * InvoiceAdapter
 * 
 * @author: vuonghv2
 * @version: 1.0
 * @since: Apr 4, 2014
 */
public class InvoiceAdapter extends ArrayAdapter<CustomerResponse> {

	private List<CustomerResponse> items;
	private Context mContext;
	private OnEventControlListener evenSendDataBackFragment;

	public InvoiceAdapter(Context context, List<CustomerResponse> items,
			OnEventControlListener listener) {
		super(context, 0, items);
		this.mContext = context;
		this.items = items;
		this.evenSendDataBackFragment = listener;
	}

	@Override
	public CustomerResponse getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
		// return 30;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Object item = getItem(position);
		if (item instanceof CustomerResponse) {
			CustomerItemControl itemControl = null;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.item_customer, null);
				itemControl = new CustomerItemControl(mContext, convertView,
						evenSendDataBackFragment);
				convertView.setTag(itemControl);
			} else {
				itemControl = (CustomerItemControl) convertView.getTag();
			}
			itemControl.setValues((CustomerResponse) item, position);
		}
		return convertView;
	}
}