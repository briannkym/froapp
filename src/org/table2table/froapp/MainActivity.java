package org.table2table.froapp;

import org.table2table.froapp.R;
import org.table2table.froapp.adapter.ParentPagerAdapter;
import org.table2table.froapp.model.OutputControl;
import org.table2table.froapp.model.TripBuilder;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 
 * Start of the Android activity for the phone application.
 * @author Brian Nakayama
 *
 */
public class MainActivity extends ActionBarActivity {

	public static ViewPager vp;
	public ParentPagerAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		
		// Specify that tabs should be displayed in the action bar.
		Intent info = this.getIntent();
		int tripID = info.getIntExtra("tripID", 0);//Do nothing for now.
		setContentView(R.layout.parent);
		setTitle("Trip #"+tripID);
		vp = (ViewPager)findViewById(R.id.pager);
		TripBuilder tf = new TripBuilder();
		adapter = new ParentPagerAdapter(getSupportFragmentManager(), tf.getExampleTrip());
		vp.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		OutputControl.writeToFile(adapter.getTripModel(), this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handling action bar item clicks here. 
		int id = item.getItemId();
		if (id == R.id.addSite) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("This button will add another site.");
			builder.create().show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/*
	 * Override the back button such that the app moves the screen instead of exitting. 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	int current = vp.getCurrentItem();
	        if(current > 0){
	        	vp.setCurrentItem(current - 1);
	        }
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
