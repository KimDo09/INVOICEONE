package com.viettel.invoiceonemessage.dto.responses;

import java.util.ArrayList;

import com.viettel.lib.webservice.json.BaseMessageResponses;

public class DebitListInvoiceResponses extends BaseMessageResponses {
	private ArrayList<DebitInvoiceResponses> listTransaction;// Danh sách khách
																// hàng

	/**
	 * @return the listTransaction
	 */
	public ArrayList<DebitInvoiceResponses> getListTransaction() {
		return listTransaction;
	}

	/**
	 * @param listTransaction
	 *            the listTransaction to set
	 */
	public void setListTransaction(
			ArrayList<DebitInvoiceResponses> listTransaction) {
		this.listTransaction = listTransaction;
	}

}
