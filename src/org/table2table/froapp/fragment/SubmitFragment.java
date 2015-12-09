package org.table2table.froapp.fragment;

import org.table2table.froapp.R;
import org.table2table.froapp.model.OutputControl;
import org.table2table.froapp.model.TripModel;
import org.table2table.froapp.model.TripSubmission;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * This page has only one button that acts to submit the user's information to
 * the database. The button calls on the model to verify that the information is
 * correct.
 * 
 * @author Brian Nakayama
 * 
 */
public class SubmitFragment extends Fragment {

	private TripModel tm;
	
	/*
	 * This is the IP address of the server that the trip will be sent to
	 */
	private String ipAddress;

	public SubmitFragment(TripModel tm, String IPAddress) {
		this.tm = tm;
		ipAddress = IPAddress;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// The last two arguments ensure LayoutParams are inflated
		// properly.
		View rootView = inflater.inflate(R.layout.submit, container, false);

		final Context context = this.getActivity();
		Button submit = (Button) rootView.findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tm.verify()) {
					TripSubmission.submitTrip(tm, ipAddress);
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					builder.setMessage("Unable to submit. Pounds received - pounds donated > "
							+ TripModel.ACCEPTABLE_POUNDS_ERROR);
					builder.create().show();
				}
			}
		});
		return rootView;
	}

}