/**
 * Copyright 2014 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * lop Store SharePreferences luu tru cac gia tri can thiet vao SharePreferences
 * 
 * @author: Duchha
 * @version: 1.0
 * @since: 1.0
 */
public class StoreSharePreferencesUtils {

	/**
	 * 
	 * @decription:
	 * @author: Truong.Le
	 * @datetime: Nov 14, 2013 11:21:53 AM
	 * @param
	 */
	Context mContext;
	SharedPreferences sharedPreferences;
	public static final String SHARE_PREFERENCE = "sharePreference";

	public StoreSharePreferencesUtils(Context context) {
		this.mContext = context;
	}

	/**
	 * load gia tri Boolean value
	 * 
	 * @author: duchha
	 * @param keyValue
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public boolean loadBooleandSavedPreferences(String keyValue) {
		sharedPreferences=mContext.getSharedPreferences(SHARE_PREFERENCE, mContext.MODE_PRIVATE);
		boolean value = sharedPreferences.getBoolean(keyValue, false);
		return value;
	}

	/**
	 * load gia tri String value
	 * 
	 * @author: duchha
	 * @param keyValue
	 * @return
	 * @return: String
	 * @throws:
	 */
	public String loadStringSavedPreferences(String keyValue) {
		sharedPreferences=mContext.getSharedPreferences(SHARE_PREFERENCE, mContext.MODE_PRIVATE);
		String value = sharedPreferences.getString(keyValue, "");
		return value;
	}

	/**
	 * load gia tri int value
	 * 
	 * @author: duchha
	 * @param keyValue
	 * @return
	 * @return: int
	 * @throws:
	 */
	public int loadIntegerSavedPreferences(String keyValue) {
		sharedPreferences=mContext.getSharedPreferences(SHARE_PREFERENCE, mContext.MODE_PRIVATE);
		int value = sharedPreferences.getInt(keyValue, 0);
		return value;
	}

	/**
	 * load gia tri float value
	 * 
	 * @author: duchha
	 * @param keyValue
	 * @return
	 * @return: float
	 * @throws:
	 */
	public float loadFloatSavedPreferences(String keyValue) {
		sharedPreferences=mContext.getSharedPreferences(SHARE_PREFERENCE, mContext.MODE_PRIVATE);
		float value = sharedPreferences.getFloat(keyValue, 0);
		return value;
	}

	/**
	 * save gia tri boolean value
	 * 
	 * @author: duchha
	 * @param key
	 * @param value
	 * @return: void
	 * @throws:
	 */
	public void saveBooleadPreferences(String key, boolean value) {
		sharedPreferences=mContext.getSharedPreferences(SHARE_PREFERENCE, mContext.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * save gia tri String value
	 * 
	 * @author: duchha
	 * @param key
	 * @param value
	 * @return: void
	 * @throws:
	 */
	public void saveStringPreferences(String key, String value) {
		sharedPreferences=mContext.getSharedPreferences(SHARE_PREFERENCE, mContext.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * save gia tri Float value
	 * 
	 * @author: duchha
	 * @param key
	 * @param value
	 * @return: void
	 * @throws:
	 */
	public void saveFloatPreferences(String key, float value) {
		sharedPreferences=mContext.getSharedPreferences(SHARE_PREFERENCE, mContext.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	/**
	 * save gia tri int value
	 * 
	 * @author: duchha
	 * @param key
	 * @param value
	 * @return: void
	 * @throws:
	 */
	public void saveIntPreferences(String key, int value) {
		sharedPreferences=mContext.getSharedPreferences(SHARE_PREFERENCE, mContext.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

}
