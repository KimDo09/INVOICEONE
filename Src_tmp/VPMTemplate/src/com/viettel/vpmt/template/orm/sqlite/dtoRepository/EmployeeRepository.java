/*
 * @ClassName: EmployeeRepository.java
 * @Date: Feb 17, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.orm.sqlite.dtoRepository;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.viettel.vpmt.template.orm.respositoryinterface.EmployeeRepositoryInterface;
import com.viettel.vpmt.template.orm.sqlite.BaseAbstractRepository;
import com.viettel.vpmt.template.orm.sqlite.dtodb.Employee;

/**
 * Mo ta Types
 * 
 * @author: thuantp
 * @version: 1.0
 * @since: Feb 17, 2014
 */

public class EmployeeRepository extends BaseAbstractRepository implements
		EmployeeRepositoryInterface {

	public static EmployeeRepository instance;
	private Dao<Employee, Integer> employeeDao;

	/**
	 * 
	 * Ham lay EmployeeRepository de su dung
	 * 
	 * @author: Thuantp
	 * @return: UserPresenter
	 * @Since: Jan 9, 2014
	 * @throws:
	 */
	public static EmployeeRepository getInstance(Context context) {
		if (instance == null) {
			instance = new EmployeeRepository(context);
		}
		return instance;
	}


	/**
	 * Constructor
	 * 
	 * @author: thuantp
	 * @Since: Feb 17, 2014
	 * @throws:
	 */
	public EmployeeRepository(Context context) {
		super(context);
		try {
			employeeDao = dbHelper.getEmployeeDao();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * insert 1 nhan vien moi
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: int
	 * @Since: Feb 17, 2014
	 * @throws:
	 */
	public int create(Employee employee) {
		try {
			return employeeDao.create(employee);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * update nhan vien
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: int
	 * @Since: Feb 17, 2014
	 * @throws:
	 */
	public int update(Employee employee) {
		try {
			return employeeDao.update(employee);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * xoa'
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: int
	 * @Since: Feb 17, 2014
	 * @throws:
	 */
	public int delete(Employee employee) {
		try {
			return employeeDao.delete(employee);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * lay tat ca nhan vien
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: List<Employee>
	 * @Since: Feb 17, 2014
	 * @throws:
	 */
	public List<Employee> getAll() {
		try {
			return employeeDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * lay theo id
	 * 
	 * @author:thuantp
	 * @since:Feb 17, 2014
	 */
	@Override
	public Employee getById(int id) {

		try {
			return employeeDao.queryForId(id);
		} catch (Exception e) {

		}
		return null;
	}

	

	/**
	 * noi dung sua
	 * 
	 * @author:thuantp
	 * @throws SQLException
	 * @since:Feb 19, 2014
	 */
	@Override
	public List<Employee> getByName(String name) throws SQLException {
		String query = "SELECT * FROM employee WHERE  name LIKE '%" + name
				+ "%'";

		GenericRawResults<Employee> rawResults;

		rawResults = employeeDao.queryRaw(query, employeeDao.getRawRowMapper());
		Log.i("SQL STATAMENT",query);
		List<Employee> list = rawResults.getResults();
     
		rawResults.close();
		return list;
	}

	/**
	 * noi dung sua
	 * 
	 * @author:thuantp
	 * @since:Feb 19, 2014
	 */
	@Override
	public List<Employee> getByNameAndCityId(String name, int cityId)
			throws SQLException {
		QueryBuilder<Employee, Integer> queryBuilder = employeeDao
				.queryBuilder();
		Where<Employee, Integer> where = queryBuilder.where();
		where.and(where.eq("cityId", cityId),
				where.like("name", "%" + name + "%"));
		PreparedQuery<Employee> preparedQuery = queryBuilder.prepare();
		Log.i("SQL STATAMENT", preparedQuery.getStatement());

		List<Employee> list = employeeDao.query(preparedQuery);
		return list;
	}

}
