package org.table2table.froapp.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class CategoryModel {

	private static int counter = 0;
	private static List<CategoryModel> types = new LinkedList<CategoryModel>();
	private String category = "";
	private int id = -1;
	private int pounds = 0;
	
	public static CategoryModel getInstance(String category){
		for(CategoryModel c :types){
			if (c.category.equalsIgnoreCase(category)){
				return c;
			}
		}
		CategoryModel c = new CategoryModel(counter, category);
		counter ++;
		types.add(c);
		return c;
	}
	
	private CategoryModel(int id, String category){
		this.id = id;
		this.category = category.toLowerCase(Locale.US);
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof CategoryModel){
			CategoryModel c = (CategoryModel) o;
			if(c.id == this.id){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean addRelative(int pounds){
		if (this.pounds + pounds < 0){
			return false;
		} else {
			this.pounds += pounds;
			return true;
		}
	}

	public int getPounds(){
		return pounds;
	}
	

	public String getCategory(){
		return category;
	}
}
