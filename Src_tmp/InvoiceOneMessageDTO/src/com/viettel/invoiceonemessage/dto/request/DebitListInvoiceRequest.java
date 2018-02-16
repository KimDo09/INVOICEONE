package com.viettel.invoiceonemessage.dto.request;

import java.util.ArrayList;

import com.viettel.lib.webservice.json.BaseMessageRequest;

public class DebitListInvoiceRequest extends BaseMessageRequest {
	private ArrayList<DebitInvoiceRequest> listInvoice;// Danh sách hóa đơn

	/**
	 * @return the listInvoice
	 */
	public ArrayList<DebitInvoiceRequest> getListInvoice() {
		return listInvoice;
	}

	/**
	 * @param listInvoice
	 *            the listInvoice to set
	 */
	public void setListInvoice(ArrayList<DebitInvoiceRequest> listInvoice) {
		this.listInvoice = listInvoice;
	}

}
