package org.table2table.froapp.model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.table2table.froserver.model.MileageEntry;
import org.table2table.froserver.model.PoundEntry;
import org.table2table.froserver.service.AddTripCommand;
import org.table2table.froserver.service.CloseCommand;

import android.os.AsyncTask;

public class TripSubmission {
	private static volatile String ip;
	private static volatile TripModel trip; 
	
	public static void submitTrip (TripModel inputTrip, String ipAddress) {
		trip = inputTrip;
		ip = ipAddress;
		Submitter submitter = new Submitter();
		submitter.execute(null, null, null);
		try {
			submitter.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static class Submitter extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			Socket socket = null;
			ObjectOutputStream oOS = null;
			ObjectInputStream oIS = null;
			try {
				socket = new Socket(ip, org.table2table.froserver.Main.portNumber);
				oOS = new ObjectOutputStream(socket.getOutputStream());
				oIS = new ObjectInputStream(socket.getInputStream());
				
				Date d = new Date(Calendar.getInstance().getTimeInMillis());
				
				MileageEntry mileage = new MileageEntry(new Integer(trip.getVanID()),
						d, new Integer(trip.getID()), new Integer(trip.getVanMileage()));
				
				List<PoundEntry> pounds = new LinkedList<PoundEntry>();
				List<SiteModel> sites = trip.getAllSites();
				for (SiteModel site: sites) {
					List<QuantityModel> quantities = site.getQuantities();
					if (site.isPickup()) {
						for (QuantityModel quantity: quantities) {
							if (quantity.getSubtotal() > 0) {
								pounds.add(new PoundEntry(site.getName(), d, quantity.getCategoryName(),
									quantity.getSubtotal()));
							}
						}
					} else {
						for (QuantityModel quantity: quantities) {
							if (quantity.getSubtotal() > 0) {
								pounds.add(new PoundEntry(site.getName(), d, trip.getID(),
									quantity.getCategoryName(), quantity.getSubtotal()));
							}
						}
					}
				}
				
				oOS.writeObject(new AddTripCommand());
				
				oOS.writeObject(mileage);
				
				oOS.writeObject(pounds);
				
				//Consider writing this to an on-screen message when it is written
				
				oIS.readObject();
				
				oIS.readObject();
				
				oOS.writeObject(new CloseCommand());
				
				oIS.readObject();
			} catch (UnknownHostException e) {
				//some kind of error message
			} catch (IOException e) {
				//some kind of error message
			} catch (ClassNotFoundException e) {
				//some kind of error message
			} finally {
				try {
					if (socket != null) {
						socket.close();
					}
					if (oOS != null) {
						oOS.close();
					}
					if (oIS != null) {
						oIS.close();
					}
				} catch (IOException e) {
					//Screw everything if this doesn't work
				}
			}
			
			/*try {
				Socket client = new Socket(ip, org.table2table.froserver.Main.portNumber);
				
				ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
				ObjectInputStream input = new ObjectInputStream(
						new BufferedInputStream(client.getInputStream()));
				
				output.writeObject(new AddTripCommand());
				
				output.writeObject(new MileageEntry(new Integer(trip.getVanID()),
						new Date(new java.util.Date().getTime()),
						new Integer(trip.getID()), new Integer(trip.getVanMileage())));
				
				List<PoundEntry> pounds = new LinkedList<PoundEntry>();
				List<SiteModel> sites = trip.getAllSites();
				for (SiteModel site : sites) {
					
				}
				
				output.close();
				input.close();
				client.close();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			return null;
		}
		
	}
}
