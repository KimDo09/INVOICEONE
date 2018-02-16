/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.commonobject;

import org.springframework.http.MediaType;

import android.util.Log;

import com.viettel.lib.webservice.json.BaseMessageRequest;
import com.viettel.lib.webservice.json.BaseMessageResponses;
import com.viettel.lib.webservice.json.SendMessageRequest;
import com.viettel.lib.webservice.json.SendMessageRequestSync;

/**
 * Abstract class cua UserModel
 * 
 * @author: Nhantd
 * @version: 1.0
 * @since: 1.0
 */
public abstract class BaseModel {

	/**
	 * 
	 * Mo ta chuc nang cua ham
	 * 
	 * @author: Duchha
	 * @param senderAction
	 * @param dataResponse
	 * @return: void
	 * @throws:
	 */
	public abstract void onReceiverSuccess(SenderAction senderAction,
			Object dataResponse);

	/**
	 * 
	 * Mo ta chuc nang cua ham
	 * 
	 * @author: Duchha
	 * @param senderAction
	 * @param abstractResponse
	 * @return: void
	 * @throws:
	 */
	public abstract void onReceiverError(SenderAction senderAction,
			BaseMessageResponses abstractResponse);

	
	public void sendLogServer(){
		
	}
	
	/**
	 * 
	 * Ham gui request webservice
	 * 
	 * @Variable : senderAction - doi tuong gui request message - cau truc du
	 *           lieu gui request urlRequest - url request
	 * @author: nhantd
	 * @return: void
	 * @Since: Jan 10, 2014
	 * @throws:
	 */
	protected void requestService(SenderAction senderAction,
			BaseMessageRequest message, String urlRequest, Class<?> type) {
		try {
			SendMessageRequest task = new SendMessageRequest(senderAction,
					message, urlRequest, this, type);
			// goi thuc thi onBackground - goi request Webservice
			task.execute((MediaType[]) null);
		} catch (Exception e) {
			Log.e("Error when call webservice", e.getLocalizedMessage());
		}
	}

	/**
	 * 
	 * Ham gui request webservice
	 * 
	 * @Variable : senderAction - doi tuong gui request message - cau truc du
	 *           lieu gui request urlRequest - url request
	 * @author: duchha
	 * @return: void
	 * @Since: Jan 10, 2014
	 * @throws:
	 */
	protected void requestService(SenderAction senderAction, boolean checkhttps) {
		try {
			SendMessageRequest task = new SendMessageRequest(senderAction,
					senderAction.getMessRequest(), senderAction.getMethod(),
					this, senderAction.getMessResponses());
			task.useHttps = checkhttps;
			// goi thuc thi onBackground - goi request Webservice
			task.execute((MediaType[]) null);
		} catch (Exception e) {
			Log.e("Error when call webservice", e.getLocalizedMessage());
		}
	}

	/**
	 * 
	 * Ham gui request webservice co ma hoa RSA
	 * 
	 * @author: Nhantd
	 * @Variable : senderAction - doi tuong gui request message - cau truc du
	 *           lieu gui request dang string duoc ma hoa RSA urlRequest - url
	 *           request
	 * @return: void
	 * @throws:
	 */
	protected void requestServiceWithRSAEncrypt(SenderAction senderAction,
			String message, String urlRequest, Class<?> type) {
		try {
			SendMessageRequest task = new SendMessageRequest(senderAction,
					message, urlRequest, this, type);
			// goi thuc thi onBackground - goi request Webservice
			task.execute((MediaType[]) null);
		} catch (Exception e) {
			Log.e("Error when call webservice", e.getLocalizedMessage());
		}
	}

	/**
	 * 
	 * Ham gui request webservice sync
	 * 
	 * @Variable : senderAction - doi tuong gui request message - cau truc du
	 *           lieu gui request urlRequest - url request
	 * @author: nhantd
	 * @return: void
	 * @Since: Jan 10, 2014
	 * @throws:
	 */
	protected void requestServiceSync(SenderAction senderAction,
			BaseMessageRequest message, String urlRequest, Class<?> type) {
		try {
			SendMessageRequestSync task = new SendMessageRequestSync(
					senderAction, message, urlRequest, this, null);
			// goi thuc thi onBackground - goi request Webservice
			task.execute((MediaType[]) null);
		} catch (Exception e) {
			Log.e("Error when call webservice", e.getLocalizedMessage());
		}
	}

	/**
	 * 
	 * Ham gui request webservicesync co ma hoa RSA
	 * 
	 * @author: Nhantd
	 * @Variable : senderAction - doi tuong gui request message - cau truc du
	 *           lieu gui request dang string duoc ma hoa RSA urlRequest - url
	 *           request
	 * @return: void
	 * @throws:
	 */
	protected void requestServiceSyncWithRSAEncrypt(SenderAction senderAction,
			String message, String urlRequest, Class<?> type) {
		try {
			SendMessageRequest task = new SendMessageRequest(senderAction,
					message, urlRequest, this, null);
			// goi thuc thi onBackground - goi request Webservice
			task.execute((MediaType[]) null);
		} catch (Exception e) {
			Log.e("Error when call webservice", e.getLocalizedMessage());
		}
	}
}
