package org.table2table.froapp.control;

import org.table2table.froapp.adapter.QuantityArrayAdapter;
import org.table2table.froapp.model.QuantityModel;



import android.app.Activity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.PopupWindow;


public class CalcControl {
	//TODO finish using Volunteer Control as an example.
	
	private ViewGroup parent;
	private PopupWindow p;
	
	public CalcControl(Activity a, ViewGroup parent, QuantityModel q, QuantityArrayAdapter qaa){
		this.parent = parent;
	}
	

	public void showPopup(){	
		p.showAtLocation(parent, Gravity.CENTER, 0, 0);
	}
}
