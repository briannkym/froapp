package org.table2table.froapp.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * The CategoryModel holds the total current pounds for each category during the
 * course of a trip. Instances of CategoryModel are protected to ensure that
 * only one of each type of Category is created.
 * 
 * @author Brian Nakayama
 * @see QuantityModel
 */
public class CategoryModel implements Serializable {

	private static int counter = 0;
	private static List<CategoryModel> types = new LinkedList<CategoryModel>();
	private String category = "";
	private int pounds = 0;
	
	/* Mr Nakayama didn't seem to be using this variable, since it was the same
	 * for all instances of this class. So I used it for the database. It now
	 * has a getter. */
	private final int id;
	
	/**
	 * Creates a category with the given name, or if that category exists,
	 * returns that category. (Factory)
	 * 
	 * @param category The name of the category, e.g. "Produce"
	 * @return The categoryModel for the given String.
	 */
	public static CategoryModel getInstance(String category) {
		for (CategoryModel c : types) {
			if (c.category.equalsIgnoreCase(category)) {
				return c;
			}
		}
		CategoryModel c = new CategoryModel(counter, category);
		counter++;
		types.add(c);
		return c;
	}

	private CategoryModel(int id, String category) {
		this.id = id;
		this.category = category.toLowerCase(Locale.US);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof CategoryModel) {
			CategoryModel c = (CategoryModel) o;
			if (c.id == this.id) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Attempts to add a relative amount of pounds (positive or negative) to this category.
	 * @param pounds The pounds to be added.
	 * @return True iff adding the requested amount of pounds results in a positive value.
	 */
	public boolean addRelative(int pounds) {
		if (this.pounds + pounds < 0) {
			return false;
		} else {
			this.pounds += pounds;
			return true;
		}
	}

	/**
	 * Gets the current pounds for this category.
	 * @return The current pounds (always positive).
	 */
	public int getPounds() {
		return pounds;
	}

	/**
	 * Returns the name of the category
	 * @return A string representing the name.
	 */
	public String getCategory() {
		return category;
	}
	
	public int getID() {
		return id;
	}
}
