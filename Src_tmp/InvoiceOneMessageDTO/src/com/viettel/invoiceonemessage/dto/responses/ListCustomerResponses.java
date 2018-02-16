package com.viettel.invoiceonemessage.dto.responses;

import java.util.ArrayList;

import com.viettel.lib.webservice.json.BaseMessageResponses;

public class ListCustomerResponses extends BaseMessageResponses {
	private ArrayList<CustomerResponse> listCustomer;// Danh sách khách hàng

	/**
	 * @return the listCustomer
	 */
	public ArrayList<CustomerResponse> getListCustomer() {
		return listCustomer;
	}

	/**
	 * @param listCustomer
	 *            the listCustomer to set
	 */
	public void setListCustomer(ArrayList<CustomerResponse> listCustomer) {
		this.listCustomer = listCustomer;
	}

}
