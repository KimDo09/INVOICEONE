/**
 * Copyright 2014 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.orm.sqlite.dtodb;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

/**
 *  Lop Cities
 *  @author: ThuanTP
 *  @version: 1.0
 *  @since: 1.0
 */
public class Cities extends BaseAbstractDto {
      

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(dataType = DataType.LONG_STRING)
	private String  cityName;
	
	@ForeignCollectionField(eager = false)
    ForeignCollection<Employee> employees;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public ForeignCollection<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(ForeignCollection<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return cityName ;
	}
	
}
