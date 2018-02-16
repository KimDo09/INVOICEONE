/*
 * @ClassName: SenderAction.java
 * @Date: Jan 9, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.lib.commonobject;

import com.viettel.lib.utils.StringUtils;
import com.viettel.lib.webservice.json.BaseMessageRequest;
import com.viettel.lib.webservice.json.BaseMessageResponses;

/**
 * Doi tuong gui du lieu tu View den Presenter va tu Presenter den Model
 * 
 * @author: nhantd
 * @version: 1.0
 * @since: Jan 9, 2014
 */

public class SenderAction {

	public int action; // Hanh dong cua View.
	public Object sender; // Doi tuong gui du lieu nhu activity, fragment.
	public Object bundle; // Doi tuong chua du lieu truyen di.
	public BaseMessageRequest messRequest;// Doi tuong chua du lieu truyen di.
	public Class<?> messResponses;// Doi tuong chua du lieu nhan ve.
	public Object startScreen; // Activity muon start | Fragment can start, dung
								// trong
								// handleswitchactivity.| handswitchFragment
	public int requestCode; // requestCode khi thuc hien
							// starrtactivityforresult.
	public int tag = 0; // tag du tru de xac dinh senderAction.
	public Object logData; // logData du tru cho viec log.
	public String method;// Phuong thuc ket den server
	public Boolean isRequestToDb = false; // dung de phan biet request tu view
											// den db hay la webservice
	public Boolean isfinish = false; // co dong man hinh khi goi man hinh khac

	// hay khong
	public SenderAction() {
		action = 0;
		sender = null;
		bundle = null;
		startScreen = null;
		messRequest = new BaseMessageRequest();
		messResponses = null;
		method = StringUtils.EMPTY_STRING;
		tag = 0;
		logData = null;
		isRequestToDb = false;
		requestCode = -1;
		isfinish = false;
	}

	/**
	 * Reset senderAction
	 * 
	 * @author: Nhantd ${tags}
	 * @return: void
	 * @throws:
	 */
	public void reset() {
		action = 0;
		sender = null;
		bundle = null;
		startScreen = null;
		messRequest = null;
		method = StringUtils.EMPTY_STRING;
		tag = 0;
		logData = null;
		requestCode = -1;
		isfinish = false;
	}

	/**
	 * @return the isfinish
	 */
	public Boolean getIsfinish() {
		return isfinish;
	}

	/**
	 * @param isfinish
	 *            the isfinish to set
	 */
	public void setIsfinish(Boolean isfinish) {
		this.isfinish = isfinish;
	}

	/**
	 * @return the action
	 */
	public int getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(int action) {
		this.action = action;
	}

	/**
	 * @return the sender
	 */
	public Object getSender() {
		return sender;
	}

	/**
	 * @param sender
	 *            the sender to set
	 */
	public void setSender(Object sender) {
		this.sender = sender;
	}

	/**
	 * @return the bundle
	 */
	public Object getBundle() {
		return bundle;
	}

	/**
	 * @param bundle
	 *            the bundle to set
	 */
	public void setBundle(Object bundle) {
		this.bundle = bundle;
	}

	/**
	 * @return the startActivity
	 */
	public Object getStartActivity() {
		return startScreen;
	}

	/**
	 * @param startActivity
	 *            the startActivity to set
	 */
	public void setStartActivity(Object startActivity) {
		this.startScreen = startActivity;
	}

	/**
	 * @return the tag
	 */
	public int getTag() {
		return tag;
	}

	/**
	 * @param tag
	 *            the tag to set
	 */
	public void setTag(int tag) {
		this.tag = tag;
	}

	/**
	 * @return the logData
	 */
	public Object getLogData() {
		return logData;
	}

	/**
	 * @param logData
	 *            the logData to set
	 */
	public void setLogData(Object logData) {
		this.logData = logData;
	}

	public BaseMessageRequest getMessRequest() {
		return messRequest;
	}

	public void setMessRequest(BaseMessageRequest messRequest) {
		this.messRequest = messRequest;
	}

	public Class<?> getMessResponses() {
		return messResponses;
	}

	public void setMessResponses(Class<?> messResponses) {
		this.messResponses = messResponses;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
