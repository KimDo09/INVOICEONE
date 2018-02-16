/*
 * @ClassName: AbstractViewDTO.java
 * @Date: Jan 9, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.dtoView;

/**
 *  Abstract class dtoView
 *  @author: nhantd
 *  @version: 1.0
 *  @since: Jan 9, 2014
 */

public class AbstractViewDTO {
	
	public int tag; // tag xac dinh dtoView;
	public String name; //name xac dinh dtoView;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
