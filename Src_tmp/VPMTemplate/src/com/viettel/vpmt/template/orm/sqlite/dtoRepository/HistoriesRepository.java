/*
 * @ClassName: HistoriesTable.java
 * @Date: Feb 17, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.orm.sqlite.dtoRepository;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.viettel.vpmt.template.orm.respositoryinterface.HistoriesRepositoryInterface;
import com.viettel.vpmt.template.orm.sqlite.BaseAbstractRepository;
import com.viettel.vpmt.template.orm.sqlite.dtodb.Histories;

/**
 * Class HistoriesRepository
 * 
 * @author: vuonghv2
 * @version: 1.0
 * @since: Mar 25, 2014
 */

public class HistoriesRepository extends BaseAbstractRepository implements
		HistoriesRepositoryInterface {

	private Dao<Histories, Integer> historiesDao;
	private static HistoriesRepository instance; // Static instance su dung o tat
												// ca

	// moi noi trong du an

	/**
	 * Mo ta chuc nang cua Constructor
	 * 
	 * @author: vuonghv2
	 * @Since: Mar 25, 2014
	 * @throws:
	 */
	public HistoriesRepository(Context context) {
		super(context);
		try {
			historiesDao = dbHelper.getHistoriesDao();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static HistoriesRepository getInstance(Context context) {
		if (instance == null) {
			instance = new HistoriesRepository(context);
		}
		return instance;
	}

	/**
	 * 
	 * insert 1 product moi
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: int
	 * @Since: Feb 17, 2014
	 * @throws:
	 */
	public int create(Histories product) {
		try {
			return historiesDao.create(product);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * update product
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: int
	 * @Since: Mar 25, 2014
	 * @throws:
	 */
	public int update(Histories product) {
		try {
			return historiesDao.update(product);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * xoa'
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: int
	 * @Since: Feb 17, 2014
	 * @throws:
	 */
	public int delete(Histories product) {
		try {
			return historiesDao.delete(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * Lấy Product theo id
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: int
	 * @Since: Feb 17, 2014
	 * @throws:
	 */
	@Override
	public Histories getById(int id) {

		try {
			return historiesDao.queryForId(id);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * lay tat ca Histories
	 * 
	 * @author:vuonghv2
	 * @since:Mar 25, 2014
	 */
	public List<Histories> getAll() {
		try {
			return historiesDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * xoa tat ca thanh pho
	 * 
	 * @author:vuonghv2
	 * @since:Mar 25, 2014
	 */
	@Override
	public void deleteAll() {
		try {
			List<Histories> list = historiesDao.queryForAll();
			historiesDao.delete(list);

		} catch (SQLException e) {
			// TODO: handle exception
		}
	}

}
