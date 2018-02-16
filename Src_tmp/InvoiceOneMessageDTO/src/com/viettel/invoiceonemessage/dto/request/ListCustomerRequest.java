package com.viettel.invoiceonemessage.dto.request;

import com.viettel.lib.webservice.json.BaseMessageRequest;

public class ListCustomerRequest extends BaseMessageRequest {
	private String currentDay;// Ngày hiện tại
	private String username;// Tên đăng nhập

	/**
	 * @return the currentDay
	 */
	public String getCurrentDay() {
		return currentDay;
	}

	/**
	 * @param currentDay
	 *            the currentDay to set
	 */
	public void setCurrentDay(String currentDay) {
		this.currentDay = currentDay;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
