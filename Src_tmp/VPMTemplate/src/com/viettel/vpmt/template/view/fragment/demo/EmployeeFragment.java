/*
 * @ClassName: EmployeeFragment.java
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
import android.widget.Toast;

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

public class EmployeeFragment extends BaseFragment {

	public TextView employeeName;
	public Spinner spn_cities;
	public EmployeeRepository employeeRepo;
	public CitiesRepository citiesRepo;
	public EmployeeAdapter adapter;
	public ListView lv_employees;
	public List<Employee> lst_employee;
	public Button btn_add_employee;
	
	
	static EmployeeFragment instance;
  
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
	 * noi dung sua
	 * 
	 * @author:thuan.trinhphuoc
	 * @since:Feb 19, 2014
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		employeeName = (TextView) getActivity().findViewById(R.id.employeeName);
		spn_cities = (Spinner) getActivity().findViewById(R.id.cities_lst);
		employeeRepo = new EmployeeRepository(getActivity());
		citiesRepo = new CitiesRepository(getActivity());
		lv_employees = (ListView) getActivity().findViewById(R.id.lst_employee);
		btn_add_employee = (Button) getActivity().findViewById(R.id.addEmployee_btn);
		btn_add_employee.setOnClickListener(this);
		
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
				R.layout.layout_employee_fragment, container, false);
		View v = super.onCreateView(inflater, view, savedInstanceState);

		return v;
	}

	/**
	 * 
	 * Khoi tao fragment cung gia tri tu acc view khac gui qua
	 * 
	 * @author: duchha
	 * @param:
	 * @return: EmployeeFragment
	 * @Since: Feb 12, 2014
	 * @throws:
	 */
	public static EmployeeFragment getInstance() {
		if (instance == null) {
			instance = new EmployeeFragment();
		}
		return instance;
	}

	/**
	 * 
	 * addEmployee
	 * 
	 * @author: ThuanTP
	 * @return: void
	 * @throws SQLException
	 * @throws:
	 */
	public void addEmployee()  {

		int city_id = 0;
		try {
			city_id = ((Cities) spn_cities.getSelectedItem()).getId();
		} catch (Exception e) {
			city_id = -1;
		}
		String str = employeeName.getText().toString();
		if (!str.equals("") && city_id != -1) {
			Cities city = citiesRepo.getById(city_id);
			Employee ep = new Employee(str, city);

			SenderAction sAction = new SenderAction();
			sAction.action = ActionConstants.ACTION_ADD_NEW_EMPLOYEE;
			sAction.sender = this;
			sAction.bundle = ep;
			sAction.isRequestToDb = ActionConstants.ACTION_IS_REQUEST_TO_DB;
			UserPresenter.getInstance().onSendViewToModel(sAction);
			// employeeRepo.create(ep);
			// requestDataToAdapter();
			// employeeName.setText("");
		} else {
			employeeName.setText("");
			Toast.makeText(getActivity(), "Miss employee name or city",
					Toast.LENGTH_SHORT).show();
		}
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
		adapter = new EmployeeAdapter(getActivity(),
				R.layout.employee_view_adapter, lst_employee);

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
		case R.id.addEmployee_btn:
			 addEmployee();
		
			break;
	
		
		default:
			break;
		}

	}
	
	
	
	
	@Override
	public void onDestroy() {

		super.onDestroy();
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

		case ActionConstants.ACTION_GET_ALL_EMPLOYEES:
			setdataToAdapter((List<Employee>) objectRecevier.getBundle());
			break;
		case ActionConstants.ACTION_GET_ALL_CITIES:
			getDataForSpiner((List<Cities>)  objectRecevier.getBundle());
			break;
		case ActionConstants.ACTION_ADD_NEW_EMPLOYEE:
			requestDataToAdapter();
			employeeName.setText("");
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
		switch (objectRecevier.senderAction.action) {

		case ActionConstants.ACTION_GET_ALL_EMPLOYEES:
			setdataToAdapter(null);
			break;

		default:
			break;

		}
		super.onHandlePresenterToViewError(objectRecevier);
	}

}
