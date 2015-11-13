package org.table2table.froapp.model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class OutputControl {
	
	public static final String PROBLEM = "PROBLEM";
	
	public static void writeToLog (TripModel tm) {
		String flag = "OUTPUT";
		int i = 0;
		SiteModel site = tm.getSite(i);
		while (site != null) {
			Log.d(flag, site.getName());
			List<QuantityModel> quantities = site.getQuantities();
			for (QuantityModel quantity: quantities) {
				Log.d(flag, quantity.getCategoryName() + quantity.getSubtotal());
			}
			
			i++;
			site = tm.getSite(i);
		}
	}

	public static void writeToFile (TripModel tm, Context context) {
		File file = new File(context.getExternalFilesDir("Froapp"), "Data");
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
		} catch (IOException e) {
			Toast.makeText(context, "Can't instantiate the PrintWriter", Toast.LENGTH_LONG).show();;
			System.exit(0);
		}
		
		int sites = tm.getNumSites();
		SiteModel site;
		List<QuantityModel> quantities;
		for (int i = 0; i < sites; i++) {
			site = tm.getSite(i);
			writer.println(site.getName());
			quantities = site.getQuantities();
			for (QuantityModel quantity: quantities) {
				writer.println(quantity.getCategoryName() + ": " + quantity.getSubtotal());
			}
		}
		
		writer.close();
	}
	
	public static void writeToSQL(TripModel tm, Context context) {
		DatabaseWriter db = new DatabaseWriter(context);
		int sites = tm.getNumSites();
		SiteModel site;
		List<QuantityModel> quantities;
		for (int i = 0; i < sites; i++) {
			site = tm.getSite(i);
			quantities = site.getQuantities();
			for (QuantityModel quantity: quantities) {
				db.insert(site.getName(), quantity.getCategoryName(), quantity.getSubtotal());
			}
		}
	}
	
	public static TripModel readFromSQL(Context context) {
		
		
		return null;
	}
	
	private static class DatabaseWriter extends SQLiteOpenHelper {
		public static final String DB_NAME = "output.sqlite";
		public static final int VERSION = 1;
		
		private static final String TABLE_TRIP = "trip";
		
		private static final String COLUMN_ID = "id";
		private static final String COLUMN_SITE = "site";
		private static final String COLUMN_CATEGORY = "category";
		private static final String COLUMN_AMOUNT = "amount";
		
		
		public DatabaseWriter(Context context) {
			super(context, null, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE trip (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SITE + " STRING, " + COLUMN_CATEGORY + " STRING, " + COLUMN_AMOUNT + " INTEGER)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
		public void insert(String site, String category, int amount) {
			ContentValues cv = new ContentValues();
			cv.put(COLUMN_SITE, site);
			cv.put(COLUMN_CATEGORY, category);
			cv.put(COLUMN_AMOUNT, amount);
			getWritableDatabase().insert(TABLE_TRIP, null, cv);
			Log.d("DATABASE", "IT WORKED!");
		}
		
	}
}