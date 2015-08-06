package org.table2table.froapp.model;

import java.util.LinkedList;
import java.util.List;

/**
 * The class for building trips
 * TODO Zach: put in code for generating trips here, should return a TripModel.
 * 
 * @author Brian Nakayama
 *
 */
public class TripBuilder {

	/**
	 * Generate a dummy trip for the prototype.
	 * @return A TripModel
	 */
	public TripModel getExampleTrip() {
		TripModel tm = new TripModel();
		
		List<CategoryModel> hyveeCategories = new LinkedList<CategoryModel>();
		hyveeCategories.add(CategoryModel.getInstance("Bread"));
		hyveeCategories.add(CategoryModel.getInstance("Dairy"));
		hyveeCategories.add(CategoryModel.getInstance("Meat"));
		tm.addSite("Hyvee", "123 Example st",
				"Meet Joe in the Bakery, etc.", hyveeCategories);
		
		List<CategoryModel> starbucksCategories = new LinkedList<CategoryModel>();
		starbucksCategories.add(CategoryModel.getInstance("Pastries"));
		tm.addSite("StarBucks", "456 Sesame st",
				"All volunteers get free Mochas, etc.", starbucksCategories);
		
		List<CategoryModel> iowaMUCategories = new LinkedList<CategoryModel>();
		iowaMUCategories.add(CategoryModel.getInstance("Fish"));
		iowaMUCategories.add(CategoryModel.getInstance("Bread"));
		iowaMUCategories.add(CategoryModel.getInstance("Meat"));
		tm.addSite("Iowa Memorial Union",
				"1800 OverTheRiver ct", "Park in the back, etc.", iowaMUCategories);
		
		tm.addSite("Christian Community Center",
				"666 Silly st", "Bla bla bla, etc.", null);
		tm.addSite("Lucas Elementary School", "Y0l0",
				"Enter through the back, etc.", null);
		tm.addSite("IC Compassion",
				"123 Hello World", "No meat here. Only vegetarians, etc.",
				null);

		return tm;
	}

}
