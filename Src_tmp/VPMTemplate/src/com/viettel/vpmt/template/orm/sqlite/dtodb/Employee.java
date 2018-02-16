/**
 * Copyright 2014 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.orm.sqlite.dtodb;

import com.j256.ormlite.field.DatabaseField;

/**
 *  Lop Employee
 *  @author: ThuanTP
 *  @version: 1.0
 *  @since: 1.0
 */
public class Employee extends BaseAbstractDto {

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField(canBeNull = false, foreign = true,foreignAutoRefresh = true,columnName= "cityId")
	private Cities city;

	public Employee() {
		super();
	}

	public Employee(String name, Cities city) {
		super();
		this.name = name;
		this.city = city;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Cities getCity() {
		return city;
	}

	public void setCity(Cities city) {
		this.city = city;
	}
}
