/*
 * @ClassName: ProductsTable.java
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
import com.viettel.vpmt.template.orm.respositoryinterface.ProductsRepositoryInterface;
import com.viettel.vpmt.template.orm.sqlite.BaseAbstractRepository;
import com.viettel.vpmt.template.orm.sqlite.dtodb.Products;

/**
 * Class ProductsTable
 * 
 * @author: vuonghv2
 * @version: 1.0
 * @since: Mar 25, 2014
 */

public class ProductsRepository extends BaseAbstractRepository implements ProductsRepositoryInterface {

	private Dao<Products, Integer> productsDao;
	private static ProductsRepository instance; // Static instance su dung o tat
												// ca

	// moi noi trong du an

	/**
	 * Mo ta chuc nang cua Constructor
	 * 
	 * @author: vuonghv2
	 * @Since: Mar 25, 2014
	 * @throws:
	 */
	public ProductsRepository(Context context) {
		super(context);
		try {
			productsDao = dbHelper.getProductsDao();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static ProductsRepository getInstance(Context context) {
		if (instance == null) {
			instance = new ProductsRepository(context);
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
	public int create(Products product) {
		try {
			if (getByServerId(product) == null) {
				return productsDao.create(product);
			}
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
	public int updateServerId(Products product) {
		try {
			String query = "UPDATE products set server_id = " + product.getServerId() + " where client_id = "
					+ product.getClientId();
			productsDao.queryRaw(query, productsDao.getRawRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return product.getServerId();
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
	public int reset() {
		try {
			String query = "UPDATE histories set flag = 1";
			productsDao.queryRaw(query, productsDao.getRawRowMapper());
		} catch (SQLException e) {
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
	public int updateByServerId(Products product) {
		try {
			String query = "UPDATE product ";
			query += "SET name = '" + product.getName() + "'";
			query += ", amount = '" + product.getAmount() + "'";
			query += ", price = '" + product.getPrice() + "'";
			query += "WHERE server_id = " + product.getServerId();

			productsDao.queryRaw(query, productsDao.getRawRowMapper());
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
	public int update(Products product) {
		try {
			return productsDao.update(product);
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
	public int deleteByServerId(Products product) {
		try {
			String query = "Delete from products ";
			query += "WHERE server_id = " + product.getServerId();
			productsDao.queryRaw(query, productsDao.getRawRowMapper());
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
	public int delete(Products product) {
		try {
			return productsDao.delete(product);
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
	public Products getById(int id) {

		try {
			return productsDao.queryForId(id);
		} catch (Exception e) {

		}
		return null;
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
	public Products getByServerId(Products product) {
		try {
			String query = "select * from products ";
			query += "WHERE server_id = " + product.getServerId();
			return productsDao.queryRaw(query, productsDao.getRawRowMapper()).getFirstResult();
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * lay tat ca Products
	 * 
	 * @author:vuonghv2
	 * @since:Mar 25, 2014
	 */
	public List<Products> getAll() {
		try {
			return productsDao.queryForAll();
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
			List<Products> list = productsDao.queryForAll();
			productsDao.delete(list);

		} catch (SQLException e) {
			// TODO: handle exception
		}
	}

}
