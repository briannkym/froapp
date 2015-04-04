package org.table2table.froapp.fragment;

import java.util.List;

import org.table2table.froapp.MainActivity;
import org.table2table.froapp.R;
import org.table2table.froapp.adapter.QuantityArrayAdapter;
import org.table2table.froapp.model.QuantityModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class QuantityFragment extends Fragment {

	private List<QuantityModel> quantities;

	public QuantityFragment(List<QuantityModel> quantities) {
		this.quantities = quantities;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// The last two arguments ensure LayoutParams are inflated
		// properly.
		View rootView = inflater.inflate(R.layout.quantities, container, false);
		ListView quantityList = (ListView) rootView
				.findViewById(R.id.quantityList);
		final QuantityArrayAdapter qAA = new QuantityArrayAdapter(
				this.getActivity(), this.getActivity(), quantities);
		quantityList.setAdapter(qAA);

		Button done = (Button) rootView.findViewById(R.id.done);
		done.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.vp.setCurrentItem(MainActivity.vp.getCurrentItem() + 1);
			}
		});

		return rootView;
	}

}
