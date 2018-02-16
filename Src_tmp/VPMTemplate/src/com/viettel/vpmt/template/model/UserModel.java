/*
 * @ClassName: UserModel.java
 * @Date: Jan 7, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.viettel.lib.commonobject.BaseModel;
import com.viettel.lib.commonobject.MapperJsonObject;
import com.viettel.lib.commonobject.ReceiverAction;
import com.viettel.lib.commonobject.SenderAction;
import com.viettel.lib.webservice.json.BaseMessageRequest;
import com.viettel.lib.webservice.json.BaseMessageResponses;
import com.viettel.vpmt.template.base.BaseActivity;
import com.viettel.vpmt.template.base.BaseFragment;
import com.viettel.vpmt.template.common.encrypt.rsa.RSAEncryptUtil;
import com.viettel.vpmt.template.constant.ActionConstants;
import com.viettel.vpmt.template.orm.sqlite.dtoRepository.CitiesRepository;
import com.viettel.vpmt.template.orm.sqlite.dtoRepository.EmployeeRepository;
import com.viettel.vpmt.template.orm.sqlite.dtoRepository.ProductsRepository;
import com.viettel.vpmt.template.orm.sqlite.dtodb.Cities;
import com.viettel.vpmt.template.orm.sqlite.dtodb.Employee;
import com.viettel.vpmt.template.orm.sqlite.dtodb.Product;
import com.viettel.vpmt.template.orm.sqlite.dtodb.Products;
import com.viettel.vpmt.template.presenter.UserPresenter;

/**
 * Mo ta Types
 * 
 * @author: duchha
 * @version: 1.0
 * @since: Jan 7, 2014
 */

public class UserModel extends BaseModel {

	public static UserModel instance; // Static instance su dung o tat ca
										// moi noi trong du an

	protected UserModel() {
	}

	/**
	 * 
	 * Ham lay UserModel de su dung
	 * 
	 * @author: nhantd
	 * @return: UserModel
	 * @Since: Jan 9, 2014
	 * @throws:
	 */
	public static UserModel getInstance() {
		if (instance == null) {
			instance = new UserModel();
		}
		return instance;
	}

	/**
	 * 
	 * Ham xu ly request tu View den service
	 * 
	 * @author: nhantd
	 * @return: void
	 * @Since: Jan 10, 2014
	 * @throws:
	 */
	public void excecute(SenderAction senderAction) {
		switch (senderAction.action) {
		case ActionConstants.ACTION_LOGIN_HTTP:
			requestService(senderAction, false);
			break;
		case ActionConstants.ACTION_LOGIN_HTTPS:
			requestService(senderAction, true);
			break;
		case ActionConstants.ACTION_REQUEST_DATA_FROM_SERVER:
			requestService(senderAction, true);
			break;
		case ActionConstants.ACTION_POST_DATA_TO_SERVER:
			requestService(senderAction, true);
			break;
		case ActionConstants.ACTION_LOGOUT:
			break;
		case ActionConstants.ACTION_CHECK_LOGIN:
			requestService(senderAction, true);
			break;
		case ActionConstants.ACTION_GET_LIST_CUSTOMER:
			requestService(senderAction, true);
			break;
		case ActionConstants.ACTION_GOTO_DETAIL_CUSTOMER:
			requestService(senderAction, true);
			break;

		default:
			break;
		}
	}

	/**
	 * 
	 * xu ly request tu view sang database
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: void
	 * @Since: Feb 18, 2014
	 * @throws:
	 */

	public void executeToDatabase(SenderAction senderAction) {
		switch (senderAction.action) {
		case ActionConstants.ACTION_GET_ALL_CITIES:
			getAllCitiesToView(senderAction);
			break;
		case ActionConstants.ACTION_ADD_NEW_CITY:
			addNewCity(senderAction);
			break;
		case ActionConstants.ACTION_DELETE_ALL_CITY:
			deleteAllCities(senderAction);
			break;
		case ActionConstants.ACTION_GET_ALL_EMPLOYEES:
			getAllEmployeeToView(senderAction);
			break;
		case ActionConstants.ACTION_ADD_NEW_EMPLOYEE:
			addNewEmployee(senderAction);
			break;
		case ActionConstants.ACTION_SEARCH_EMPLOYEE_BY_CITY:
			searchEmployeeByCity(senderAction);
			break;
		case ActionConstants.ACTION_SEARCH_EMPLOYEE_BY_NAME:
			searchEmployeeByName(senderAction);
			break;
		case ActionConstants.ACTION_SEARCH_EMPLOYEE_BY_NAME_AND_CITYID:
			searchEmployeeByNameAndCityId(senderAction);
			break;

		default:
			break;
		}

	}

	public Product converToServerProduct(Products products) {
		if (products == null)
			return null;
		Product p = new Product();
		p.setName(products.getName());
		p.setPrice(products.getPrice());
		p.setServerId(products.getServerId());
		p.setType(products.getType());
		p.setClientId(products.getClientId());
		return p;
	}

	public Products converToDbProduct(Product product) {
		if (product == null)
			return null;
		Products p = new Products();
		p.setName(product.getName());
		p.setPrice(product.getPrice());
		p.setServerId(product.getServerId());
		p.setType(product.getType());
		p.setClientId(product.getClientId());
		return p;
	}

	/**
	 * Lay tat ca san pham
	 * 
	 * @author: vuonghv2
	 * @param:
	 * @return: void
	 * @Since: Mar 26, 2014
	 * @throws:
	 */
	private void getAllProduct(SenderAction senderAction) {
		// TODO Auto-generated method stub
		BaseActivity base = (BaseActivity) senderAction.sender;
		try {

			List<Products> lst = ProductsRepository.getInstance(base).getAll();
			this.onReceiverSuccess(senderAction, lst);
		} catch (Exception e) {
			excuteError(senderAction, e);
		}
	}

	/**
	 * 
	 * searchEmployeeBy Name and City id
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: void
	 * @Since: Feb 19, 2014
	 * @throws:
	 */
	public void searchEmployeeByNameAndCityId(SenderAction senderAction) {
		BaseFragment base = (BaseFragment) senderAction.sender;
		try {
			String name = ((Bundle) senderAction.getBundle()).getString("name");
			int cityId = ((Bundle) senderAction.getBundle()).getInt("city_id");
			List<Employee> lst = EmployeeRepository.getInstance(
					base.getActivity()).getByNameAndCityId(name, cityId);
			senderAction.bundle = lst;
			onReceiverSuccess(senderAction, lst);
		} catch (Exception e) {
			excuteError(senderAction, e);
		}
	}

	/**
	 * 
	 * searchEmployeeByName
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: void
	 * @Since: Feb 19, 2014
	 * @throws:
	 */
	public void searchEmployeeByName(SenderAction senderAction) {
		BaseFragment base = (BaseFragment) senderAction.sender;
		try {
			List<Employee> lst = EmployeeRepository.getInstance(
					base.getActivity()).getByName(
					(String) senderAction.getBundle());
			onReceiverSuccess(senderAction, lst);
		} catch (Exception e) {
			excuteError(senderAction, e);
		}
	}

	/**
	 * 
	 * searchEmployeeByCity
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: void
	 * @Since: Feb 19, 2014
	 * @throws:
	 */
	public void searchEmployeeByCity(SenderAction senderAction) {
		BaseFragment base = (BaseFragment) senderAction.sender;
		try {
			Cities city = CitiesRepository.getInstance(base.getActivity())
					.getById((Integer) senderAction.getBundle());
			List<Employee> lst = new ArrayList<Employee>(city.getEmployees());
			onReceiverSuccess(senderAction, lst);
		} catch (Exception e) {
			e.printStackTrace();
			excuteError(senderAction, e);
		}
	}

	/**
	 * 
	 * them 1 nhan vien
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: void
	 * @Since: Feb 19, 2014
	 * @throws:
	 */
	public void addNewEmployee(SenderAction senderAction) {
		BaseFragment base = (BaseFragment) senderAction.sender;
		try {

			int i = EmployeeRepository.getInstance(base.getActivity()).create(
					(Employee) senderAction.bundle);
			onReceiverSuccess(senderAction, i);
		} catch (Exception e) {
			excuteError(senderAction, e);
		}
	}

	/**
	 * 
	 * lay tat ca nhan vien
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: void
	 * @Since: Feb 19, 2014
	 * @throws:
	 */
	public void getAllEmployeeToView(SenderAction senderAction) {
		BaseFragment base = (BaseFragment) senderAction.sender;
		try {

			List<Employee> lst = EmployeeRepository.getInstance(
					base.getActivity()).getAll();
			onReceiverSuccess(senderAction, lst);
		} catch (Exception e) {

			excuteError(senderAction, e);
		}
	}

	/**
	 * 
	 * xoa tat ca thanh pho
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: void
	 * @Since: Feb 19, 2014
	 * @throws:
	 */
	public void deleteAllCities(SenderAction senderAction) {
		BaseFragment base = (BaseFragment) senderAction.sender;

		try {

			CitiesRepository.getInstance(base.getActivity()).deleteAll();
			onReceiverSuccess(senderAction, "OK");
		} catch (Exception e) {
			excuteError(senderAction, e);
		}
	}

	/**
	 * 
	 * getBaseActivity
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: BaseActivity
	 * @Since: Feb 19, 2014
	 * @throws:
	 */
	public BaseActivity getBaseActivity(SenderAction senderAction) {
		BaseActivity base = null;
		if (senderAction.sender instanceof BaseActivity) {
			base = (BaseActivity) senderAction.sender;
		}
		return base;
	}

	/**
	 * 
	 * tao moi thanh pho
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: void
	 * @Since: Feb 18, 2014
	 * @throws:
	 */
	public void addNewCity(SenderAction senderAction) {
		BaseFragment base = (BaseFragment) senderAction.sender;
		try {
			int kq = CitiesRepository.getInstance(base.getActivity()).create(
					(Cities) senderAction.bundle);
			onReceiverSuccess(senderAction, kq);
		} catch (Exception e) {
			excuteError(senderAction, e);
		}
	}

	/**
	 * 
	 * lay tat ca thanh pho
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: void
	 * @Since: Feb 18, 2014
	 * @throws:
	 */
	public void getAllCitiesToView(SenderAction senderAction) {
		BaseFragment base = (BaseFragment) senderAction.sender;
		try {

			List<Cities> lst = CitiesRepository.getInstance(base.getActivity())
					.getAll();
			this.onReceiverSuccess(senderAction, lst);
		} catch (Exception e) {
			excuteError(senderAction, e);
		}
	}

	/**
	 * 
	 * excuteError
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: void
	 * @Since: Feb 18, 2014
	 * @throws:
	 */
	public void excuteError(SenderAction senderAction, Exception e) {
		BaseMessageResponses abstractResponse = new BaseMessageResponses();
		abstractResponse
				.setResponseCode(BaseMessageResponses.DIFFERENCE_MESSAGE_ERROR);
		this.onReceiverError(senderAction, abstractResponse);

	}

	/**
	 * 
	 * Ham get string encrypt rsa from message
	 * 
	 * @author: nhantd
	 * @return: String
	 * @Since: Jan 10, 2014
	 * @throws:
	 */
	private String getJSONencryptRSA(BaseMessageRequest message) {
		try {
			String str = RSAEncryptUtil.getInstance().rsaEncrypt(
					MapperJsonObject.convertObjectToJson(message));
			return str;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * noi dung sua
	 * 
	 * @author:nhantd
	 * @since:Jan 20, 2014
	 */
	@Override
	public void onReceiverError(SenderAction senderAction,
			BaseMessageResponses abstractResponse) {

		ReceiverAction receiver = new ReceiverAction();
		receiver.senderAction = senderAction;
		receiver.bundle = abstractResponse;
		UserPresenter.getInstance().onReceiverModelToViewError(receiver);
	}

	/**
	 * noi dung sua
	 * 
	 * @author:thuantp
	 * @since:Feb 19, 2014
	 */
	@Override
	public void onReceiverSuccess(SenderAction senderAction, Object dataResponse) {

		ReceiverAction receiver = new ReceiverAction();
		receiver.senderAction = senderAction;
		receiver.errorCode = 200;
		receiver.bundle = dataResponse;
		UserPresenter.getInstance().onReceiverModelToViewSuccess(receiver);
	}

	/**
	 * send loi len server Mo ta chuc nang cua ham
	 * 
	 * @author: duchha
	 * @param:
	 * @return: void
	 * @Since: Mar 7, 2014
	 * @throws:
	 */
	public void sendLogServer(SenderAction senderhttp, boolean checkhttps) {
		requestService(senderhttp, checkhttps);
	}
}
