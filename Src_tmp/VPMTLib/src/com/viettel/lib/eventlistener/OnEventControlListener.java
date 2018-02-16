/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.eventlistener;

import android.view.View;

/**
 * 
 * lang nghe su kien event
 * 
 * @author duchha
 * @OnEventControlListener
 * @since: 4 Oct 2013
 */
public interface OnEventControlListener {
	public static final int ACTION_FILTER = 1;
	public static final int ACTION_SEARCH = 2;
	public static final int ACTION_FINISH_DOWNLOAD = 3;
	public static final int ACTION_HIDE_EDITTEXT_SEARCHS = 999999999;

	void onEvent(int eventType, View control, Object data);

}
