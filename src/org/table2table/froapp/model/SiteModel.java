package org.table2table.froapp.model;

import java.util.List;

/**
 * The class for sites where pickups or dropoffs occur. Holds other classes (but
 * does not perform any other notable action)
 * 
 * @author Brian Nakayama
 * 
 */
public class SiteModel {

	private String name = "";
	private String address = "";
	private List<QuantityModel> quantities;
	private String description = "";

	/**
	 * Creates a new site.
	 * 
	 * @param name
	 *            The name of the site, e.g. "HyVee".
	 * @param address
	 *            The address of the site, e.g. "123 Example St. Ames, IA 50010"
	 * @param description
	 *            A description of the site and potentially what to do, e.g.
	 *            "Meet Bob in the bakery."
	 * @param quantities
	 *            A list of quantities associated with this site. Care must be
	 *            taken to ensure that the QuantityModels given are sensible for
	 *            this site.
	 */
	public SiteModel(String name, String address, String description,
			List<QuantityModel> quantities) {
		this.name = name;
		this.address = address;
		this.description = description;
		this.quantities = quantities;
	}

	/**
	 * Get the name of this site.
	 * 
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the address of this site.
	 * 
	 * @return the address.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Get the description of this site.
	 * 
	 * @return the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns whether or not this site is a pickup or a drop off.
	 * 
	 * @return True if one of the encapsulated quantites are receiving.
	 */
	public boolean isPickup() {
		if (quantities != null) {
			return quantities.get(0).isPickup();
		}
		return false;
	}

	/**
	 * Get the quantities of the site.
	 * 
	 * @return a list of quantities associated with this site.
	 */
	public List<QuantityModel> getQuantities() {
		return quantities;
	}
}
