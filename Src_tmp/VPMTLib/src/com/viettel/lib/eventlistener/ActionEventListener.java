/**
 * Copyright 2014 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.eventlistener;

/**
 * Mo ta muc dich cua lop (interface)
 * 
 * @author: Duchha
 * @version: 1.0
 * @since: 1.0
 */
public interface ActionEventListener {
	/**
	 * 
	*  Mo ta chuc nang cua ham
	*  @author: Duchha
	*  @param actionEvent: Su kien gi
	*  @param actionType : thuoc loai nao
	*  @param data : du lieu tra ve
	*  @return: void
	*  @throws:
	 */
	public void onActionEventListener(int actionEvent, int actionType,
			Object data);
}
