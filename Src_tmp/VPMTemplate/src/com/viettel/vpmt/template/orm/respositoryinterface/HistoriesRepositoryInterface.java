/*
 * @ClassName: CitiesRepositoryInterface.java
 * @Date: Feb 17, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.orm.respositoryinterface;

import java.util.List;

import com.viettel.vpmt.template.orm.sqlite.dtodb.Histories;
import com.viettel.vpmt.template.orm.sqlite.dtodb.Products;

/**
 * HistoriesRepositoryInterface
 * 
 * @author: vuonghv2
 * @version: 1.0
 * @since: Mar 25, 2014
 */

public interface HistoriesRepositoryInterface {

	// insert 1 history moi
	public int create(Histories history);

	// update 1 history moi
	public int update(Histories history);

	// xoa 1 history moi
	public int delete(Histories history);

	// lay tat ca history
	public List<Histories> getAll();

	// lay theo id
	public Histories getById(int id);

	// xoa tat ca
	public void deleteAll();

}
