package org.table2table.froapp.model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import android.content.Context;
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
}