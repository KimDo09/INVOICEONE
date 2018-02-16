/**
 * Copyright 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.utils.location;

import android.location.LocationListener;

/**
 *  lang nghe cac su kien dinh vi co time out (interface)
 *  @author: AnhND
 *  @version: 1.0
 *  @since: 1.0
 */
public interface LocatingListener extends LocationListener{
	public void onTimeout (Locating locating);
}