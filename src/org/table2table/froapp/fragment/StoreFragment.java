package org.table2table.froapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.table2table.froapp.MainActivity;
import org.table2table.froapp.R;
import org.table2table.froapp.model.SiteModel;

public class StoreFragment extends Fragment {

	private int index = -1;
	
	public StoreFragment(){
	}
	
	public StoreFragment(int index) {
		this.index = index;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if(index == -1){
			index = savedInstanceState.getInt("index");
		}
		SiteModel sm = MainActivity.getTrip().getSite(index);
		
		// The last two arguments ensure LayoutParams are inflated
		// properly.
		View rootView = inflater.inflate(R.layout.store, container, false);
		sm.getName();

		TextView storeName = (TextView) rootView.findViewById(R.id.storeName);
		TextView storeAddress = (TextView) rootView
				.findViewById(R.id.storeAddress);
		TextView storeDescription = (TextView) rootView
				.findViewById(R.id.storeDescription);
		storeName.setText(sm.getName());
		storeAddress.setText(sm.getAddress());
		storeDescription.setText(sm.getDescription());
		// For use with google maps sm.getAddress();

		Button next = (Button) rootView.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.getViewPager().setCurrentItem(MainActivity.getViewPager().getCurrentItem() + 1);
			}
		});

		return rootView;
	}
	
	@Override
	public void onSaveInstanceState (Bundle outState) {
		outState.putInt("index", index);
	}
}