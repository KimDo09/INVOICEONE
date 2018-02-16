/*
 * @ClassName: CitiesTable.java
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
import com.viettel.vpmt.template.orm.respositoryinterface.CitiesRepositoryInterface;
import com.viettel.vpmt.template.orm.sqlite.BaseAbstractRepository;
import com.viettel.vpmt.template.orm.sqlite.dtodb.Cities;

/**
 * Class CitiesTable
 * 
 * @author: thuantp
 * @version: 1.0
 * @since: Feb 17, 2014
 */

public class CitiesRepository extends BaseAbstractRepository implements CitiesRepositoryInterface  {

	private Dao<Cities, Integer> citiesDao;
	private static CitiesRepository instance; // Static instance su dung o tat ca
	// moi noi trong du an
	
	/**
	 * Mo ta chuc nang cua Constructor
	 * 
	 * @author: thuantp
	 * @Since: Feb 17, 2014
	 * @throws:
	 */
	public CitiesRepository(Context context) {
		super(context);
		try {
			citiesDao = dbHelper.getCitiesDao();
		} catch (SQLException e) {

			e.printStackTrace()
			;
		}
	}

	public static CitiesRepository getInstance(Context context) {
		if (instance == null) {
			instance = new CitiesRepository(context);
		}
		return instance;
	}
	
	
	/**
	 * 
	 * insert 1 thanh pho moi
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: int
	 * @Since: Feb 17, 2014
	 * @throws:
	 */
	public int create(Cities city) {
		try {
			return citiesDao.create(city);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * update thanh pho
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: int
	 * @Since: Feb 17, 2014
	 * @throws:
	 */
	public int update(Cities city) {
		try {
			return citiesDao.update(city);
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
	 * @author: thuantp
	 * @param:
	 * @return: int
	 * @Since: Feb 17, 2014
	 * @throws:
	 */
	public int delete(Cities city) {
		try {
			return citiesDao.delete(city);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	

	/** 
	  * lay theo id
	  * @author:thuantp
	  *  @since:Feb 17, 2014
	  */
	@Override
	public Cities getById(int id) {
		
		try {
			return citiesDao.queryForId(id);
		} catch (Exception e) {
			
		}
		return null;
	}



	/** 
	  * lay tat ca thanh pho
	  * @author:thuantp
	  *  @since:Feb 17, 2014
	  */
	public List<Cities> getAll() {
		try {
			return citiesDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 
	  * xoa tat ca thanh pho
	  * @author:thuantp
	  *  @since:Feb 18, 2014
	  */
	@Override
	public void deleteAll() {
		try {
			List<Cities> list = citiesDao.queryForAll();
			citiesDao.delete(list);
			
		} catch (SQLException e) {
			// TODO: handle exception
		}
	}

	


}
