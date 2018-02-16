package com.viettel.invoiceonemessage.dto.responses;

import com.viettel.lib.webservice.json.BaseMessageResponses;

public class CustomerResponse extends BaseMessageResponses {
	private String customerName;// Tên khách hàng
	private String customerAddress;// Địa chỉ
	private String customerID;// Mã khách hàng
	private Float electricTotal;// Sản lượng điện
	private Float invoiceMoney;// Tiền
	private String customerSTT;// Số thứ tự
	private String customerInvoiceType;// Loại hóa đơn
	private String customerInvoiceTime;// Kỳ hóa đơn

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

	public String getCustomerInvoiceType() {
		return customerInvoiceType;
	}

	public void setCustomerInvoiceType(String customerInvoiceType) {
		this.customerInvoiceType = customerInvoiceType;
	}

	public String getCustomerInvoiceTime() {
		return customerInvoiceTime;
	}

	public void setCustomerInvoiceTime(String customerInvoiceTime) {
		this.customerInvoiceTime = customerInvoiceTime;
	}

}
