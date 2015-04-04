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

	/**
	 * Verifies the constraints are met for mileage and vanID. TODO Better
	 * constraints can potentially be put on the mileage by comparing it with
	 * previous mileage inputs.
	 * 
	 * @return True iff the mileage and vanID are within the constraints
	 */
	public boolean verify() {
		return mileage > 0 && vanID >= 0 && vanID <= 6;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public void setVanID(int vanID) {
		this.vanID = vanID;
	}

	public int getMileage() {
		return mileage;
	}

	public int getVanID() {
		return vanID;
	}
}
