/*
 * @ClassName: BaseFunction.java
 * @Date: Feb 11, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.base;

import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;

import com.viettel.lib.eventlistener.ActionEventListener;
import com.viettel.lib.eventlistener.OnEventControlListener;
import com.viettel.lib.eventlistener.ReceiverActionListener;

/**
 * Mo ta Types
 * 
 * @author: duchha
 * @version: 1.0
 * @since: Feb 11, 2014
 */

public interface BaseFunction extends OnClickListener, ReceiverActionListener,OnItemClickListener,
		OnEventControlListener, ActionEventListener,DialogInterface.OnCancelListener {
}
