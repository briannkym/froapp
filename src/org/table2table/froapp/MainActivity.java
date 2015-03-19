package org.table2table.froapp;

import org.table2table.froapp.R;
import org.table2table.froapp.adapter.ParentPagerAdapter;
import org.table2table.froapp.model.TripFactory;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final ActionBar actionBar = this.getSupportActionBar();

		// Specify that tabs should be displayed in the action bar.
		Intent info = this.getIntent();
		int tripID = info.getIntExtra("tripID", 0);//Do nothing for now.
		setContentView(R.layout.parent);
		setTitle("Trip #"+tripID);
		ViewPager vp = (ViewPager)findViewById(R.id.pager);
		TripFactory tf = new TripFactory();
		vp.setAdapter(new ParentPagerAdapter(getSupportFragmentManager(), tf.getExampleTrip()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		//if (id == R.id.action_settings) {
			return true;
		//}
		//return super.onOptionsItemSelected(item);
	}
}
