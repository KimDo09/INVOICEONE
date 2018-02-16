/*
 * @ClassName: ReceiverAction.java
 * @Date: Jan 9, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.lib.commonobject;

/**
 *  Doi tuong truyen du lieu tu Model den Presenter va tu Presenter den View
 *  @author: nhantd
 *  @version: 1.0
 *  @since: Jan 9, 2014
 */

public class ReceiverAction {
	
	public SenderAction senderAction;	// Doi tuong truyen du lieu tu View xuong, 
										// sau khi xu ly tra ve thi them vao receiverAction 
										// de co the dung den.
	public Object bundle;				// Doi tuong chua du lieu tra ve.
	public int errorCode;				// Ma tra ve (neu co ke noi gui request den Server).
	public String messageCode;			// Chuoi String tra ve tuong ung voi errorCode neu co.
	public int tag = 0;					// tag du tru de xac dinh receiverAction.
	public Object logData;				// logData du tru cho viec log.
	/**
	 * @return the senderAction
	 */
	public SenderAction getSenderAction() {
		return senderAction;
	}
	/**
	 * @param senderAction the senderAction to set
	 */
	public void setSenderAction(SenderAction senderAction) {
		this.senderAction = senderAction;
	}
	/**
	 * @return the bundle
	 */
	public Object getBundle() {
		return bundle;
	}
	/**
	 * @param bundle the bundle to set
	 */
	public void setBundle(Object bundle) {
		this.bundle = bundle;
	}
	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the messageCode
	 */
	public String getMessageCode() {
		return messageCode;
	}
	/**
	 * @param messageCode the messageCode to set
	 */
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	/**
	 * @return the tag
	 */
	public int getTag() {
		return tag;
	}
	/**
	 * @param tag the tag to set
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
	 * @param logData the logData to set
	 */
	public void setLogData(Object logData) {
		this.logData = logData;
	}
}
