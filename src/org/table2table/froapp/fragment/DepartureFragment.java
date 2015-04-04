package org.table2table.froapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

import org.table2table.froapp.MainActivity;
import org.table2table.froapp.R;
import org.table2table.froapp.adapter.ParentPagerAdapter;
import org.table2table.froapp.control.DepartureControl;

public class DepartureFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// The last two arguments ensure LayoutParams are inflated
		// properly.
		View rootView = inflater.inflate(R.layout.pre_departure, container,
				false);
		final DepartureControl dc = new DepartureControl(rootView);

		Button b2 = (Button) rootView.findViewById(R.id.start);
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (dc.start()) {
					MainActivity.vp.setCurrentItem(MainActivity.vp
							.getCurrentItem() + 1);
				} else {

				}
			}

		});

		return rootView;
	}
}