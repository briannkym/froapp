package org.table2table.froapp.model;

import java.io.Serializable;

/**
 * The object for a single row of calculations for a category. Using
 * {@link #update(), #setPounds(int), #setQuantity(int)} the CalculationModel
 * updates an encapsulated CategoryModel, while ensuring that no totals are
 * negative.
 * 
 * @author Brian Nakayama
 * @see CategoryModel
 */
public class CalculationModel implements Serializable {

	/**
	 * 1st version. Documented as of Dec. 3rd.
	 */
	private static final long serialVersionUID = 1L;
	
	private int pounds = 0;
	private int quantity = 1;
	private boolean receiving = true;
	private int prevPounds = 0;
	private int prevQuantity = 1;
	private CategoryModel category;

	/**
	 * Create a calculation model for a specific category at a Site that is
	 * receiving.
	 * 
	 * @param category
	 *            The CategoryModel representing a specific category.
	 * @see CategoryModel#getInstance(String)
	 */
	public CalculationModel(CategoryModel category) {
		this.category = category;
	}

	/**
	 * Create a CalculationModel that can be either receiving (taking food from
	 * a site) or donating (giving food to a site)
	 * 
	 * @param category
	 *            The CategoryModel representing a specific category.
	 * @param receiving
	 *            True iff the Model is for a receiving site.
	 */
	public CalculationModel(CategoryModel category, boolean receiving) {
		this.category = category;
		this.receiving = receiving;
	}

	/**
	 * Setter for quantity. Note that quantities cannot be negative (negative
	 * numbers are converted first).
	 * 
	 * Also note that setting the quantity does not automatically update the
	 * relevant category.
	 * 
	 * @param quantity
	 *            The quantity (in units)
	 * @see #update()
	 */
	public void setQuantity(int quantity) {
		this.quantity = Math.abs(quantity);
	}

	/**
	 * Setter for pounds. Note that pounds cannot be negative (negative numbers
	 * are converted first).
	 * 
	 * Also note that setting the pounds does not automatically update the
	 * relevant category.
	 * 
	 * @param pounds
	 *            The pounds (lb.s)
	 * @see #update()
	 */
	public void setPounds(int pounds) {
		this.pounds = Math.abs(pounds);
	}

	/**
	 * Get the quantity
	 * 
	 * @return the quantity (in units).
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Get the pounds
	 * 
	 * @return The pounds (lb.s)
	 */
	public int getPounds() {
		return pounds;
	}

	/**
	 * Attempts to set Pounds to the remaining left for donating. (and calls the
	 * update method).
	 * 
	 * @return False iff not donating or if we cannot add the remainder.
	 */
	public boolean remainder() {
		if (!receiving) {
			this.quantity = 1;
			this.pounds = category.getPounds();
			return update();
		}
		return false;
	}

	/**
	 * 
	 * Updates the associated category with the currently set pounds and
	 * quantity if the total results in a positive number of pounds for the
	 * category.
	 * 
	 * If this calculation model is being used for a donating site, then the
	 * total will be subtracted from the category. If this would make the
	 * category negative, then the update returns false, and the current
	 * quantity and pounds are reset to their previous values.
	 * 
	 * Similarly, for a receiving site, if we are decreasing the amount received
	 * in a calculation, we first check if this would make the category
	 * negative. If so update returns false, and the current quantity and pounds
	 * are reset to their previous values.
	 * 
	 * @return True iff the current quantity and pounds result in a positive
	 *         number of pounds for the category.
	 */
	public boolean update() {
		int total = getTotal();
		int change = total - prevPounds * prevQuantity;
		if (!receiving) {
			change = -change;
		}
		if (category.addRelative(change)) {
			prevQuantity = quantity;
			prevPounds = pounds;
		} else {
			this.pounds = prevPounds;
			this.quantity = prevQuantity;
			return false;
		}
		return true;
	}

	/**
	 * Get the total amount of pounds donated (quantity times pounds).
	 * 
	 * @return The total pounds (lb.s)
	 */
	public int getTotal() {
		return quantity * pounds;
	}
}
