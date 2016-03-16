package org.table2table.froapp.control;


import org.table2table.froapp.R;
import org.table2table.froapp.adapter.CalcArrayAdapter;
import org.table2table.froapp.adapter.QuantityArrayAdapter;
import org.table2table.froapp.model.QuantityModel;



import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;


public class CalcControl {
	//TODO finish using Volunteer Control as an example.
	
	private ViewGroup parent;
	private PopupWindow p;
	private Button add;
	private Button remainder;
	private Button done;
	private ListView calcList;

	public CalcControl(Activity a, ViewGroup parent, QuantityModel q, final QuantityArrayAdapter qaa){
		LayoutInflater li = LayoutInflater.from(parent.getContext());
		View v = li.inflate(R.layout.calc_popup, null);
		p = new PopupWindow(v, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		p.setFocusable(true);
		add = (Button)v.findViewById(R.id.addRow);
		remainder = (Button)v.findViewById(R.id.addRemainder);
		done = (Button)v.findViewById(R.id.doneAdding);
		calcList = (ListView)v.findViewById(R.id.calcList);
		final CalcArrayAdapter cAA = new CalcArrayAdapter(parent.getContext(), q);
		calcList.setAdapter(cAA);
		done.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				/*A bit of a hack.
				* Each Edittext saves its value upon losing focus, 
				* So force all Edittexts to lose focus.
				*/
				removeFocus();
				p.dismiss();
				qaa.notifyDataSetChanged();
			}

		});

		add.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				removeFocus();
				cAA.addCalc();	
			}

		});

		remainder.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				removeFocus();
				cAA.addRemainder();
			}

		});

		if(q.isPickup()){
			remainder.setVisibility(View.GONE);
		}
		this.parent = parent;
	}

	/**
	 * 
	 */
	public void removeFocus(){
		if(calcList.getChildAt(0)!=null){
			calcList.getChildAt(0).requestFocus();
			calcList.getChildAt(0).clearFocus();
		}
	}

	public void showPopup(){	
		p.showAtLocation(parent, Gravity.CENTER, 0, 0);
	}
}