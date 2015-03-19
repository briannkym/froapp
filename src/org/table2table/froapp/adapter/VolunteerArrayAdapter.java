package org.table2table.froapp.adapter;

import java.util.List;

import org.table2table.froapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;

public class VolunteerArrayAdapter extends ArrayAdapter<String>{
	private Context context;
	private List<String> volunteers;
	
	static class ViewHolder{
		EditText volunteer;
		ImageButton delete;
	}
	
	public VolunteerArrayAdapter(Context context, List<String> volunteers){
		super(context, R.layout.volunteer, volunteers);
		this.context = context;
		this.volunteers = volunteers;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent){
		ViewHolder vh;
		if(convertView == null){
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.volunteer, parent, false);
			
			vh = new ViewHolder();
			vh.volunteer = (EditText)convertView.findViewById(R.id.volunteer);
			vh.delete = (ImageButton)convertView.findViewById(R.id.delete);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.volunteer.setText(volunteers.get(position));
		vh.volunteer.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
					String name = ((EditText)v).getText().toString();
					if(volunteers.size() > position){
						volunteers.set(position, name);
					}
				}				
			}
			
		});
		
		vh.delete.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				volunteers.remove(position);
				notifyDataSetChanged();
			}
			
		});
		
		
		return convertView;
	}
	
	public void addVolunteer(){
		volunteers.add("");
		notifyDataSetChanged();
	}
}
