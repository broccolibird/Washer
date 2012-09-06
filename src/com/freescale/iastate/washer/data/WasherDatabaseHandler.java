package com.freescale.iastate.washer.data;

import com.freescale.iastate.washer.util.Program;
import com.freescale.iastate.washer.util.Rinse;
import com.freescale.iastate.washer.util.Spin;
import com.freescale.iastate.washer.util.Stain;
import com.freescale.iastate.washer.util.Wash;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WasherDatabaseHandler extends SQLiteOpenHelper{

    private static final String DEBUG_TAG = "WasherDatabase";
    private static final int DB_VERSION = 10;
    private static final String DB_NAME = "washer_data";
    private static String DB_PATH = "/data/data/com.freescale.iastate.washer.data/databases/";
    private Context context;
    
	public WasherDatabaseHandler(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {        
        db.execSQL(ProgramDataSource.CREATE_TABLE_PROGRAMS);
        db.execSQL(StainDataSource.CREATE_TABLE_STAINS);
        
        DatabaseInfo.populateProgramTable(this, db);
        DatabaseInfo.populateStainTable(context, this, db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DEBUG_TAG, "Upgrading database. Existing contents will be lost. ["
                + oldVersion + "]->[" + newVersion + "]");
        db.execSQL("DROP TABLE IF EXISTS " + ProgramDataSource.TABLE_PROGRAMS);
        db.execSQL("DROP TABLE IF EXISTS " + StainDataSource.TABLE_STAINS);
        onCreate(db);
		
	}
    
    public void addProgram(SQLiteDatabase db, Program program){
    	Wash wash = (Wash) program.getWashCycle();
    	Rinse rinse = (Rinse) program.getRinseCycle();
    	Spin spin = (Spin) program.getSpinCycle();
    	
    	db.execSQL("insert into programs (name, custom, description, wash_length, wash_temperature, "
    			+ "wash_steam, wash_agitation, presoak_length, rinse_length, rinse_temperature, "
    			+ "rinse_steam, rinse_agitation, spin_length, spin_speed, spin_steam) values "
    			+ "('" + program.getName() + "', '" + program.getCustom() + "', '" 
    			+ program.getDescription() + "', '" + wash.getLength() + "', '" + wash.getTemp().getID() + "', '"
        		+ wash.getSteamInt() + "', '" + wash.getAgitation().getID() + "', '" 
    			+ wash.getPresoakLength() + "', '" + rinse.getLength() + "', '" + rinse.getTemp().getID() + "', '"
    			+ rinse.getSteamInt() + "', '" + rinse.getAgitation().getID() + "', '" 
    			+ spin.getLength() + "', '" + spin.getSpinSpeed().getID() + "', '" + spin.getSteamInt() + "');");
    }
    
    public void addStain(SQLiteDatabase db, Stain stain) {
    	ContentValues values = new ContentValues();
    	values.put(StainDataSource.COL_TYPE, stain.getType());
    	values.put(StainDataSource.COL_FABRIC, stain.getFabric());
    	values.put(StainDataSource.COL_SUPPLIES, stain.getSuppliesString());
    	values.put(StainDataSource.COL_STEPS, stain.getStepsString());
    	values.put(StainDataSource.COL_NOTES, stain.getNotes());
    	values.put(StainDataSource.COL_DISCLAIMER, stain.getDisclaimer());
    	values.put(StainDataSource.COL_SOURCE, stain.getSource());
    	values.put(StainDataSource.COL_SOURCEURL, stain.getSourceURL());

    	 Log.w(DEBUG_TAG, "Adding stain to stains database: " + stain.getType());
    	 
    	db.insert(StainDataSource.TABLE_STAINS, null, values);
    }
    
    /*private void addStain(SQLiteDatabase db, StainTreatment stain){
    	db.execSQL("insert into stains (type, fabric, directions, wash) values ('" + stain.getType() + "', '" 
    			+ stain.getFabric() + "', " + stain.getDirections() + "', " + stain.getWash() + "');");
    }*/
    
    /*
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    /*private boolean checkDataBase(){
    	 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }*/
    
}
