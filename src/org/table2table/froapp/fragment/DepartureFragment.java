package org.table2table.froapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.table2table.froapp.R;
import org.table2table.froapp.control.DepartureControl;

public class DepartureFragment extends Fragment {

	@Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.pre_departure, container, false);
        new DepartureControl(rootView);
        
        return rootView;
    }
}