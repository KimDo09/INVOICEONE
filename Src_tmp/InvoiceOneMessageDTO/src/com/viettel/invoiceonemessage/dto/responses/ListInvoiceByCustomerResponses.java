package com.viettel.invoiceonemessage.dto.responses;

import java.util.ArrayList;

import com.viettel.lib.webservice.json.BaseMessageResponses;

public class ListInvoiceByCustomerResponses extends BaseMessageResponses {
	private ArrayList<InvoiceResponse> listInvoiceByCustomer;// Danh sách hóa đơn của 1 khách hàng

	public ArrayList<InvoiceResponse> getListInvoiceByCustomer() {
		return listInvoiceByCustomer;
	}

	public void setListInvoiceByCustomer(
			ArrayList<InvoiceResponse> listInvoiceByCustomer) {
		this.listInvoiceByCustomer = listInvoiceByCustomer;
	}

}
