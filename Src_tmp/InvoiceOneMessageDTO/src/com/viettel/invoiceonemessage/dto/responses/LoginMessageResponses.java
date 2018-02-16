package com.viettel.invoiceonemessage.dto.responses;

import com.viettel.lib.webservice.json.BaseMessageResponses;

public class LoginMessageResponses extends BaseMessageResponses {
	private String maTNV; // mã Thu Ngân Viên
	private String tenTNV;// tên Thu Ngân Viên
	private String token;// Chuỗi token trả về từ server
	private String currentDate;// Trả về ngày hiện tại của server, để khi các
								// request khác, lấy ngày này, ko phụ thuộc vào
								// ngày của hệ thống Client - vì Client có thể
								// thay đổi ngày

	/**
	 * @return the maTNV
	 */
	public String getMaTNV() {
		return maTNV;
	}

	/**
	 * @param maTNV
	 *            the maTNV to set
	 */
	public void setMaTNV(String maTNV) {
		this.maTNV = maTNV;
	}

	/**
	 * @return the tenTNV
	 */
	public String getTenTNV() {
		return tenTNV;
	}

	/**
	 * @param tenTNV
	 *            the tenTNV to set
	 */
	public void setTenTNV(String tenTNV) {
		this.tenTNV = tenTNV;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the currentDate
	 */
	public String getCurrentDate() {
		return currentDate;
	}

	/**
	 * @param currentDate
	 *            the currentDate to set
	 */
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

}
