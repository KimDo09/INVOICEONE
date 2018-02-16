/*
 * @ClassName: AbstractTable.java
 * @Date: Feb 17, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.orm.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.viettel.vpmt.template.constant.Constants;

/**
 * DatabaseManager
 * 
 * @author: thuantp
 * @version: 1.0
 * @since: Feb 17, 2014
 */
public class DatabaseManager {

	private static  DatabaseManager instance;
    private DatabaseHelper databaseHelper = null;
   
    
	/**
	 * 
	 * Khoi tao DatabaseManager 
	 * 
	 * @author: duchha
	 * @param:
	 * @return: CitiesFragment
	 * @Since: Feb 12, 2014
	 * @throws:
	 */
	public static DatabaseManager getInstance() {
		if (instance == null) {
			instance = new DatabaseManager();
		}
		return instance;
	}
   
    /**
     * 
    *  //gets a helper once one is created ensures it doesnt create a new one
    *  @author: thuantp
    *  @param:
    *  @return: DatabaseHelper
    *  @Since: Feb 20, 2014
    *  @throws:
     */
    public DatabaseHelper getHelper(Context context)
    {
        if (databaseHelper == null) {
            databaseHelper =DatabaseHelper.getInstance(context);
                   // OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return databaseHelper;
    }
    /**
     * 
    *  kiem tra DB da ton tai chua
    *  @author: thuantp
    *  @param:
    *  @return: boolean
    *  @Since: Feb 20, 2014
    *  @throws:
     */
    private boolean checkDatabase(){
    	 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = Constants.DATABASE_PATH + Constants.DATABASE_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){ 
    	}
 
    	if(checkDB != null){
    		checkDB.close();
    	}
 
    	return checkDB != null ? true : false;
    }
    
    /**
     * 
    *  //releases the helper once usages has ended
    *  @author: thuantp
    *  @param:
    *  @return: void
    *  @Since: Feb 20, 2014
    *  @throws:
     */
    public void releaseHelper()
    {
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

}
