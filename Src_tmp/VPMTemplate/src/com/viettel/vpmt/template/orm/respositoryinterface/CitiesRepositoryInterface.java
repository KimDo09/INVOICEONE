/*
 * @ClassName: CitiesRepositoryInterface.java
 * @Date: Feb 17, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.orm.respositoryinterface;

import java.util.List;

import com.viettel.vpmt.template.orm.sqlite.dtodb.Cities;

/**
 * CitiesRepository Interface
 * 
 * @author: thuantp
 * @version: 1.0
 * @since: Feb 17, 2014
 */

public interface CitiesRepositoryInterface {

	// insert 1 thanh pho moi
	public int create(Cities city);

	// update 1 thanh pho moi
	public int update(Cities city);

	// xoa 1 thanh pho moi
	public int delete(Cities city);

	// lay tat ca thanh pho
	public List<Cities> getAll();
    
	// lay theo id
	public Cities getById(int id);
	
	//xoa tat ca
	public void deleteAll();
	
	
}
