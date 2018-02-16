/*
 * @ClassName: CitiesRepositoryInterface.java
 * @Date: Feb 17, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.orm.respositoryinterface;

import java.util.List;

import com.viettel.vpmt.template.orm.sqlite.dtodb.Products;

/**
 * ProductsRepositoryInterface
 * 
 * @author: vuonghv2
 * @version: 1.0
 * @since: Mar 25, 2014
 */

public interface ProductsRepositoryInterface {

	// insert 1 product moi
	public int create(Products product);

	// update 1 product moi
	public int update(Products product);

	// xoa 1 product moi
	public int delete(Products product);

	// lay tat ca product
	public List<Products> getAll();

	// lay theo id
	public Products getById(int id);

	// xoa tat ca
	public void deleteAll();

}
