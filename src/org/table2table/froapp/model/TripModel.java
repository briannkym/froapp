package org.table2table.froapp.model;

import java.util.List;

public class TripModel {
	
	public static final int ACCEPTABLE_POUNDS_ERROR = 10;
	
	private List<SiteModel> trip;
	private List<CategoryModel> categories;
	private List<QuantityModel> donations;
	
	public TripModel(List<SiteModel> trip, List<QuantityModel> donations, List<CategoryModel> categories){
		this.trip = trip;
		this.categories = categories;
		this.donations = donations;
	}
	
	public void addSite(SiteModel site){
		trip.add(site);
		//TODO may make more sense to add method that creates a new site here.
	}
	
	public SiteModel getSite(int index){
		return trip.get(index);
	}
	
	public List<CategoryModel> getCategories(){
		return categories;
	}
	
	public int getAllPounds(){
		int total = 0;
		for (CategoryModel c : categories){
			total += c.getPounds();
		}
		return total;
	}
	
	public boolean verify(){
		return getAllPounds() < ACCEPTABLE_POUNDS_ERROR;
	}
	
	public int getNumSites(){
		return trip.size();
	}
	
}
