package org.table2table.froapp.fragment;

import java.util.List;

import org.table2table.froapp.R;
import org.table2table.froapp.model.CategoryModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SubmitFragment extends Fragment {

	private List<CategoryModel> categories;
	
	public SubmitFragment(List<CategoryModel> categories){
		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.submit, container, false);
        
        Button submit = (Button)rootView.findViewById(R.id.submit);
        //TODO Add listener to submit button.
        return rootView;
    }

}
