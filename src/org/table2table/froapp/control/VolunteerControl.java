package org.table2table.froapp.control;

import java.util.List;

import org.table2table.froapp.R;
import org.table2table.froapp.adapter.VolunteerArrayAdapter;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.view.View.OnClickListener;

public class VolunteerControl{
	private ViewGroup parent;
	private Button add;
	private Button done;
	private ListView volunteerList;
	private PopupWindow p;
	
	@SuppressLint("InflateParams")
	public VolunteerControl(ViewGroup parent, final List<String> volunteers){
		LayoutInflater li = LayoutInflater.from(parent.getContext());
		View v = li.inflate(R.layout.volunteer_popup, null);
		p = new PopupWindow(v, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		p.setFocusable(true);
		add = (Button)v.findViewById(R.id.addNewVolunteer);
		done = (Button)v.findViewById(R.id.doneAdding);
		volunteerList = (ListView)v.findViewById(R.id.volunteerList);
		final VolunteerArrayAdapter vAA = new VolunteerArrayAdapter(parent.getContext(), volunteers);
		volunteerList.setAdapter(vAA);
		done.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				p.dismiss();
			}
			
		});
		
		add.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				vAA.addVolunteer();
			}
			
		});
		this.parent = parent;
		
	}
	
	public void showPopup(){	
		p.showAtLocation(parent, Gravity.CENTER, 0, 0);
	}
	
}
