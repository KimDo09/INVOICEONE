package com.viettel.invoiceonemessage.dto.responses;

import com.viettel.lib.webservice.json.BaseMessageResponses;

public class DebitInvoiceResponses extends BaseMessageResponses {
	private String customerID;// ID khách hàng trả về để cập nhật trạng thái
								// dưới Client
	private String invoiceID;// ID Hóa đơn trả về để cập nhật trạng thái dưới
								// Client
	private Float customerRemainingMoney;// Số tiền nợ còn lại của khách hàng
	private String transactionID;// Mã giao dịch của một hóa đơn

	public Float getCustomerRemainingMoney() {
		return customerRemainingMoney;
	}

	public void setCustomerRemainingMoney(Float customerRemainingMoney) {
		this.customerRemainingMoney = customerRemainingMoney;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	/**
	 * @return the customerID
	 */
	public String getCustomerID() {
		return customerID;
	}

	/**
	 * @param customerID
	 *            the customerID to set
	 */
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	/**
	 * @return the invoiceID
	 */
	public String getInvoiceID() {
		return invoiceID;
	}

	/**
	 * @param invoiceID
	 *            the invoiceID to set
	 */
	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
	}

}