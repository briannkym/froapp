package org.table2table.froapp.model;

public interface TripExtractor {
	public void saveTrip(TripModel trip);
	
	public TripModel getPreviousTrip();
	
	public TripModel getTripFromNumber(int tripID) throws TripDoesNotExistException;
	
	public boolean isMileageValid(int vanID, int mileage);
	
	public boolean tripExits(int tripID);
}
