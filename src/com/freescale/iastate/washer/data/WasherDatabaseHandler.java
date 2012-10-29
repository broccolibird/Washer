package com.freescale.iastate.washer.data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.freescale.iastate.washer.util.MaintenanceItem;
import com.freescale.iastate.washer.util.Program;
import com.freescale.iastate.washer.util.Rinse;
import com.freescale.iastate.washer.util.Spin;
import com.freescale.iastate.washer.util.Stain;
import com.freescale.iastate.washer.util.Wash;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class WasherDatabaseHandler extends SQLiteAssetHelper{

    private static final String DEBUG_TAG = "WasherDatabase";

    private static final int DB_VERSION = 1;
    public static final String DB_NAME = "washer_data";
    public static String DB_PATH = "/data/data/com.freescale.iastate.washer/databases/";
    
	public WasherDatabaseHandler(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
    
    public void addStain(SQLiteDatabase db, Stain stain) {
    	ContentValues values = new ContentValues();
    	values.put(StainDataSource.COL_TYPE, stain.getType());
    	values.put(StainDataSource.COL_FABRIC, stain.getFabric());
    	values.put(StainDataSource.COL_SUPPLIES, stain.getSuppliesListString());
    	values.put(StainDataSource.COL_STEPS, stain.getStepsListString());
    	values.put(StainDataSource.COL_NOTES, stain.getNotes());
    	values.put(StainDataSource.COL_DISCLAIMER, stain.getDisclaimer());
    	values.put(StainDataSource.COL_SOURCE, stain.getSource());
    	values.put(StainDataSource.COL_SOURCEURL, stain.getSourceURL());

    	 Log.w(DEBUG_TAG, "Adding stain to stains database: " + stain.getType());
    	 
    	db.insert(StainDataSource.TABLE, null, values);
    }
    
    public void addMaintenanceItem(SQLiteDatabase db, MaintenanceItem mi) {
    	ContentValues values = new ContentValues();
    	values.put(MaintenanceDataSource.COL_TYPE, mi.type);
    	values.put(MaintenanceDataSource.COL_TITLE, mi.title);
    	values.put(MaintenanceDataSource.COL_DESCRIPTION, mi.getDescriptionString());
    	values.put(MaintenanceDataSource.COL_SOURCE, mi.source);
    	values.put(MaintenanceDataSource.COL_SOURCEURL, mi.source_url);

    	 Log.w(DEBUG_TAG, "Adding maintenance item to maintenance database: " + mi.type);
    	 
    	db.insert(MaintenanceDataSource.TABLE, null, values);
    }
    
    
   
    
}
