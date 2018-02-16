/*
 * @ClassName: AbstractTable.java
 * @Date: Feb 17, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.orm.sqlite;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Abstract class Table
 * 
 * @author: thuantp
 * @version: 1.0
 * @since: Feb 17, 2014
 */

public abstract class BaseAbstractRepository {

	protected DatabaseHelper dbHelper;

	/**
	 * 
	 * Constructor
	 * 
	 * @author: thuantp
	 * @Since: Feb 17, 2014
	 * @throws:
	 */
	public BaseAbstractRepository(Context context) {
		super();
		this.dbHelper = DatabaseManager.getInstance().getHelper(context);
	}

	/*
	 * abstract protected int insert(BaseAbstractDto dto);
	 * 
	 * abstract protected int update(BaseAbstractDto dto);
	 * 
	 * abstract protected int delete(BaseAbstractDto dto);
	 * 
	 * abstract protected BaseAbstractDto getById(int id);
	 * 
	 * abstract protected List<BaseAbstractDto> getAll();
	 * 	
	 * abstract protected void deleteAll();
	 */
	


	/**
	 * 
	 * releaseHelper
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: void
	 * @Since: Feb 17, 2014
	 * @throws:
	 */
	public void onDestroy() {
		OpenHelperManager.releaseHelper();
		dbHelper = null;
	}

}
