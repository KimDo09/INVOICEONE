/*
 * @ClassName: LoginRequestMess.java
 * @Date: Feb 6, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.lib.logs;

import com.viettel.lib.webservice.json.BaseMessageRequest;

/**
 * Lop gui thog tin loi
 * 
 * @author: duchha
 * @version: 1.0
 * @since: Feb 6, 2014
 */

public class SendLogMessRequest extends BaseMessageRequest {
	private String errorCode;
	private String imei;
	private String contentError;
	private String timeSendLog;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getContentError() {
		return contentError;
	}

	public void setContentError(String contentError) {
		this.contentError = contentError;
	}

	public String getTimeSendLog() {
		return timeSendLog;
	}

	public void setTimeSendLog(String timeSendLog) {
		this.timeSendLog = timeSendLog;
	}

}
