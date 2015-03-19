package org.table2table.froapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.table2table.froapp.R;
import org.table2table.froapp.model.SiteModel;

public class StoreFragment extends Fragment {

	private SiteModel sm;
	
	public StoreFragment(SiteModel sm){
		this.sm = sm;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.store, container, false);
        sm.getName();

        TextView storeName = (TextView)rootView.findViewById(R.id.storeName);
        TextView storeDescription = (TextView)rootView.findViewById(R.id.storeDescription);
        storeName.setText(sm.getName());
        storeDescription.setText(sm.getDescription());
        //For use with google maps sm.getAddress();
        
        return rootView;
    }

}
