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
	private VolunteerControl vc;
	private VolunteerModel vm = new VolunteerModel();
	private List<String> volunteerList = vm.getVolunteerList();

	/**
	 * This code interfaces with the model for adding volunteers, mileage, and
	 * vanID.
	 * 
	 * @param a
	 *            The view of the departure fragment.
	 */
	public DepartureControl(View a) {
		e1 = (EditText) a.findViewById(R.id.mileage);
		e2 = (EditText) a.findViewById(R.id.numberField);
		b1 = (Button) a.findViewById(R.id.addVolunteer);
		vc = new VolunteerControl((ViewGroup) a.findViewById(R.id.top),
				volunteerList);
		b1.setVisibility(View.INVISIBLE);
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!addVolunteer()) {
					// TODO add code for if volunteer is not added.
				}
			}

		});

		// TabListener is added only when tabs are created.
	}

	/**
	 * Mostly for use with the Add Volunteer button.
	 * 
	 * @return true
	 */
	public boolean addVolunteer() {
		vc.showPopup();
		return true;
	}

	/**
	 * 
	 * @return true iff the mileage and vanID are valid.
	 */
	public boolean start() {
		int mileage = 0;
		int vanID = -1;
		try {
			mileage = Integer.parseInt(e1.getText().toString());
			if (!e2.getText().toString().equals("")) {
				vanID = Integer.parseInt(e2.getText().toString());
			}
		} catch (Exception e) {
			return false;
		}

		vm.setMileage(mileage);
		vm.setVanID(vanID);

		if (vm.verify()) {
			return true;
		}
		return false;

	}
}
