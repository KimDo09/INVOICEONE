/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.webservice.json;

import android.content.Context;

import com.example.androidvtmtlib.R;

/**
 * Class chua thong tin ma loi va loi cua Responses
 * 
 * @author: Nhantd
 * @version: 1.0
 * @since: 1.0
 */
public class BaseMessageResponses {

	/*
	 * ma loi cho session bi timeout
	 */
	public final static int SESSION_TIMEOUT = 403;

	/*
	 * loi ket noi mang
	 */
	public final static int NETWORK_TIMEOUT = 599;

	/*
	 * loi request bi sai cau truc
	 */
	public final static int BAD_REQUEST = 400;

	/*
	 * Loi khong tim thay tai nguyen (request dung cu phap nhung khong co
	 * service nao dap ung)
	 */
	public final static int NOT_FOUND = 404;

	/*
	 * Loi khong biet
	 */
	public final static int UNKNOWN = 0;
	// loi chung
	public static final int ERROR_COMMON = 201;
	/*
	 * Loi ben trong server
	 */
	public final static int INTERAL_SERVER_ERROR = 500;

	/*
	 * Loi ben trong server
	 */
	public final static int DIFFERENCE_MESSAGE_ERROR = 509;

	/*
	 * Loi kieu du lieu khong hop le
	 */
	public final static int VALIDATION_ERROR = 600;

	/*
	 * Loi client khong convert dung dinh dang body response
	 */
	public final static int VALIDATION_CAST = 207;
	public static final int ERROR_SESSION_RESET = 215;// (errorCode=215) session
	// time out
	public static final int ERROR_ACCOUNT_LOCKED_REQUEST = 218;// (errorCode=218)
	public static final int ERROR_LOCATION_DELETED = 219;// (errorCode=219)
	/*
	 * thanh cong
	 */
	public final static int SUCCESS = 200;

	private int responseCode = 200;
	private String message;

	public void setMessage(String message) {
		this.message = message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the message
	 */
	public String getMessage(Context context) {
		switch (this.responseCode) {
		case BAD_REQUEST:
			return context.getString(R.string.bad_request);

		case NETWORK_TIMEOUT:
			return context.getString(R.string.network_timeout);

		case NOT_FOUND:
			return context.getString(R.string.not_found);

		case SESSION_TIMEOUT:
			return context.getString(R.string.session_timeout);

		case INTERAL_SERVER_ERROR:
			return context.getString(R.string.internal_server_error);

		case ERROR_SESSION_RESET:
			return context.getString(R.string.error_session_reset);

		case ERROR_ACCOUNT_LOCKED_REQUEST:
			return context.getString(R.string.error_account_locked_request);

		case ERROR_COMMON:
			return context.getString(R.string.error_common);

		case VALIDATION_ERROR:
			return message;
		}

		return this.message;
	}

}
