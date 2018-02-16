/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.commonobject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *  Lop anh xa Json object
 *  @author: Nhantd
 *  @version: 1.0
 *  @since: 1.0
 */
public class MapperJsonObject {
	
	public static ObjectMapper objectMapper = null;
	
	/**
	 * 
	 *  Tra ve doi tuong objectMapper
	 *  @author: Nhantd
	 *  @return
	 *  @return: ObjectMapper
	 *  @throws:
	 */
	public static ObjectMapper getInstance() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}
	
	/**
	 * 
	 *  Anh xa sang doi tuong Json
	 *  @author: Nhantd
	 *  @param obj
	 *  @return
	 *  @return: String
	 *  @throws:
	 */
	public static String convertObjectToJson(Object obj)
	{
		String result = null;
		try {
			result = getInstance().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
