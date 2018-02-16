/*
 * @ClassName: DatabaseHelper.java
 * @Date: Feb 17, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.orm.sqlite;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.viettel.vpmt.template.constant.Constants;
import com.viettel.vpmt.template.orm.sqlite.dtodb.Cities;
import com.viettel.vpmt.template.orm.sqlite.dtodb.Employee;
import com.viettel.vpmt.template.orm.sqlite.dtodb.Histories;
import com.viettel.vpmt.template.orm.sqlite.dtodb.Products;

/**
 * Class DatabaseHelper
 * 
 * @author: thuantp
 * @version: 1.0
 * @since: Feb 17, 2014
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static DatabaseHelper sInstanceHelper;
	// count how many time helper be called
	private static final AtomicInteger usageCounter = new AtomicInteger(0);

	// the DAO object we use to access the table
	private Dao<Cities, Integer> citiesDao = null;
	private Dao<Employee, Integer> employeeDao = null;
	private Dao<Products, Integer> productDao = null;
	private Dao<Histories, Integer> historyDao = null;

	/**
	 * Constructor - tham so gom context, ten database, CursorFactory, db
	 * version
	 * 
	 * @author: thuantp
	 * @Since: Feb 17, 2014
	 * @throws:
	 */
	public DatabaseHelper(Context context) {

		super(context, Constants.DATABASE_NAME, null,
				Constants.DATABASE_VERSION);

	}

	/**
	 * 
	 * getInstance cua DatabaseHelper
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: DatabaseHelper
	 * @Since: Feb 17, 2014
	 * @throws:
	 */
	public static DatabaseHelper getInstance(Context context) {

		// Use the application context, which will ensure that you
		// don't accidentally leak an Activity's context.
		// See this article for more information: http://bit.ly/6LRzfx
		if (sInstanceHelper == null) {
			sInstanceHelper = new DatabaseHelper(context);
		}
		return sInstanceHelper;
	}

	/**
	 * Khoi tao db
	 * 
	 * @author:thuantp
	 * @since:Feb 17, 2014
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, Cities.class);
			TableUtils.createTable(connectionSource, Employee.class);
			TableUtils.createTable(connectionSource, Products.class);
			TableUtils.createTable(connectionSource, Histories.class);

		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			e.printStackTrace();
			throw new RuntimeException(e);

		}

	}

	/**
	 * upgrade db khi db version thay doi
	 * 
	 * @author:thuantp
	 * @since:Feb 17, 2014
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, Cities.class, true);
			TableUtils.dropTable(connectionSource, Employee.class, true);
			TableUtils.dropTable(connectionSource, Products.class, true);
			TableUtils.dropTable(connectionSource, Histories.class, true);

			onCreate(db, connectionSource);

		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}

	}

	/**
	 * Close the database connections and clear any cached DAOs. For each call
	 * to {@link #getHelper(Context)}, there should be 1 and only 1 call to this
	 * method. If there were 3 calls to {@link #getHelper(Context)} then on the
	 * 3rd call to this method, the helper and the underlying database
	 * connections will be closed.
	 */
	@Override
	public void close() {
		Log.i("usageCounter", usageCounter.toString());
		if (usageCounter.decrementAndGet() == 0) {

			super.close();
			sInstanceHelper = null;
		}
	}

	/**
	 * 
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao
	 * for Employee
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: Dao<Employee,Integer>
	 * @throws SQLException
	 * @Since: Feb 17, 2014
	 * @throws:
	 */
	public Dao<Employee, Integer> getEmployeeDao() throws SQLException {
		if (employeeDao == null) {
			employeeDao = getDao(Employee.class);
		}

		return employeeDao;
	}

	/**
	 * 
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao
	 * for Cities
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: Dao<Cities,Integer>
	 * @throws SQLException
	 * @Since: Feb 17, 2014
	 * @throws:
	 */
	public Dao<Cities, Integer> getCitiesDao() throws SQLException {
		if (citiesDao == null) {
			citiesDao = getDao(Cities.class);
		}
		return citiesDao;
	}

	/**
	 * 
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao
	 * for Cities
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: Dao<Products,Integer>
	 * @throws SQLException
	 * @Since: Mar 25, 2014
	 * @throws:
	 */
	public Dao<Products, Integer> getProductsDao() throws SQLException {
		if (productDao == null) {
			productDao = getDao(Products.class);
		}
		return productDao;
	}

	/**
	 * 
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao
	 * for Cities
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: Dao<Histories,Integer>
	 * @throws SQLException
	 * @Since: Mar 25, 2014
	 * @throws:
	 */
	public Dao<Histories, Integer> getHistoriesDao() throws SQLException {
		if (historyDao == null) {
			historyDao = getDao(Histories.class);
		}
		return historyDao;
	}
}
