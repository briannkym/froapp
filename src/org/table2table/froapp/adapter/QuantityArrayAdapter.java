package org.table2table.froapp.adapter;

import java.util.LinkedList;
import java.util.List;


import org.table2table.froapp.R;
import org.table2table.froapp.control.CalcControl;
import org.table2table.froapp.model.QuantityModel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class QuantityArrayAdapter extends ArrayAdapter<QuantityModel> {
	private Context context;
	private List<QuantityModel> quantities;
	private List<CalcControl> calculations;

	static class ViewHolder {
		TextView foodType;
		Button foodQuantity;
	}

	public QuantityArrayAdapter(Activity a, Context context, List<QuantityModel> quantities) {
		super(context, R.layout.quantities_row, quantities);
		this.context = context;
		this.quantities = quantities;
		calculations = new LinkedList<CalcControl>();

		for(QuantityModel q : quantities){
			calculations.add(new CalcControl(a, (ViewGroup)a.findViewById(R.id.pager) , q, this));
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.quantities_row, parent, false);

			vh = new ViewHolder();
			vh.foodType = (TextView) convertView.findViewById(R.id.foodType);
			vh.foodQuantity = (Button) convertView.findViewById(R.id.foodQuantity);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.foodType.setText(quantities.get(position).getCategoryName());
		if(!quantities.get(position).isPickup() && quantities.get(position).getSubtotal()== 0){
			vh.foodQuantity.setText("");
			vh.foodQuantity.setHint("+" + quantities.get(position).getCategory().getPounds());
		} else {
			vh.foodQuantity.setText("" + quantities.get(position).getSubtotal());
		}
		
		vh.foodQuantity.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				calculations.get(position).showPopup();
			}
			
		});
		
		return convertView;
	}

}