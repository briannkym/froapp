package org.table2table.froapp.fragment;

import org.table2table.froapp.MainActivity;
import org.table2table.froapp.R;
import org.table2table.froapp.control.DepartureControl;
import org.table2table.froapp.model.TripModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class DepartureFragment extends Fragment {
	private TripModel tm = null;
	
	public DepartureFragment(){
		tm = MainActivity.getTrip();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		// The last two arguments ensure LayoutParams are inflated
		// properly.
		View rootView = inflater.inflate(R.layout.pre_departure, container,
				false);
		final DepartureControl dc = new DepartureControl(rootView);
		
		final EditText vanID = (EditText) rootView.findViewById(R.id.vanID);
		final EditText mileage = (EditText) rootView.findViewById(R.id.mileage);

		Button b2 = (Button) rootView.findViewById(R.id.start);
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (dc.start()) {
					/*
					 * The code here should do three things. Firstly, it should check to see if the van id entered
					 * here is valid. Then it should check to see if the van's mileage is valid. Finally, if the
					 * numbers are valid, it should pass them to the trip model. If either number is invalid, a
					 * Toast should appear that informs the user.
					*/
					
					//For now, I'll just assume that it is correct
					tm.setVanMileage(Integer.parseInt(vanID.getText().toString()), Integer.parseInt(mileage.getText().toString()));
					
					MainActivity.getViewPager().setCurrentItem(MainActivity.getViewPager()
							.getCurrentItem() + 1);
				} else {

				}
			}

		});

		return rootView;
	}
}