package org.table2table.froapp.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class saves to the database, reloads trips from the database, and will probably send
 * trips to Table to Table's server via the Internet.
 * @author Zachariah Watt
 *
 */
public class DatabaseWriter extends SQLiteOpenHelper implements TripExtractor {

	private static DatabaseWriter instance = null;

	/*
	 * All of these strings hold the names of every table and column in the database. That way,
	 * changes need only be made here, and it will affect every reference to that table or column.
	 */
	public static final String DB_NAME = "output.sqlite";
	public static final int VERSION = 1;
	
	public static final String TABLE_VANS = "vans";
	public static final String VANS_VAN = "van";
	
	public static final String TABLE_MILEAGE = "mileage";
	public static final String MILEAGE_VAN = "van";
	public static final String MILEAGE_ROUTE = "route";
	public static final String MILEAGE_MILES = "miles";
	
	public static final String TABLE_ROUTES = "routes";
	public static final String ROUTES_ROUTE = "route";
	public static final String ROUTES_STOP = "stop";
	public static final String ROUTES_SITE = "site";
	public static final String ROUTES_IS_PICKUP = "isPickup";
	
	public static final String TABLE_EXPECTED_CAT = "expectedCategories";
	public static final String EXPECTED_CAT_SITE = "site";
	public static final String EXPECTED_CAT_CAT = "category";
	
	public static final String TABLE_SITES = "sites";
	public static final String SITES_SITE = "site";
	public static final String SITES_NAME = "name";
	public static final String SITES_ADDRESS = "address";
	public static final String SITES_INFORMATION = "information";
	
	public static final String TABLE_CATEGORIES = "categories";
	public static final String CATEGORIES_CAT = "category";
	public static final String CATEGORIES_NAME = "name";
	
	public static final String TABLE_CALCULATIONS = "calculations";
	public static final String CALCULATIONS_SITE = "site";
	public static final String CALCULATIONS_CAT = "category";
	public static final String CALCULATIONS_POUNDS = "pounds";
	public static final String CALCULATIONS_QUANTITY = "quantity";
	
	//End of table and column name declarations
	
	/**
	 * The object will save the number of this trip to the app's persistent preferences,
	 * and retrieve it again from the same object. This is probably the simplest way to
	 * store that information.
	 */
	private Context context = null;
	
	/**
	 * Constructs a DatabaseWriter
	 * @param context
	 * @param prefs
	 */
	private DatabaseWriter (Context context) {
		super(context, DB_NAME, null, VERSION);
		this.context = context;
		instance = this;
	}
	
	/**
	 * This object is a singleton. This decision was made partly because it makes no sense
	 * for multiple DatabaseWriter objects to exist, and partially because I can't find a
	 * way to transfer objects across activities (and this DatabaseWriter is needed in both
	 * IntroActivity and MainActivity).
	 * @param context
	 * @param prefs
	 * @return
	 */
	public static DatabaseWriter getInstance(Context context) {
		if (instance == null) {
			instance = new DatabaseWriter(context);
		}
		return instance;
	}

	/**
	 * Right now, all I need to do is create a bunch of dummy data until I link this program
	 * up to Mr Nakayama's FroDatabase.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_VANS + " (" + VANS_VAN + " int);");
		db.execSQL("INSERT INTO " + TABLE_VANS + " VALUES(1);");
		db.execSQL("INSERT INTO " + TABLE_VANS + " VALUES(2);");
		
		db.execSQL("CREATE TABLE " + TABLE_MILEAGE + " (" + MILEAGE_VAN + " int," + MILEAGE_ROUTE + " int," + MILEAGE_MILES + " int);");
		
		db.execSQL("CREATE TABLE " + TABLE_EXPECTED_CAT + " (" + EXPECTED_CAT_SITE + " int," + EXPECTED_CAT_CAT + " int);");
		db.execSQL("INSERT INTO " + TABLE_EXPECTED_CAT + " VALUES(1,1);");
		db.execSQL("INSERT INTO " + TABLE_EXPECTED_CAT + " VALUES(1,3);");
		db.execSQL("INSERT INTO " + TABLE_EXPECTED_CAT + " VALUES(2,2);");
		db.execSQL("INSERT INTO " + TABLE_EXPECTED_CAT + " VALUES(2,3);");
		
		db.execSQL("CREATE TABLE " + TABLE_ROUTES + " (" + ROUTES_ROUTE + " int," + ROUTES_STOP + " int," + ROUTES_SITE + " int," + ROUTES_IS_PICKUP + " int);");
		db.execSQL("INSERT INTO " + TABLE_ROUTES + " VALUES(1,1,1,1)");
		db.execSQL("INSERT INTO " + TABLE_ROUTES + " VALUES(1,2,3,0)");
		db.execSQL("INSERT INTO " + TABLE_ROUTES + " VALUES(2,1,2,1)");
		db.execSQL("INSERT INTO " + TABLE_ROUTES + " VALUES(2,2,4,0)");
		
		db.execSQL("CREATE TABLE " + TABLE_SITES + " (" + SITES_SITE + " int," + SITES_NAME + " text," + SITES_ADDRESS + " text," + SITES_INFORMATION + " text);");
		db.execSQL("INSERT INTO " + TABLE_SITES + " VALUES(1,'Hy-Vee','123 Helloworld','the manager's name is wang zhi kang');");
		db.execSQL("INSERT INTO " + TABLE_SITES + " VALUES(2,'Red Lobster','2132 Sunset Drive','each volunteer gets a biscuit');");
		db.execSQL("INSERT INTO " + TABLE_SITES + " VALUES(3,'shelter','somewhere behind an abandoned warehouse','people in ski masks will come out and empty the truck. Hopefully they're the right people');");
		db.execSQL("INSERT INTO " + TABLE_SITES + " VALUES(4,'community','EVERYWHERE','just dump the food on the curb and people will come get it');");
		
		db.execSQL("CREATE TABLE " + TABLE_CATEGORIES + " (" + CATEGORIES_CAT + " int," + CATEGORIES_NAME + " text);");
		db.execSQL("INSERT INTO " + TABLE_CATEGORIES + " VALUES(1,'Hy-Chi');");
		db.execSQL("INSERT INTO " + TABLE_CATEGORIES + " VALUES(2,'Biscuits');");
		db.execSQL("INSERT INTO " + TABLE_CATEGORIES + " VALUES(3,'Leeks');");
		
		db.execSQL("CREATE TABLE " + TABLE_CALCULATIONS + " (" + CALCULATIONS_SITE + " int," + CALCULATIONS_CAT + " int," + CALCULATIONS_POUNDS + " int," + CALCULATIONS_QUANTITY + " int);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Nothing to do here
	}
	
	/**
	 * This method will return a blank Trip based on its corresponding route number in the
	 * "routes" table
	 * @param tripNumber
	 * @return
	 */
	public TripModel getTripFromNumber(int tripNumber) {
		/*Cursor cursor = db.query(
			    String table,
			    String[] columns,
			    String where,
			    String[] whereArgs,
			    String groupBy,
			    String having,
			    String orderBy,
			    String limit)*/
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		String[] tripNumberWhereArgs = {"" + tripNumber};
		
		TripModel trip = new TripModel();
		Cursor routeCursor = db.query(
			    TABLE_ROUTES,
			    null,
			    ROUTES_ROUTE + " = ?",
			    tripNumberWhereArgs,
			    null,
			    null,
			    null
		);
		while (!routeCursor.isAfterLast()) {
			String[] siteNumberWhereArgs = {"" + routeCursor.getString(routeCursor.getColumnIndex(ROUTES_SITE))};
			Cursor siteCursor = db.query(
					TABLE_SITES,
					null,
					SITES_SITE + " = ?",
					siteNumberWhereArgs,
					null,
					null,
					null
			);
			String name = siteCursor.getString(siteCursor.getColumnIndex(SITES_NAME));
			String address = siteCursor.getString(siteCursor.getColumnIndex(SITES_ADDRESS));
			String description = siteCursor.getString(siteCursor.getColumnIndex(SITES_INFORMATION));
			List<CategoryModel>  categories = null;
			if (routeCursor.getInt(routeCursor.getColumnIndex(ROUTES_IS_PICKUP)) == 1) {
				Cursor expectedCat = db.query(
					TABLE_EXPECTED_CAT,
					null,
					EXPECTED_CAT_SITE + " = ?",
					siteNumberWhereArgs,
					null,
					null,
					null
				);
				categories = new LinkedList<CategoryModel>();
				while (!expectedCat.isAfterLast()) {
					String[] catNumberWhereArgs = {"" + expectedCat.getInt(expectedCat.getColumnIndex(EXPECTED_CAT_CAT))};
					Cursor catCursor = db.query(
						TABLE_CATEGORIES,
						null,
						CATEGORIES_CAT + " = ?",
						catNumberWhereArgs,
						null,
						null,
						null
					);
					categories.add(CategoryModel.getInstance(catCursor.getString(catCursor.getColumnIndex(CATEGORIES_NAME))));
					expectedCat.moveToNext();
				}
			}
			trip.addSite(name, address, description, categories);
			routeCursor.moveToNext();
		}
		return trip;
	}
	
	/**
	 * Returns this trip's ID number as stored in the key-value pair in the app's preferences
	 * @return
	 */
	public int getCurrentTripID() {
		return 0;
	}
	
	/**
	 * Reconstitutes the previous trip from the data in the table
	 * @return
	 */
	public TripModel getPreviousTrip() {
		
		FileInputStream inputStream = null;
		ObjectInputStream input = null;
		TripModel trip = null;

		try {
			inputStream = context.openFileInput("trip");
			input = new ObjectInputStream(inputStream);
			trip = (TripModel) input.readObject();
			inputStream.close();
			input.close();
		} catch (Exception e) {
			Log.e("INPUT", e.getMessage());
		}
		
		return trip;
	}
	
	/**
	 * Checks to see if the route number exists in the database
	 * @param tripNumber
	 * @return
	 */
	public boolean tripExists (int tripNumber) {
		return true;
	}
	
	/**
	 * Checks to see if the van exists in the database
	 * @param vanNumber
	 * @return
	 */
	public boolean vanExists (int vanNumber) {
		return true;
	}
	
	/**
	 * Saves the trip to the database so that it can be reconstituted
	 * @param trip
	 * @return
	 */
	public void saveTrip (TripModel trip) {
		
		FileOutputStream fileOutput = null;
		ObjectOutputStream output = null;
		
		try {
			fileOutput = context.openFileOutput("trip", Context.MODE_PRIVATE);
			output = new ObjectOutputStream(fileOutput);
			output.writeObject(trip);
			output.close();
			output.close();
		} catch (Exception e) {
			Log.e("OUTPUT", e.getMessage());
		}
	}
	
	/**
	 * Erases the previous trip from the database in preparation for the acceptance of a new trip.
	 */
	public void clearTrip() {
		
	}

	@Override
	public boolean isMileageValid(int vanID, int mileage) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tripExits(int tripID) {
		// TODO Auto-generated method stub
		return true;
	}
}