package com.freescale.iastate.washer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.freescale.iastate.washer.util.Stain;


public class StainDataSource {

	private SQLiteDatabase database;
	private WasherDatabaseHandler dbHelper;
	
    //Stain Database Values
    public static final String TABLE = "stains";
    public static final String ID = "_id";
    public static final String COL_TYPE = "type";
    public static final String COL_FABRIC = "fabric";
    public static final String COL_SUPPLIES = "supplies";
    public static final String COL_STEPS = "steps";
    public static final String COL_NOTES = "notes";
    public static final String COL_DISCLAIMER = "disclaimer";
    public static final String COL_SOURCE = "source";
    public static final String COL_SOURCEURL = "source_url";
    //public static final String COL_WASH = "wash"; 
    
    public static String FABRIC_WASHABLE = "Washable fabrics";
    public static String FABRIC_CARPET = "Carpet";
    public static String FABRIC_UPHOLSTERY = "Upholstery";

    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE + "(" + ID + " INTEGER PRIMARY KEY, " 
    		+ COL_TYPE + " TEXT, " + COL_FABRIC + " TEXT, " 
            + COL_SUPPLIES + " TEXT, " + COL_STEPS + " TEXT, "
            + COL_NOTES + " TEXT, " + COL_DISCLAIMER + " TEXT, "
            + COL_SOURCE + " TEXT, " + COL_SOURCEURL+ " TEXT);";
    
    public static final String allColumns[] = {ID, 
    	COL_TYPE, COL_FABRIC, COL_SUPPLIES, COL_STEPS,
    	COL_NOTES, COL_DISCLAIMER, COL_SOURCE, COL_SOURCEURL}; 
    
    public StainDataSource(Context context) {
    	dbHelper = new WasherDatabaseHandler(context);
    }
    
    public void open() throws SQLException {
    	database = dbHelper.getWritableDatabase();
    }
    
    public void close() {
    	dbHelper.close();
    }
    
    
    public void addStain(Stain stain) {
    	ContentValues values = new ContentValues();
    	values.put(COL_TYPE, stain.getType());
    	values.put(COL_FABRIC, stain.getFabric());
    	values.put(COL_SUPPLIES, stain.getSuppliesString());
    	values.put(COL_STEPS, stain.getStepsString());
    	values.put(COL_NOTES, stain.getNotes());
    	values.put(COL_DISCLAIMER, stain.getDisclaimer());
    	values.put(COL_SOURCE, stain.getSource());
    	values.put(COL_SOURCEURL, stain.getSourceURL());

    	database.insert(TABLE, null, values);
    }
    
    public Cursor getAllStains() {
    	return database.query(TABLE, allColumns, null, null, null, null, null);
    }
    
    public Cursor getStainByFabric(String fabric) {
    	return database.query(TABLE, allColumns,
    			COL_FABRIC+"='"+fabric+"'", null, null, null, null);
    }
    
    public Cursor searchForStains(String request) {
    	return database.query(TABLE, allColumns, COL_TYPE+" LIKE '%"+request+"%'", null, null, null, null);
    }
    
    public Stain getStain(int id){
		
		Cursor cursor = database.query(TABLE,
				null, ID + " = '" + id + "'", null,
				null, null, null);
		
		boolean isEntry = cursor.moveToFirst();
		
		if(isEntry)
			return cursorToStain(cursor);
		else
			return null;
	}
    
    public static Stain cursorToStain(Cursor cursor) {
        
    	String type = cursor.getString(1);
    	String fabric = cursor.getString(2);
    	String supplies = cursor.getString(3);
    	String steps = cursor.getString(4);
    	String notes = cursor.getString(5);
    	String disclaimer = cursor.getString(6);
    	String source = cursor.getString(7);
    	String source_url = cursor.getString(8);
    	
    	Stain stain = new Stain(type, fabric, supplies,
    			steps, notes, disclaimer, source, source_url);
    	
		return stain;
	
    }

	public SQLiteDatabase getReadableDatabase() {
		// TODO Auto-generated method stub
		return null;
	}
}
