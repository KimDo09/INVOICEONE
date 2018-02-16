/*
 * @ClassName: CommonInfoDTO.java
 * @Date: Jun 15, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.common;

import com.viettel.vpmt.template.log.LogUtilsGeneral;

/**
 * Mo ta Types
 * 
 * @author: Administrator
 * @version: 1.0
 * @since: Jun 15, 2014
 */

public class CommonInfoDTO {
	private volatile static CommonInfoDTO uniqueInstance;
	private String token;

	/**
	 * 
	 * 
	 * getInstance CommonInfoDTO
	 * 
	 * @author: vuonghv2
	 * @return: CommonInfoDTO
	 * @throws: @param tag
	 * @throws: @param msg
	 * @since: June 04, 2014
	 */
	public static CommonInfoDTO getInstance() {
		if (uniqueInstance == null) {
			synchronized (LogUtilsGeneral.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new CommonInfoDTO();
				}
			}
		}
		return uniqueInstance;
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

}
