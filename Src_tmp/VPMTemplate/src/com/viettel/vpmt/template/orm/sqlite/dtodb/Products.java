/**
 * Copyright 2014 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.orm.sqlite.dtodb;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

/**
 * Lop Products
 * 
 * @author: vuonghv2
 * @version: 1.0
 * @since: 1.0
 */
public class Products extends BaseAbstractDto {

	@DatabaseField(generatedId = true)
	private int client_id;
	@DatabaseField(canBeNull = true)
	private int server_id;
	@DatabaseField(dataType = DataType.LONG_STRING)
	private String name;
	@DatabaseField(dataType = DataType.INTEGER)
	private int amount;
	@DatabaseField(dataType = DataType.INTEGER)
	private int price;

	private int request_type = -1;// 0: DELETE; 1: INSERT;2:UPDDATE\
	private String className = "Products";

	/**
	 * @return class name
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @return the id
	 */
	public int getType() {
		return request_type;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setType(int action) {
		this.request_type = action;
	}

	/**
	 * @return the id
	 */
	public int getServerId() {
		return server_id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setServerId(int server_id) {
		this.server_id = server_id;
	}

	/**
	 * @return the id
	 */
	public int getClientId() {
		return client_id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setClientId(int client_id) {
		this.client_id = client_id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

}
