/**
 * Copyright 2014 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.orm.sqlite.dtodb;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

/**
 * Lop Histories
 * 
 * @author: vuonghv2
 * @version: 1.0
 * @since: 1.0
 */
public class Histories extends BaseAbstractDto {

	@DatabaseField(generatedId = true, dataType = DataType.INTEGER)
	private int version;

	@DatabaseField(dataType = DataType.LONG_STRING)
	private String type;

	@DatabaseField(dataType = DataType.LONG_STRING)
	private String table_name;

	@DatabaseField(dataType = DataType.INTEGER)
	private int table_row_client_id;

	@DatabaseField(canBeNull = true, dataType = DataType.INTEGER)
	private int table_row_server_id;

	@DatabaseField(dataType = DataType.BOOLEAN)
	private boolean flag;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public int getTable_row_client_id() {
		return table_row_client_id;
	}

	public void setTable_row_client_id(int table_row_client_id) {
		this.table_row_client_id = table_row_client_id;
	}

	public int getTable_row_server_id() {
		return table_row_server_id;
	}

	public void setTable_row_server_id(int table_row_server_id) {
		this.table_row_server_id = table_row_server_id;
	}

	/**
	 * @return the flag
	 */
	public boolean getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
