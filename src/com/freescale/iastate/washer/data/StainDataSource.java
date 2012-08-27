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
    public static final String TABLE_STAINS = "stains";
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
    
    public static final String CREATE_TABLE_STAINS = "CREATE TABLE "
            + TABLE_STAINS + "(" + ID + " INTEGER PRIMARY KEY, " 
    		+ COL_TYPE + " TEXT, " + COL_FABRIC + " TEXT, " 
            + COL_SUPPLIES + " TEXT, " + COL_STEPS + " TEXT, "
            + COL_NOTES + " TEXT, " + COL_DISCLAIMER + " TEXT, "
            + COL_SOURCE + " TEXT, " + COL_SOURCEURL+ " TEXT);";
    
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

    	database.insert(TABLE_STAINS, null, values);
    }
    
    public Cursor getAllStains(String columns[]) {

    	return database.query(TABLE_STAINS, columns, null, null, null, null, null);
    }
    
    public Stain getStain(int id){
		
		Cursor cursor = database.query(TABLE_STAINS,
				null, ID + " = '" + id + "'", null,
				null, null, null);
		
		boolean isEntry = cursor.moveToFirst();
		
		if(isEntry)
			return cursorToStain(cursor);
		else
			return null;
	}
    
    public static Stain cursorToStain(Cursor cursor) {
        
    	String type = cursor.getString(0);
    	String fabric = cursor.getString(1);
    	String supplies = cursor.getString(2);
/*    	String steps = cursor.getString(3);
    	String notes = cursor.getString(4);
    	String disclaimer = cursor.getString(5);
    	String source = cursor.getString(6);
    	String source_url = cursor.getString(7);
    	
    	Stain stain = new Stain(type, fabric, supplies,
    			steps, notes, disclaimer, source, source_url);*/
    	Stain stain = new Stain(type, fabric, supplies,
    			"steps", "notes", "disclaimer", "source", "http:////source_url.com");
    	
		return stain;
	
    }

	public SQLiteDatabase getReadableDatabase() {
		// TODO Auto-generated method stub
		return null;
	}
}
