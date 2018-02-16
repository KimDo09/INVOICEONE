/*
 * @ClassName: ProfileApp.java
 * @Date: Apr 10, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.dtoView;

import java.io.Serializable;

/**
 * ProfileApp
 * 
 * @author: vuonghv2
 * @version: 1.0
 * @since: Apr 10, 2014
 */

@SuppressWarnings("serial")
public class ProfileApp implements Serializable {
	private String maThuNganVien;
	private String tenThuNganVien;
	private String userName;
	private String password;

	public String getMaThuNganVien() {
		return maThuNganVien;
	}

	public void setMaThuNganVien(String maThuNganVien) {
		this.maThuNganVien = maThuNganVien;
	}

	public String getTenThuNganVien() {
		return tenThuNganVien;
	}

	public void setTenThuNganVien(String tenThuNganVien) {
		this.tenThuNganVien = tenThuNganVien;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
