/*
 * @ClassName: CitiesFragment.java
 * @Date: Feb 19, 2014
 * @Version: 1
 * Copyright 2006 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.vpmt.template.view.fragment.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.lib.commonobject.ReceiverAction;
import com.viettel.lib.commonobject.SenderAction;
import com.viettel.invoice.one.R;
import com.viettel.vpmt.template.base.BaseFragment;
import com.viettel.vpmt.template.constant.ActionConstants;
import com.viettel.vpmt.template.orm.sqlite.dtoRepository.CitiesRepository;
import com.viettel.vpmt.template.orm.sqlite.dtodb.Cities;
import com.viettel.vpmt.template.presenter.UserPresenter;

/**
 * Mo ta Types
 * 
 * @author: thuantp
 * @version: 1.0
 * @since: Feb 19, 2014
 */

public class CitiesFragment extends BaseFragment {

	public CitiesRepository citiesRepo;
	public TextView cityName;
	public ListView lv_cities;
	public List<String> lst_city_name;
	public List<Cities> lst_cities;
	public ArrayAdapter<String> adapter;
	public Button add_cities_btn;
	public Button delete_all_cities_btn;
	
	static CitiesFragment instance;
	

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
	 * @author:thuantp
	 * @since:Feb 19, 2014
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		citiesRepo = new CitiesRepository(getActivity());
		cityName = (TextView) getActivity().findViewById(R.id.cityName);
		lv_cities = (ListView) getActivity().findViewById(R.id.lst_cities);
		lst_city_name = new ArrayList<String>();
		add_cities_btn = (Button) getActivity().findViewById(R.id.add_cities_btn);
		delete_all_cities_btn = (Button) getActivity().findViewById(R.id.delete_cities_btn);
		
		add_cities_btn.setOnClickListener(this);
		delete_all_cities_btn.setOnClickListener(this);
		
		requestDataToAdapter();
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * 
	 * Khoi tao fragment cung gia tri tu acc view khac gui qua
	 * 
	 * @author: duchha
	 * @param:
	 * @return: CitiesFragment
	 * @Since: Feb 12, 2014
	 * @throws:
	 */
	public static CitiesFragment getInstance() {
		if (instance == null) {
			instance = new CitiesFragment();
		}
		return instance;
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
				R.layout.layout_cities_fragment, container, false);
		View v = super.onCreateView(inflater, view, savedInstanceState);

		return v;
	}

	/**
	 * 
	 * them thanh pho
	 * 
	 * @author: ThuanTP
	 * @param view
	 * @throws SQLException
	 * @return: void
	 * @throws:
	 */
	public void addNew() {
		String str = cityName.getText().toString();
		Cities city = new Cities();
		city.setCityName(str);

		SenderAction sAction = new SenderAction();
		sAction.action = ActionConstants.ACTION_ADD_NEW_CITY;
		sAction.sender = this;
		sAction.isRequestToDb = ActionConstants.ACTION_IS_REQUEST_TO_DB;
		sAction.bundle = city;
		UserPresenter.getInstance().onSendViewToModel(sAction);

		// citiesRepo.create(city);

	}

	public void showUpdateListView(String str) {

		lst_city_name.add(str);
		adapter.notifyDataSetChanged();
		cityName.setText("");
	}

	/**
	 * 
	 * xoa tat ca thanh pho
	 * 
	 * @author: ThuanTP
	 * @param view
	 * @throws SQLException
	 * @return: void
	 * @throws:
	 */
	public void deleteAll() throws SQLException {
		// citiesRepo.deleteAll();
		SenderAction sAction = new SenderAction();
		sAction.action = ActionConstants.ACTION_DELETE_ALL_CITY;
		sAction.sender = this;
		sAction.isRequestToDb = ActionConstants.ACTION_IS_REQUEST_TO_DB;
		UserPresenter.getInstance().onSendViewToModel(sAction);

	}

	/**
	 * 
	 * requestDataToAdapter
	 * 
	 * @author: ThuanTP
	 * @throws SQLException
	 * @return: void
	 * @throws:
	 */
	private void requestDataToAdapter() {

		SenderAction sAction = new SenderAction();
		sAction.action = ActionConstants.ACTION_GET_ALL_CITIES;
		sAction.sender = this;
		sAction.isRequestToDb = ActionConstants.ACTION_IS_REQUEST_TO_DB;
		UserPresenter.getInstance().onSendViewToModel(sAction);

	}

	private void getDataForModelToAdapter(List<Cities> lst) {

		lst_cities = lst;

		for (Cities ct : lst_cities) {
			lst_city_name.add(ct.getCityName());
		}

		/*
		 * if(lst_city_name.size()==0) { lst_city_name.add("aaaa"); }
		 */

		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1,
				lst_city_name);

		lv_cities.setAdapter(adapter);
	}

	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.add_cities_btn:
			addNew();
		
			break;
		case R.id.delete_cities_btn:
			try {
				deleteAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		
		default:
			break;
		}

	}
	
	
	
	/**
	 * onDestroy
	 * 
	 * @author:thuantp
	 * @since:Feb 19, 2014
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/**
	 * 
	 * noi dung sua
	 * 
	 * @author:thuantp
	 * @since:Feb 19, 2014
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onHandlePresenterToViewSuccess(ReceiverAction objectRecevier) {

		switch (objectRecevier.senderAction.action) {
		case ActionConstants.ACTION_GET_ALL_CITIES:
			getDataForModelToAdapter((List<Cities>) objectRecevier.getBundle());
			break;
		case ActionConstants.ACTION_ADD_NEW_CITY:
			showUpdateListView(((Cities) objectRecevier.senderAction
					.getBundle()).getCityName());
			break;
		case ActionConstants.ACTION_DELETE_ALL_CITY:
			lst_city_name = new ArrayList<String>();
			requestDataToAdapter();
			break;
		default:
			break;
		}

		super.onHandlePresenterToViewSuccess(objectRecevier);
	}

	/**
	 * 
	 * noi dung sua
	 * 
	 * @author:thuantp
	 * @since:Feb 19, 2014
	 */
	@Override
	public void onHandlePresenterToViewError(ReceiverAction objectRecevier) {

		super.onHandlePresenterToViewError(objectRecevier);
	}

}
