/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.eventlistener;

import com.viettel.lib.commonobject.ReceiverAction;

/**
 *  Interface nhan action tra ve cho View tu UserPresenter
 *  @author: Nhantd
 *  @version: 1.0
 *  @since: 1.0
 */
public interface ReceiverActionListener {
	
	public void onHandlePresenterToViewSuccess(ReceiverAction objectRecevier);
	
	public void onHandlePresenterToViewError(ReceiverAction objectRecevier);
}
