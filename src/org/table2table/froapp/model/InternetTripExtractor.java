package org.table2table.froapp.model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.table2table.froserver.model.SiteEntry;
import org.table2table.froserver.service.ClientMessage;
import org.table2table.froserver.service.CloseCommand;
import org.table2table.froserver.service.GetRoutesCommand;
import org.table2table.froserver.service.GetSitesCommand;

import android.content.Context;
import android.os.AsyncTask;

public class InternetTripExtractor implements TripExtractor {
	
	private volatile Map<Integer, List<String>> routes = null;
	private volatile List<SiteEntry> sites = null;

	private Context context;
	private String hostName;

	public InternetTripExtractor(Context theContext, String IPAddress) {
		context = theContext;
		hostName = IPAddress;
	}

	@Override
	public void saveTrip(TripModel trip) {
		DatabaseWriter.getInstance(context).saveTrip(trip);
	}

	@Override
	public TripModel getPreviousTrip() {
		return DatabaseWriter.getInstance(context).getPreviousTrip();
	}

	@Override
	public TripModel getTripFromNumber(int tripID)
			throws TripDoesNotExistException {
		Communicator c = new Communicator();
		c.execute(null, null, null);
		try {
			c.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TripModel trip = new TripModel();

		if (c.successful()) {
			List<String> route = routes.get(tripID);
	
			for (String siteName : route) {
				SiteEntry site = null;
				for (SiteEntry iterSite : sites) {
					if (iterSite.getSite().equals(siteName)) {
						site = iterSite;
						break;
					}
				}
	
				List<CategoryModel> categories = null;
	
				if (site.isPickup()) {
					categories = new LinkedList<CategoryModel>();
					for (String catName : site.getExpectedCat()) {
						categories.add(CategoryModel.getInstance(catName));
					}
				}
	
				trip.addSite(site.getSite(), site.getAddress(),
						site.getInformation(), categories);
			}
		} else {
			throw new TripDoesNotExistException("Could not connect");
		}

		return trip;
	}

	@Override
	public boolean isMileageValid(int vanID, int mileage) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean tripExits(int tripID) {
		// TODO
		return true;
	}

	private class Communicator extends AsyncTask<Void, Void, Void> {

		private boolean success = true;
		
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Socket client = new Socket(hostName,
						org.table2table.froserver.Main.portNumber);
	
				ObjectOutputStream output = new ObjectOutputStream(
						client.getOutputStream());
				ObjectInputStream input = new ObjectInputStream(
						new BufferedInputStream(client.getInputStream()));

				output.writeObject(new GetRoutesCommand());
	
				routes = (Map<Integer, List<String>>) input.readObject();
	
				ClientMessage m = (ClientMessage) input.readObject();
	
				output.writeObject(new GetSitesCommand());
	
				sites = (List<SiteEntry>) input.readObject();
	
				m = (ClientMessage) input.readObject();
	
				output.writeObject(new CloseCommand());
	
				m = (ClientMessage) input.readObject();
				
				output.close();
				input.close();
				client.close();
			} catch (Exception e) {
				success = false;
			}
			return null;
		}
		
		public boolean successful() {
			return success;
		}

	}

}
