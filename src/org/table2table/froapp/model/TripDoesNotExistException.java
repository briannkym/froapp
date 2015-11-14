package org.table2table.froapp.model;

public class TripDoesNotExistException extends Exception {
	
	int invalidTrip = 0;
	
	public TripDoesNotExistException() {
		super();
	}

	public TripDoesNotExistException(String detailMessage) {
		super(detailMessage);
	}

	public TripDoesNotExistException(Throwable throwable) {
		super(throwable);
	}

	public TripDoesNotExistException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
	
	public TripDoesNotExistException(int invalidTripNumber) {
		super();
		invalidTrip = invalidTripNumber;
	}
	
	public int getTrip() {
		return invalidTrip;
	}
}
