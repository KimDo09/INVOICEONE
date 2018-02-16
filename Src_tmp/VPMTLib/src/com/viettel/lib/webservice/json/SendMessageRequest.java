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

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viettel.lib.commonobject.BaseModel;
import com.viettel.lib.commonobject.SenderAction;
import com.viettel.lib.utils.LogUtils;

/**
 * Class gui request len webservice theo phuong thuc JSON
 * 
 * @author: Nhantd
 * @version: 1.0
 * @since: 1.0
 */
public class SendMessageRequest extends
		AsyncTask<MediaType, Void, BaseMessageResponses> {

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
	public SendMessageRequest(SenderAction senderAction,
			BaseMessageRequest message, String requestURL, BaseModel model,
			Class<?> typeResponses) {
		this.encryptRSA = false;
		this.requestURL = requestURL;
		this.baseModel = model;
		this.message = message;
		this.senderAction = senderAction;
		this.type = typeResponses;
	}

	public SendMessageRequest(SenderAction senderAction, String messagersa,
			String requestURL, BaseModel model, Class<?> typeResponses) {
		this.encryptRSA = true;
		this.requestURL = requestURL;
		this.baseModel = model;
		this.messageRsa = messagersa;
		this.senderAction = senderAction;
		this.type = typeResponses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	/**
	 * Ham chay ngam cua AsyncTask khi goi lenh execute
	 * 
	 * @author:
	 * @since: Description: (neu co)
	 */
	@Override
	protected BaseMessageResponses doInBackground(MediaType... params) {
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
			ObjectMapper mapper = new ObjectMapper();
			try {
				LogUtils.w("SENDREQUEST", mapper.writeValueAsString(message));
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// tao mot the hien restTemplate
			RestTemplate restTemplate = new RestTemplate();
			// Convert message sang dinh dang HTTP
			restTemplate.getMessageConverters().add(
					new StringHttpMessageConverter());

			// Su dung giao thuc https
			if (useHttps) {
				HttpClient httpClient = SSLClient.getNewHttpClient();
				restTemplate
						.setRequestFactory(new HttpComponentsClientHttpRequestFactory(
								httpClient));
			} else {
				restTemplate
						.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			}
			restTemplate.getMessageConverters().add(
					new StringHttpMessageConverter());

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
				try {
					LogUtils.i("READ RESPONSE",
							mapper.writeValueAsString(response.getBody()));
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return (BaseMessageResponses) response.getBody();

			} catch (RestClientException e) {
				BaseMessageResponses restClientError = new BaseMessageResponses();
				if (e.getMessage().equals("400 Bad Request")) {
					restClientError
							.setResponseCode(BaseMessageResponses.BAD_REQUEST);
					restClientError.setMessage("Cấu trúc gửi lỗi");
					LogUtils.d("POST MESSAGE TASK", e.getMessage());
				} else if (e.getMessage().equals("500 Internal Server Error")) {
					restClientError
							.setResponseCode(BaseMessageResponses.INTERAL_SERVER_ERROR);
					restClientError.setMessage("Không có kết nối mạng");
					LogUtils.d("POST MESSAGE TASK", e.getMessage());
				} else {
					restClientError
							.setResponseCode(BaseMessageResponses.NETWORK_TIMEOUT);
					restClientError.setMessage("Không có kết nối mạng");
					LogUtils.d("POST MESSAGE TASK", e.getMessage());
				}
				return restClientError;
			} catch (org.springframework.http.converter.HttpMessageNotReadableException e) {
				BaseMessageResponses wrongMessageError = new BaseMessageResponses();
				wrongMessageError
						.setResponseCode(BaseMessageResponses.DIFFERENCE_MESSAGE_ERROR);
				wrongMessageError
						.setMessage("Wrong message structure from server.");
				Log.d("POST MESSAGE TASK", e.getMessage());
				return wrongMessageError;
			}
		} catch (ResourceAccessException e) {
			Log.e("(POST TASK)NETWORK ERROR", e.getMessage());
			BaseMessageResponses error = new BaseMessageResponses();
			error.setResponseCode(BaseMessageResponses.NETWORK_TIMEOUT);
			return error;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	/**
	 * @author:
	 * @since: Description: (neu co)
	 */
	@Override
	protected void onPostExecute(BaseMessageResponses abstractResponse) {
		if (abstractResponse.getResponseCode() != BaseMessageResponses.SUCCESS) {
			// Response that bai
			baseModel.onReceiverError(senderAction, abstractResponse);
		} else {
			// Response thanh cong
			baseModel.onReceiverSuccess(senderAction, abstractResponse);
		}
	}
}
