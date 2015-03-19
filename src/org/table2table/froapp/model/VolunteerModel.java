package org.table2table.froapp.model;

import java.util.LinkedList;
import java.util.List;

public class VolunteerModel {
	private int mileage = 0;
	private int vanID = 0;
	private List<String> volunteerList = new LinkedList<String>();

	public VolunteerModel() {

	}

	public List<String> getVolunteerList() {
		return volunteerList;
	}
	
	public void setMileage(int mileage) {
		this.mileage = mileage;
	}
	
	public void setVanID(int vanID) {
		this.vanID = vanID;
	}
}
