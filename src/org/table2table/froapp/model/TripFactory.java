package org.table2table.froapp.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TripFactory {
	
	private Set<CategoryModel> categorySet = new HashSet<CategoryModel>();
	private CategoryModel misc = CategoryModel.getInstance("Misc.");
	
	public TripModel getExampleTrip(){
		
		List<CategoryModel> hyveeCategories = new LinkedList<CategoryModel>();
		hyveeCategories.add(CategoryModel.getInstance("Bread"));
		hyveeCategories.add(CategoryModel.getInstance("Dairy"));
		hyveeCategories.add(CategoryModel.getInstance("Meat"));
		union(hyveeCategories);
		
		List<CategoryModel> starbucksCategories = new LinkedList<CategoryModel>();
		starbucksCategories.add(CategoryModel.getInstance("Pastries"));
		union(starbucksCategories);
		
		List<CategoryModel> iowaMUCategories = new LinkedList<CategoryModel>();
		iowaMUCategories.add(CategoryModel.getInstance("Fish"));
		iowaMUCategories.add(CategoryModel.getInstance("Bread"));
		iowaMUCategories.add(CategoryModel.getInstance("Meat"));
		union(iowaMUCategories);
		List<QuantityModel> donations = getDonationList();
		SiteModel hyvee = new SiteModel("Hyvee", "123 Example st", "Meet Joe in the Bakery, etc.", getReceiveList(hyveeCategories));
		SiteModel starbucks = new SiteModel("StarBucks", "456 Sesame st", "All volunteers get free Mochas, etc.", getReceiveList(starbucksCategories));
		SiteModel iowaMU = new SiteModel("Iowa Memorial Union", "1800 OverTheRiver ct", "Park in the back, etc.", getReceiveList(iowaMUCategories));
		SiteModel christianCC = new SiteModel("Christian Community Center", "666 Silly st", "Bla bla bla, etc.", donations);
		SiteModel lucasES = new SiteModel("Lucas Elementary School", "Y0l0", "Enter through the back, etc.", donations);
		SiteModel icCompassion = new SiteModel("IC Compassion", "123 Hello World", "No meat here. Only vegetarians, etc.", donations);

		List<SiteModel> sites = new LinkedList<SiteModel>();
		sites.add(hyvee);
		sites.add(starbucks);
		sites.add(iowaMU);
		sites.add(christianCC);
		sites.add(lucasES);
		sites.add(icCompassion);
		
		TripModel tm = new TripModel(sites, donations, getCategoryList());
		return tm;
	}
	
	public void union(List<CategoryModel> categories){
		categorySet.addAll(categories);
	}
	
	public List<QuantityModel> getReceiveList(List<CategoryModel> categories){
		List<QuantityModel> qm = new LinkedList<QuantityModel>();
		for(CategoryModel c : categories){
			qm.add(new QuantityModel(c, true));
		}
		qm.add(new QuantityModel(misc, true));
		return qm;
	}
	
	public List<QuantityModel> getDonationList(){
		List<QuantityModel> qm = new LinkedList<QuantityModel>();
		for(CategoryModel c : categorySet){
			qm.add(new QuantityModel(c, false));
		}
		qm.add(new QuantityModel(misc, false));
		return qm;
	}

	public List<CategoryModel> getCategoryList(){
		List<QuantityModel> donations = getDonationList();
		List<CategoryModel> categories = new LinkedList<CategoryModel>();
		for(QuantityModel q : donations){
			categories.add(q.getCategory());
		}
		categories.add(misc);
		return categories;
	}
}
