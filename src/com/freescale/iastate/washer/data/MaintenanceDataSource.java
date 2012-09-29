package com.freescale.iastate.washer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.freescale.iastate.washer.util.MaintenanceItem;

public class MaintenanceDataSource {

	private SQLiteDatabase database;
	private WasherDatabaseHandler dbHelper;
	
	// Maintenance Database Columns
    public static final String TABLE = "maintenance";
    public static final String ID = "_id";
    public static final String COL_TYPE = "type";
    public static final String COL_TITLE = "title";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_SOURCE = "source";
    public static final String COL_SOURCEURL = "source_url";
    
    public static String ROUTINE = "Routine";
    public static String HOW_TO = "How To";
    public static String PROBLEM = "Problem";
    
    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE + "(" + ID + " INTEGER PRIMARY KEY, " 
    		+ COL_TYPE + " TEXT, " + COL_TITLE + " TEXT, " 
            + COL_DESCRIPTION + " TEXT, "
            + COL_SOURCE + " TEXT, " + COL_SOURCEURL+ " TEXT);";
    
    public static final String allColumns[] = {ID, 
    	COL_TYPE, COL_TITLE, COL_DESCRIPTION,
    	COL_SOURCE, COL_SOURCEURL};
   
    public MaintenanceDataSource(Context context) {
    	dbHelper = new WasherDatabaseHandler(context);
    }
    
    public void open() throws SQLException {
    	database = dbHelper.getWritableDatabase();
    }
    
    public void close() {
    	dbHelper.close();
    }
    
    public void addMaintenanceItem(MaintenanceItem mi) {
    	ContentValues values = new ContentValues();
    	values.put(COL_TYPE, mi.type);
    	values.put(COL_TITLE, mi.title);
    	values.put(COL_DESCRIPTION, mi.getDescriptionString());
    	values.put(COL_SOURCE, mi.source);
    	values.put(COL_SOURCEURL, mi.source_url);

    	database.insert(TABLE, null, values);
    }
    
    public Cursor getAllMaintenanceItems() {
    	return database.query(TABLE, allColumns, null, null, null, null, null);
    }
    
    public Cursor getMaintenanceItemByType(String type) {
    	return database.query(TABLE, allColumns,
    			COL_TYPE+"='"+type+"'", null, null, null, null);
    }
   
    public MaintenanceItem getMaintenanceItem(int id){
		
		Cursor cursor = database.query(TABLE,
				null, ID + " = '" + id + "'", null,
				null, null, null);
		
		boolean isEntry = cursor.moveToFirst();
		
		if(isEntry)
			return cursorToMaintenanceItem(cursor);
		else
			return null;
	}
    
    public static MaintenanceItem cursorToMaintenanceItem(Cursor cursor) {
        
    	String type = cursor.getString(1);
    	String title = cursor.getString(2);
    	String description = cursor.getString(3);
    	String source = cursor.getString(4);
    	String source_url = cursor.getString(5);
    	
    	MaintenanceItem mi = new MaintenanceItem(type, title, description,
    			source, source_url);
    	
		return mi;
	
    }
    
    public void updateMaintenanceTableData(Context context) {
		database.execSQL("DROP TABLE IF EXISTS " + MaintenanceDataSource.TABLE);
		database.execSQL(MaintenanceDataSource.CREATE_TABLE);
		DatabaseInfo.populateMaintenanceTable(context, dbHelper, database);
	}
}
