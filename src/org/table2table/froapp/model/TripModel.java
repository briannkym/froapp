package org.table2table.froapp.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TripModel {

	public static final int ACCEPTABLE_POUNDS_ERROR = 10;

	private Set<CategoryModel> categorySet = new HashSet<CategoryModel>();
	public final CategoryModel misc = CategoryModel.getInstance("Misc.");
	private List<SiteModel> trip;
	private List<CategoryModel> categories;

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
		SiteModel site = new SiteModel(name, address, description,
				quantities);
		trip.add(site);
		this.categories = getCategoryList();
	}

	public SiteModel getSite(int index) {
		return trip.get(index);
	}

	public List<CategoryModel> getCategories() {
		return categories;
	}

	public int getAllPounds() {
		int total = 0;
		for (CategoryModel c : categories) {
			total += c.getPounds();
		}
		return total;
	}

	public boolean verify() {
		return getAllPounds() < ACCEPTABLE_POUNDS_ERROR;
	}

	public int getNumSites() {
		return trip.size();
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
