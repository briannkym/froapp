package org.table2table.froapp.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TripModel implements Serializable {

	/**
	 * The acceptable error for submitting data to the database. Within this
	 * many pounds greater than 0, one can submit.
	 */
	public static final int ACCEPTABLE_POUNDS_ERROR = 10;

	private Set<CategoryModel> categorySet = new HashSet<CategoryModel>();
	/**
	 * The miscellaneous category.
	 */
	public static final CategoryModel misc = CategoryModel.getInstance("Misc.");
	private List<SiteModel> trip;
	private List<CategoryModel> categories;
	
	private int van;
	private int mileage;
	
	private int tripID = 0;

	/**
	 * Creates an empty TripModel
	 */
	public TripModel() {
		trip = new LinkedList<SiteModel>();
		categories = new LinkedList<CategoryModel>();
	}

	/**
	 * Adds a site to the Trip Model
	 * 
	 * @param name
	 *            The name of the site
	 * @param address
	 *            The address where the site is located (e.g. 1234 Street st.)
	 * @param description
	 *            A short description of the
	 * @param quantities
	 *            The quantities to be picked up from the site. If null, then
	 *            the site is for drop offs.
	 */
	public void addSite(String name, String address, String description,
			List<CategoryModel> categories) {
		List<QuantityModel> quantities;
		if (categories != null) {
			union(categories);
			quantities = getReceiveList(categories);
		} else {
			quantities = getDonationList();
		}
		SiteModel site = new SiteModel(name, address, description, quantities);
		trip.add(site);
		this.categories = getCategoryList();
	}

	/**
	 * Get the site with the given index.
	 * 
	 * @param index
	 *            The index of the site
	 * @return A SiteModel representing the indexed site.
	 */
	public SiteModel getSite(int index) {
		return trip.get(index);
	}

	/**
	 * Get all of the relevant categories for this trip.
	 * 
	 * @return A list of all the categories received.
	 */
	public List<CategoryModel> getCategories() {
		return categories;
	}

	/**
	 * Gets the total pounds currently accumulated on the trip.
	 * 
	 * Currently accumulated = total received - total donated.
	 * 
	 * @return the current total Pounds.
	 */
	public int getAllPounds() {
		int total = 0;
		for (CategoryModel c : categories) {
			total += c.getPounds();
		}
		return total;
	}

	/**
	 * Verify that the current accumulated pounds is within the acceptable
	 * error.
	 * 
	 * @return True iff the pounds are within the acceptable error.
	 */
	public boolean verify() {
		return getAllPounds() < ACCEPTABLE_POUNDS_ERROR;
	}

	/**
	 * Get the number of sites visited in this trip.
	 * @return The number of sites.
	 */
	public int getNumSites() {
		return trip.size();
	}
	
	public void setVanMileage(int van, int mileage) {
		van = van;
		mileage = mileage;
	}

	public int getVanID() {
		return van;
	}
	
	public int getVanMileage() {
		return mileage;
	}

	public List<SiteModel> getAllSites() {
		List<SiteModel> output = new LinkedList<SiteModel>();
		output.addAll(trip);
		return output;
	}
	
	public void setID (int ID) {
		tripID = ID;
	}
	
	public int getID() {
		return tripID;
	}

	private void union(List<CategoryModel> categories) {
		categorySet.addAll(categories);
	}

	private List<QuantityModel> getReceiveList(List<CategoryModel> categories) {
		List<QuantityModel> qm = new LinkedList<QuantityModel>();
		for (CategoryModel c : categories) {
			qm.add(new QuantityModel(c, true));
		}
		qm.add(new QuantityModel(misc, true));
		return qm;
	}

	private List<QuantityModel> getDonationList() {
		List<QuantityModel> qm = new LinkedList<QuantityModel>();
		for (CategoryModel c : categorySet) {
			qm.add(new QuantityModel(c, false));
		}
		qm.add(new QuantityModel(misc, false));
		return qm;
	}

	private List<CategoryModel> getCategoryList() {
		List<QuantityModel> donations = getDonationList();
		List<CategoryModel> categories = new LinkedList<CategoryModel>();
		for (QuantityModel q : donations) {
			categories.add(q.getCategory());
		}
		categories.add(misc);
		return categories;
	}
}
