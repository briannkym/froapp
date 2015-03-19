package org.table2table.froapp.model;

import java.util.LinkedList;
import java.util.List;

public class QuantityModel {

	private CategoryModel category;
	// US lb.s
	private boolean receiving = true;
	private List<CalculationModel> calculations = new LinkedList<CalculationModel>();

	public QuantityModel(CategoryModel category, boolean receiving) {
		this.category = category;
		this.receiving = receiving;
	}

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
	
	public List<CalculationModel> getCalculations(){
		return calculations;
	}

	public CategoryModel getCategory() {
		return category;
	}

	public String getCategoryName() {
		return category.getCategory();
	}

	public void addCalculation() {
		calculations.add(new CalculationModel(category, receiving));
	}

	public boolean isPickup(){
		return receiving;
	}
	
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

	public int getSubtotal() {
		int subtotal = 0;
		for (CalculationModel c : calculations) {
			subtotal += c.getTotal();
		}
		return subtotal;
	}

}
