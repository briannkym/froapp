package org.table2table.froapp.control;

import java.util.List;


import org.table2table.froapp.R;
import org.table2table.froapp.model.VolunteerModel;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class DepartureControl {
	
	private EditText e1;
	private EditText e2;
	private Button b1;
	private Button b2;
	private VolunteerControl vc;
	private VolunteerModel vm= new VolunteerModel();
	private List<String> volunteerList = vm.getVolunteerList();
	
	public DepartureControl(View a){
		e1 = (EditText)a.findViewById(R.id.mileage);
		e2 = (EditText)a.findViewById(R.id.numberField);
		b1 = (Button)a.findViewById(R.id.addVolunteer);
		b2 = (Button)a.findViewById(R.id.start);
		vc = new VolunteerControl((ViewGroup)a.findViewById(R.id.top) ,volunteerList);
		b1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(!addVolunteer()){
					//TODO add code for if volunteer is not added.
				}
			}
			
		});
		
		b2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(start()){
					//TODO add code for if volunteer is not added.
				} else {
					
				}
			}
			
		});
		
		//TabListener is added only when tabs are created.
	}

	public boolean addVolunteer(){
		vc.showPopup();
		return true;
	}
	
	public boolean start(){
		vm.setMileage(Integer.parseInt(e1.getText().toString()));
		vm.setVanID(Integer.parseInt(e2.getText().toString()));
		
		return true;
	}
}
