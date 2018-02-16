package com.viettel.invoiceonemessage.dto.responses;

import com.viettel.lib.webservice.json.BaseMessageResponses;

public class InvoiceResponse extends BaseMessageResponses {
	private String customerName;// Tên khách hàng
	private String customerAddress;// Địa chỉ
	private String customerID;// Mã khách hàng
	private Float electricTotal;// Sản lượng điện
	private Float invoiceMoney;// Tiền
	private String customerSTT;// Số thứ tự
	private String invoiceType;// Loại hóa đơn
	private String invoiceTime;// Kỳ hóa đơn
	private String invoiceID;// ID hóa đơn

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public Float getElectricTotal() {
		return electricTotal;
	}

	public void setElectricTotal(Float electricTotal) {
		this.electricTotal = electricTotal;
	}

	public Float getInvoiceMoney() {
		return invoiceMoney;
	}

	public void setInvoiceMoney(Float invoiceMoney) {
		this.invoiceMoney = invoiceMoney;
	}

	public String getCustomerSTT() {
		return customerSTT;
	}

	public void setCustomerSTT(String customerSTT) {
		this.customerSTT = customerSTT;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceTime() {
		return invoiceTime;
	}

	public void setInvoiceTime(String invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public String getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
	}

}
