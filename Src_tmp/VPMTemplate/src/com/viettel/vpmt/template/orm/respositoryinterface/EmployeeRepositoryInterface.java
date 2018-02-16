/*
 * @ClassName: EmployeeRepositoryInterface.java
 * @Date: Feb 17, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.orm.respositoryinterface;

import java.sql.SQLException;
import java.util.List;

import com.viettel.vpmt.template.orm.sqlite.dtodb.Employee;

/**
 * EmployeeRepository Interface
 * 
 * @author: thuantp
 * @version: 1.0
 * @since: Feb 17, 2014
 */

public interface EmployeeRepositoryInterface {
	// insert 1 nhan vien moi
	public int create(Employee city);

	// update 1 nhan vien moi
	public int update(Employee city);

	// xoa 1 nhan vien moi
	public int delete(Employee city);

	// lay tat ca nhan vien
	public List<Employee> getAll();

	// lay theo id
	public Employee getById(int id);

	// lay theo name
	public List<Employee> getByName(String name) throws SQLException;
	// lay theo name
		public List<Employee> getByNameAndCityId(String name,int cityId) throws SQLException;
}
