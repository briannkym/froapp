package org.table2table.froapp.adapter;

import org.table2table.froapp.R;
import org.table2table.froapp.model.CalculationModel;
import org.table2table.froapp.model.QuantityModel;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;

public class CalcArrayAdapter extends ArrayAdapter<CalculationModel> {
	private Context context;
	private QuantityModel q;

	static class ViewHolder {
		EditText quantity;
		EditText pounds;
		ImageButton delete;
	}

	public CalcArrayAdapter(Context context, QuantityModel q) {
		super(context, R.layout.volunteer, q.getCalculations());
		this.context = context;
		this.q = q;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.calc_row, parent, false);

			vh = new ViewHolder();
			vh.quantity = (EditText) convertView.findViewById(R.id.quantity);
			vh.pounds = (EditText) convertView.findViewById(R.id.pounds);
			vh.delete = (ImageButton) convertView.findViewById(R.id.delete);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.quantity.setFocusable(true);
		vh.pounds.setFocusable(true);
		vh.quantity.setText(q.getCalculations().get(position).getQuantity()
				+ "");
		vh.pounds.setText(q.getCalculations().get(position).getPounds() + "");

		vh.quantity.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					int quantity;
					try {
						quantity = Integer.parseInt(((EditText) v).getText()
								.toString());
					} catch (Exception e) {
						quantity = 0;
					}
					if (q.getCalculations().size() > position) {
						if (!q.updateCalculation(position, quantity, q
								.getCalculations().get(position).getPounds())) {
							notifyDataSetChanged();
						}
					}
				}
			}

		});

		vh.pounds.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					int pounds;
					try {
						pounds = Integer.parseInt(((EditText) v).getText()
								.toString());
					} catch (Exception e) {
						pounds = 0;
					}
					if (q.getCalculations().size() > position) {
						if (!q.updateCalculation(position, q.getCalculations()
								.get(position).getQuantity(), pounds)) {
							notifyDataSetChanged();
						}
					} 
				}
			}
		});

		vh.delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (q.removeCalculation(position)) {
					notifyDataSetChanged();
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					builder.setMessage("Unable to delete. Removing this calculation will make the quantity less than zero.");
					builder.create().show();
				}
			}

		});

		return convertView;
	}

	public void addRemainder() {
		if (!q.isPickup()) {
			q.addCalculation().remainder();
			notifyDataSetChanged();
		}
	}

	public void addCalc() {
		q.addCalculation();
		notifyDataSetChanged();
	}
}
