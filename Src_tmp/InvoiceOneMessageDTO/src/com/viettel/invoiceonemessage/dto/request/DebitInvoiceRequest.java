package com.viettel.invoiceonemessage.dto.request;

import com.viettel.lib.webservice.json.BaseMessageRequest;

public class DebitInvoiceRequest extends BaseMessageRequest {
	private String currentDay;// Ngày hiện tại
	private String userName;// Tên đăng nhập
	private String customerID;// ID khách hàng
	private String invoiceID;// ID Hóa đơn

	public String getCurrentDay() {
		return currentDay;
	}

	public void setCurrentDay(String currentDay) {
		this.currentDay = currentDay;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
	}

}
