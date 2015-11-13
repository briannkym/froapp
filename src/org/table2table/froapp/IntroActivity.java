package org.table2table.froapp;

import org.table2table.froapp.model.InternetTripExtractor;
import org.table2table.froapp.model.TripExtractor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IntroActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro_activity);
		final EditText tripID = (EditText) findViewById(R.id.tripID);
		final EditText IPAddress = (EditText) findViewById(R.id.ipAddress);
		final Button load = (Button) findViewById(R.id.load);
		final Button reload = (Button) findViewById(R.id.reload);
		load.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String s = tripID.getText().toString();
				try {
					int id = Integer.parseInt(s);
					
					String ip = IPAddress.getText().toString();
					
					TripExtractor extractor = new InternetTripExtractor(getApplicationContext(), ip);
					
					if (extractor.tripExits(id)) {
						Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
						
						nextScreen.putExtra("reload", false);
						nextScreen.putExtra("tripID", id);
						nextScreen.putExtra("IPAddress", ip.toCharArray());
						
						startActivity(nextScreen);
						finish();
					} else {
						Toast.makeText(IntroActivity.this, R.string.trip_DNE, Toast.LENGTH_SHORT).show();
					}
				} catch (NumberFormatException e) {
					Toast.makeText(IntroActivity.this, R.string.invalid_trip_ID, Toast.LENGTH_SHORT).show();
				}
			}

		});
		
		reload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
				
				nextScreen.putExtra("reload", true);
				
				startActivity(nextScreen);
				finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Nothing needed here.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return true;
	}
}