/*
 * @ClassName: FilterFragment.java
 * @Date: Feb 19, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.view.fragment.demo;

import java.sql.SQLException;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.lib.commonobject.ReceiverAction;
import com.viettel.lib.commonobject.SenderAction;
import com.viettel.invoice.one.R;
import com.viettel.vpmt.template.base.BaseFragment;
import com.viettel.vpmt.template.constant.ActionConstants;
import com.viettel.vpmt.template.orm.sqlite.dtoRepository.CitiesRepository;
import com.viettel.vpmt.template.orm.sqlite.dtoRepository.EmployeeRepository;
import com.viettel.vpmt.template.orm.sqlite.dtodb.Cities;
import com.viettel.vpmt.template.orm.sqlite.dtodb.Employee;
import com.viettel.vpmt.template.presenter.UserPresenter;
import com.viettel.vpmt.template.view.adapter.EmployeeAdapter;

/**
 * Mo ta Types
 * 
 * @author: thuantp
 * @version: 1.0
 * @since: Feb 19, 2014
 */

public class FilterFragment extends BaseFragment {

	public TextView employeeName;
	public Spinner spn_cities;
	public EmployeeRepository employeeRepo;
	public CitiesRepository citiesRepo;
	public EmployeeAdapter adapter;
	public ListView lv_employees;
	public List<Employee> lst_employee;
	public Button btn_filterByName;
	public Button btn_filterByCity;
	public Button btn_filterByAll;
	
	
	static FilterFragment instance;

	/**
	 * noi dung sua
	 * 
	 * @author:thuantp
	 * @since:Feb 19, 2014
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}

	/**
	 * 
	 * Khoi tao fragment cung gia tri tu acc view khac gui qua
	 * 
	 * @author: duchha
	 * @param:
	 * @return: FilterFragment
	 * @Since: Feb 12, 2014
	 * @throws:
	 */
	public static FilterFragment getInstance() {
		if (instance == null) {
			instance = new FilterFragment();
		}
		return instance;
	}

	/**
	 * noi dung sua
	 * 
	 * @author:thuan.trinhphuoc
	 * @since:Feb 19, 2014
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		employeeName = (TextView) getActivity().findViewById(R.id.employeeName);
		spn_cities = (Spinner) getActivity().findViewById(R.id.lst_cities_spn);
		employeeRepo = new EmployeeRepository(getActivity());
		citiesRepo = new CitiesRepository(getActivity());
		lv_employees = (ListView) getActivity().findViewById(R.id.lst_employee);
		
		btn_filterByName = (Button) getActivity().findViewById(R.id.filter_by_name);
		btn_filterByCity = (Button) getActivity().findViewById(R.id.filter_by_city);
		btn_filterByAll = (Button) getActivity().findViewById(R.id.filter_by_all);

		btn_filterByName.setOnClickListener(this);
		btn_filterByCity.setOnClickListener(this);
		btn_filterByAll.setOnClickListener(this);
		
		requestDataToAdapter();
		try {
			requestDataForSpiner();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * noi dung sua
	 * 
	 * @author:thuantp
	 * @since:Feb 19, 2014
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup view = (ViewGroup) inflater.inflate(
				R.layout.layout_filter_fragment, container, false);
		View v = super.onCreateView(inflater, view, savedInstanceState);

		return v;
	}

	/**
	 * 
	 * search by name
	 * 
	 * @author: ThuanTP
	 * @return: void
	 * @throws SQLException
	 * @throws:
	 */
	public void filterEmployeeByName() {
		String str = employeeName.getText().toString();
		// lst_employee = employeeRepo.searchByName(str);
		SenderAction sAction = new SenderAction();
		sAction.action = ActionConstants.ACTION_SEARCH_EMPLOYEE_BY_NAME;
		sAction.sender = this;
		sAction.bundle = str;
		sAction.isRequestToDb = ActionConstants.ACTION_IS_REQUEST_TO_DB;
		UserPresenter.getInstance().onSendViewToModel(sAction);
		// requestDataToAdapter();

	}

	/**
	 * 
	 * search by city
	 * 
	 * @author: ThuanTP
	 * @param v
	 * @return: void
	 * @throws SQLException
	 * @throws:
	 */
	public void filterEmployeeByCity() {

		int city_id = 0;
		try {
			city_id = ((Cities) spn_cities.getSelectedItem()).getId();
		} catch (Exception e) {
			city_id = -1;
		}

		String str = employeeName.getText().toString();

		SenderAction sAction = new SenderAction();
		sAction.action = ActionConstants.ACTION_SEARCH_EMPLOYEE_BY_CITY;
		sAction.sender = this;
		sAction.bundle = city_id;
		sAction.isRequestToDb = ActionConstants.ACTION_IS_REQUEST_TO_DB;
		UserPresenter.getInstance().onSendViewToModel(sAction);
		// lst_employee = employeeRepo.searchByCity(city_id,str);
		// requestDataToAdapter();
		// employeeName.setText("");
	}

	/**
	 * 
	 * search by city and name
	 * 
	 * @author: ThuanTP
	 * @param v
	 * @return: void
	 * @throws SQLException
	 * @throws:
	 */
	public void filterEmployeeByAll() {

		int city_id = 0;
		try {
			city_id = ((Cities) spn_cities.getSelectedItem()).getId();
		} catch (Exception e) {
			city_id = -1;
		}

		String str = employeeName.getText().toString();

		SenderAction sAction = new SenderAction();
		sAction.action = ActionConstants.ACTION_SEARCH_EMPLOYEE_BY_NAME_AND_CITYID;
		sAction.sender = this;
		Bundle bundle = new Bundle();
		bundle.putString("name", str);
		bundle.putInt("city_id", city_id);
		sAction.bundle = bundle;
		sAction.isRequestToDb = ActionConstants.ACTION_IS_REQUEST_TO_DB;
		UserPresenter.getInstance().onSendViewToModel(sAction);
		// lst_employee = employeeRepo.searchByCity(city_id,str);
		// requestDataToAdapter();
		// employeeName.setText("");
	}

	/**
	 * 
	 * setDataToAdapter
	 * 
	 * @author: ThuanTP
	 * @throws SQLException
	 * @return: void
	 * @throws:
	 */
	private void requestDataToAdapter() {

		SenderAction sAction = new SenderAction();
		sAction.action = ActionConstants.ACTION_GET_ALL_EMPLOYEES;
		sAction.sender = this;
		sAction.isRequestToDb = ActionConstants.ACTION_IS_REQUEST_TO_DB;
		UserPresenter.getInstance().onSendViewToModel(sAction);

	}

	/**
	 * 
	 * setdataToAdapter
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: void
	 * @Since: Feb 18, 2014
	 * @throws:
	 */
	private void setdataToAdapter(List<Employee> lst_employee) {
		this.lst_employee = lst_employee;
		adapter = new EmployeeAdapter(getActivity(),
				R.layout.employee_view_adapter, this.lst_employee);

		lv_employees.setAdapter(adapter);

	}

	/**
	 * 
	 * lay gia tri city cho spiner
	 * 
	 * @author: ThuanTP
	 * @throws SQLException
	 * @return: void
	 * @throws:
	 */
	public void requestDataForSpiner() throws SQLException {
		SenderAction sAction = new SenderAction();
		sAction.action = ActionConstants.ACTION_GET_ALL_CITIES;
		sAction.sender = this;
		sAction.isRequestToDb = ActionConstants.ACTION_IS_REQUEST_TO_DB;
		UserPresenter.getInstance().onSendViewToModel(sAction);
	}

	/**
	 * 
	 * lay gia tri city cho spiner
	 * 
	 * @author: thuantp
	 * @param:
	 * @return: void
	 * @Since: Feb 18, 2014
	 * @throws:
	 */
	public void getDataForSpiner(List<Cities> lst_cities) {
		ArrayAdapter<Cities> adapter1 = new ArrayAdapter<Cities>(getActivity(),
				android.R.layout.simple_spinner_item, lst_cities);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spn_cities.setAdapter(adapter1);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.filter_by_name:
			filterEmployeeByName();

			break;
		case R.id.filter_by_city:

			filterEmployeeByCity();

			break;
		case R.id.filter_by_all:

			filterEmployeeByAll();

			break;

		default:
			break;
		}

	}

	/**
	 * handlePresenterToViewSuccess
	 * 
	 * @author:thuantp
	 * @since:Feb 18, 2014
	 */
	@Override
	public void onHandlePresenterToViewSuccess(ReceiverAction objectRecevier) {
		switch (objectRecevier.senderAction.action) {
		case ActionConstants.ACTION_GET_ALL_CITIES:
			getDataForSpiner((List<Cities>) objectRecevier.getBundle());
			break;
		case ActionConstants.ACTION_GET_ALL_EMPLOYEES:
			setdataToAdapter((List<Employee>) objectRecevier.getBundle());
			break;
		case ActionConstants.ACTION_SEARCH_EMPLOYEE_BY_NAME:
			setdataToAdapter((List<Employee>) objectRecevier.getBundle());
			break;
		case ActionConstants.ACTION_SEARCH_EMPLOYEE_BY_CITY:
			setdataToAdapter((List<Employee>) objectRecevier.getBundle());
			employeeName.setText("");
			break;
		case ActionConstants.ACTION_SEARCH_EMPLOYEE_BY_NAME_AND_CITYID:
			setdataToAdapter((List<Employee>) objectRecevier.getBundle());
			break;
		default:
			break;

		}
		super.onHandlePresenterToViewSuccess(objectRecevier);
	}

	/**
	 * handlePresenterToViewError
	 * 
	 * @author:thuantp
	 * @since:Feb 18, 2014
	 */
	@Override
	public void onHandlePresenterToViewError(ReceiverAction objectRecevier) {
		// TODO Auto-generated method stub
		super.onHandlePresenterToViewError(objectRecevier);
	}

}
