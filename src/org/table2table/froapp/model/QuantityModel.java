package org.table2table.froapp.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Brian Nakayama
 * 
 */
public class QuantityModel implements Serializable {

	/**
	 * 1st version. Documented as of Dec. 3rd.
	 */
	private static final long serialVersionUID = 1L;
	
	
	private CategoryModel category;
	// US lb.s
	private boolean receiving = true;
	private List<CalculationModel> calculations = new LinkedList<CalculationModel>();

	/**
	 * Instantiates a model for a sum quantity of a category from/to a site.
	 * 
	 * @param category
	 *            The related CategoryModel.
	 * @param receiving
	 *            True iff the site holding this model is receiving (vs.
	 *            donating).
	 */
	public QuantityModel(CategoryModel category, boolean receiving) {
		this.category = category;
		this.receiving = receiving;
	}

	/**
	 * Update a given calculation in a list of calculations.
	 * 
	 * @param index
	 *            The index of the calculation (starting from 0).
	 * @param quantity
	 *            The new quantity.
	 * @param pounds
	 *            The new pounds.
	 * @return True iff the quantity and pounds results in a positive total for
	 *         the category.
	 * @see CalculationModel#update()
	 */
	public boolean updateCalculation(int index, int quantity, int pounds) {
		CalculationModel c = calculations.get(index);
		if (c == null) {
			return false;
		}
		c.setPounds(pounds);
		c.setQuantity(quantity);
		if (c.update()) {
			return true;
		}
		return false;
	}

	/**
	 * Get the encapsulated list of Calculations. (Not necessary. This class is
	 * meant to be a facade).
	 * 
	 * @return The encapsulated list of Calculations.
	 */
	public List<CalculationModel> getCalculations() {
		return calculations;
	}

	/**
	 * Return the encapsulated Category.
	 * 
	 * @return the encapsulated Category.
	 */
	public CategoryModel getCategory() {
		return category;
	}

	/**
	 * Get the name of the Category.
	 * 
	 * @return A string representing the category's name.
	 */
	public String getCategoryName() {
		return category.getCategory();
	}

	/**
	 * Add an empty calculation.
	 * 
	 * @return A reference to the calculation added.
	 */
	public CalculationModel addCalculation() {
		CalculationModel c = new CalculationModel(category, receiving);
		calculations.add(c);
		return c;
	}

	/**
	 * Returns if the site for this quantity is a pickup or a drop off.
	 * 
	 * @return True iff we are receiving.
	 */
	public boolean isPickup() {
		return receiving;
	}

	/**
	 * Remove a calculation from the list.
	 * 
	 * @param index
	 *            The index of the calculation.
	 * @return True iff the index exists and removing that calculation does not
	 *         result in a negative amount for the Category.
	 * @see CalculationModel#update()
	 */
	public boolean removeCalculation(int index) {
		CalculationModel c = calculations.get(index);
		if (c == null) {
			return false;
		}
		c.setPounds(0);
		c.setQuantity(1);
		if (c.update()) {
			if (calculations.remove(index) != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the total pounds (donated or received) for the site.
	 * @return a subtotal for the pounds donated or received.
	 */
	public int getSubtotal() {
		int subtotal = 0;
		for (CalculationModel c : calculations) {
			subtotal += c.getTotal();
		}
		return subtotal;
	}

}
