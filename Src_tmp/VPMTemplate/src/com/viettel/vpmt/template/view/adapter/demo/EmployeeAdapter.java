package com.viettel.vpmt.template.view.adapter.demo;

import java.util.List;

import com.viettel.invoice.one.R;
import com.viettel.vpmt.template.orm.sqlite.dtoRepository.EmployeeRepository;
import com.viettel.vpmt.template.orm.sqlite.dtodb.Employee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EmployeeAdapter extends ArrayAdapter<Employee> {
	private Context mContext;
	private int row;
	private List<Employee> list;

	public EmployeeAdapter(Context context, int textViewResourceId,
			List<Employee> list) {
		super(context, textViewResourceId, list);

		this.mContext = context;
		this.row = textViewResourceId;
		this.list = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		final Employee employee = list.get(position);
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(row, null);

			holder = new ViewHolder();
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if ((list == null) || ((position + 1) > list.size()))
			return view; // Can't extract item

		Employee obj = list.get(position);

		holder.cityName = (TextView) view.findViewById(R.id.employeeName);
		holder.cityName.setText(obj.getName());

		holder.employeeName = (TextView) view.findViewById(R.id.cityName);
		try {

			holder.employeeName.setText(obj.getCity().getCityName());

		}

		catch (Exception e) {
			holder.employeeName.setText("null");
		}

		holder.imgDelete = (ImageView) view.findViewById(R.id.deleteImg);
		holder.imgDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				EmployeeRepository employeeRepo = new EmployeeRepository(
						mContext);
				employeeRepo.delete(employee);
				list.remove(employee);
				EmployeeAdapter.this.notifyDataSetChanged();
			}
		});
		/*
		 * if(null!=holder.name&&null!=obj&&obj.getName().length()!=0){
		 * holder.name.setText(obj.getName()); }
		 */

		return view;
	}

	public static class ViewHolder {
		public TextView cityName;
		public TextView employeeName;
		public ImageView imgDelete;
	}
}
