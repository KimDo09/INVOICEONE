/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.webservice.json;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.util.Log;
import com.viettel.lib.commonobject.BaseModel;
import com.viettel.lib.commonobject.SenderAction;

/**
 * Mo ta muc dich cua lop (interface)
 * 
 * @author: Nhantd
 * @version: 1.0
 * @since: 1.0
 */
public class SendMessageRequestSync {

	private String requestURL; // URL cua yeu cau can gui len Webservice
	private BaseModel baseModel; // baseModel goi request len Webservice
	private Class<?> type; // class response nhan du lieu tra ve va parse
	private BaseMessageRequest message; // message chua thong tin du lieu
										// request
	private String messageRsa; // message chua thong tin du lieu request da duoc
	// ma hoa rsa thanh string
	private SenderAction senderAction; // senderAction chua thong tin goi
										// Webservice
	@SuppressWarnings("unused")
	private JSONObject jsonObj = null; // Doi tuong JSON chua response tra ve tu
										// Webservice
	private ResponseEntity<?> response = null; // Response tra ve tu Webservice

	public String authentication_name = null; // ten Authentication cua request;
	public String authentication_password = null; // pass Authentication cua
													// request
	public boolean useHttps = false; // check use http or https
	private boolean encryptRSA = false; // check encrypt

	/**
	 * 
	 * Contructors
	 * 
	 * @author: Nhantd
	 */
	public SendMessageRequestSync(SenderAction senderAction,
			BaseMessageRequest message, String requestURL, BaseModel model,
			Class<?> typeResponses) {
		this.encryptRSA = false;
		this.requestURL = requestURL;
		this.baseModel = model;
		this.message = message;
		this.senderAction = senderAction;
		this.type = typeResponses;
	}

	public SendMessageRequestSync(SenderAction senderAction, String messagersa,
			String requestURL, BaseModel model, Class<?> typeResponses) {
		this.encryptRSA = true;
		this.requestURL = requestURL;
		this.baseModel = model;
		this.messageRsa = messagersa;
		this.senderAction = senderAction;
		this.type = typeResponses;
	}

	/**
	 * 
	 * Ham xu ly goi request sync
	 * 
	 * @author: Nhantd
	 * @param params
	 * @return: void
	 * @throws:
	 */
	public void execute(MediaType... params) {
		try {
			// Mac dinh kieu du lieu trao doi voi service la JSON
			MediaType mediaType = MediaType.APPLICATION_JSON;

			// Neu co tham so truyen vao thi thiet lap lai kieu trao doi du lieu
			if (params != null) {
				mediaType = params[0];
			}

			// Tao Header cho JSON
			HttpHeaders headerRequest = new HttpHeaders();
			// Thiet lap kieu du lieu trao doi vao Header
			headerRequest.setContentType(mediaType);
			// them cai nay de tranh loi EOFException - tuy nhien co the anh
			// huong toi hieu nang.
			headerRequest.set("Connection", "Close");

			/*
			 * // neu da login roi thi them cookie vao request header
			 * requestHeaders.set("Cookie",
			 * GlobalApplication.getSessionManager() .getCookie());
			 * Log.d("ANDROID COOKIE: BEFORE", GlobalApplication
			 * .getSessionManager().getCookie());
			 */

			// Populate the HTTP Basic Authentitcation header with the username
			// and password
			if (authentication_name != null && authentication_password != null) {
				HttpAuthentication authHeader = new HttpBasicAuthentication(
						authentication_name, authentication_password);
				headerRequest.setAuthorization(authHeader);
			}

			HttpEntity<?> requestEntity;
			// Check use encrypt rsa
			if (encryptRSA) {
				requestEntity = new HttpEntity<String>(messageRsa,
						headerRequest);
			} else {
				requestEntity = new HttpEntity<BaseMessageRequest>(message,
						headerRequest);
			}

			// tao mot the hien restTemplate
			RestTemplate restTemplate = new RestTemplate();
			// Convert message sang dinh dang HTTP
			restTemplate.getMessageConverters().add(
					new StringHttpMessageConverter());

			// su dung dung giao thuc Https
			if (useHttps) {
				HttpClient httpClient = SSLClient.getNewHttpClient();
				restTemplate
						.setRequestFactory(new HttpComponentsClientHttpRequestFactory(
								httpClient));
			} else {
				restTemplate
						.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			}

			if (mediaType == MediaType.APPLICATION_JSON) {
				// Convert message sang dinh dang JSON
				restTemplate.getMessageConverters().add(
						new MappingJackson2HttpMessageConverter());
			} else if (mediaType == MediaType.APPLICATION_XML) {
				// Convert message sang dinh dang XML
				restTemplate.getMessageConverters().add(
						new SimpleXmlHttpMessageConverter());
			}

			try {
				response = restTemplate.exchange(requestURL, HttpMethod.POST,
						requestEntity, type);

				// thuc hien luu cookie cua nguoi dung
				/*
				 * HttpHeaders header = response.getHeaders(); List<String> val
				 * = header.get("Set-Cookie"); if (null != val) { String cookie
				 * = val.get(0);
				 * GlobalApplication.getSessionManager().setCookie(cookie); }
				 */

				Log.e("POSTASK SUCCESS", "Thanh cong");

				processResponse((BaseMessageResponses) response.getBody());

			} catch (RestClientException e) {
				BaseMessageResponses restClientError = new BaseMessageResponses();
				if (e.getMessage().equals("400 Bad Request")) {
					restClientError
							.setResponseCode(BaseMessageResponses.BAD_REQUEST);
					restClientError.setMessage("Wrong request message");
					Log.d("POST MESSAGE TASK", e.getMessage());
				} else {
					restClientError
							.setResponseCode(BaseMessageResponses.NETWORK_TIMEOUT);
					restClientError.setMessage("No network found");
					Log.d("POST MESSAGE TASK", e.getMessage());
				}
				processResponse(restClientError);
			} catch (org.springframework.http.converter.HttpMessageNotReadableException e) {
				BaseMessageResponses wrongMessageError = new BaseMessageResponses();
				wrongMessageError
						.setResponseCode(BaseMessageResponses.DIFFERENCE_MESSAGE_ERROR);
				wrongMessageError
						.setMessage("Wrong message structure from server.");
				Log.d("POST MESSAGE TASK", e.getMessage());
				processResponse(wrongMessageError);
			}
		} catch (ResourceAccessException e) {
			Log.e("(POST TASK)NETWORK ERROR", e.getMessage());
			BaseMessageResponses error = new BaseMessageResponses();
			error.setResponseCode(BaseMessageResponses.NETWORK_TIMEOUT);
			processResponse(error);
		}
	}

	/**
	 * 
	 * Ham xu ly response tra ve tu Webservice
	 * 
	 * @author: Nhantd
	 * @param result
	 * @return: void
	 * @throws:
	 */
	private void processResponse(BaseMessageResponses result) {
		if (result.getResponseCode() != BaseMessageResponses.SUCCESS) {
			// Response that bai
			baseModel.onReceiverError(senderAction, result);
		} else {
			baseModel.onReceiverSuccess(senderAction, result);
		}
	}
}
