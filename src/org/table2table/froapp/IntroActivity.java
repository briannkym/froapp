package org.table2table.froapp;

import org.table2table.froapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;

public class IntroActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Use this to get intent
		Intent i = getIntent();
		final ActionBar actionBar = this.getSupportActionBar();
		setContentView(R.layout.activity_main);
		final EditText tripID = (EditText) findViewById(R.id.tripID);
		final Button load = (Button) findViewById(R.id.load);
		load.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String s = tripID.getText().toString();
				try {
					int id = Integer.parseInt(s);
					Intent nextScreen = new Intent(getApplicationContext(),
							MainActivity.class);

					// Sending data to another Activity
					nextScreen.putExtra("tripID", id);

					startActivity(nextScreen);
					finish();
				} catch (NumberFormatException e) {
					tripID.setText("");
					tripID.setHint("Incorrect Trip ID");
				}
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return true;
	}
}
