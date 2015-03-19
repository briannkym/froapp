package org.table2table.froapp.model;

import java.util.List;


public class SiteModel {

	private String name = "";
	private String address = "";
	List<QuantityModel> quantities;
	private String description= "";
	
	public SiteModel(String name, String address, String description, List<QuantityModel> quantities){
		this.name = name;
		this.address = address;
		this.description = description;
		this.quantities = quantities;
	}

	public String getName(){
		return name;
	}

	public String getAddress(){
		return address;
	}
	
	public String getDescription(){
		return description;
	}
	
	public boolean isPickup(){
		if(quantities!=null){
			return quantities.get(0).isPickup();
		}
		return false;
	}
	
	public List<QuantityModel> getQuantities(){
		return quantities;
	}
}
