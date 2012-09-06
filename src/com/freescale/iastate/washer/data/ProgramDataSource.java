package com.freescale.iastate.washer.data;

import java.util.ArrayList;
import java.util.List;

import com.freescale.iastate.washer.util.Cycle;
import com.freescale.iastate.washer.util.Program;
import com.freescale.iastate.washer.util.Rinse;
import com.freescale.iastate.washer.util.Spin;
import com.freescale.iastate.washer.util.Wash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ProgramDataSource {
	private static final String DEBUG_TAG = "ProgramDS";
	
	// Database fields
	private SQLiteDatabase database;
	private WasherDatabaseHandler dbHelper;
	
    // Programs table
    public static final String TABLE_PROGRAMS = "programs";
    public static final String ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_CUSTOM = "custom";
    public static final String COL_DESCRIPTION = "description";
    // Programs table - Wash Cycle
    public static final String COL_WASHLENGTH = "wash_length";
    public static final String COL_WASHTEMP = "wash_temperature";
    public static final String COL_WASHSTEAM = "wash_steam";
    public static final String COL_WASHAG = "wash_agitation";
    public static final String COL_SOAK = "presoak_length";
    // Programs table - Rinse Cycle
    public static final String COL_RINSELENGTH = "rinse_length";
    public static final String COL_RINSETEMP = "rinse_temperature";
    public static final String COL_RINSESTEAM = "rinse_steam"; 
    public static final String COL_RINSEAG = "rinse_agitation";
    // Programs table - Spin Cycle
    public static final String COL_SPINLENGTH = "spin_length";
    public static final String COL_SPINSPEED = "spin_speed";
    public static final String COL_SPINSTEAM = "spin_steam";
    
	private String[] allColumns = { ID, 						//0
			COL_NAME, COL_CUSTOM,				//1, 2
			COL_DESCRIPTION, COL_WASHLENGTH,	//3, 4
			COL_WASHTEMP, COL_WASHSTEAM,		//5, 6
			COL_WASHAG, COL_SOAK,				//7, 8
			COL_RINSELENGTH, COL_RINSETEMP,	//9, 10
			COL_RINSESTEAM, COL_RINSEAG,		//11, 12
			COL_SPINLENGTH, COL_SPINSPEED, 	//13, 14
			COL_SPINSTEAM };									//15
	
	public static final String CREATE_TABLE_PROGRAMS = "CREATE TABLE "
            + TABLE_PROGRAMS + "(" + ID + " INTEGER PRIMARY KEY, " 
    		+ COL_NAME + " TEXT, " + COL_CUSTOM + " INTEGER, " 
            + COL_DESCRIPTION + " TEXT, " + COL_WASHLENGTH + " INTEGER, "
            + COL_WASHTEMP + " INTEGER, " + COL_WASHSTEAM + " INTEGER, "
            + COL_WASHAG + " INTEGER, " + COL_SOAK + " INTEGER, "
            + COL_RINSELENGTH + " INTEGER, " + COL_RINSETEMP + " INTEGER, "
            + COL_RINSESTEAM + " INTEGER, " + COL_RINSEAG + " INTEGER, " 
            + COL_SPINLENGTH + " INTEGER, " + COL_SPINSPEED + " INTEGER, " 
            + COL_SPINSTEAM + " INTEGER);";
	
	
	
	public ProgramDataSource(Context context) {
		dbHelper = new WasherDatabaseHandler(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
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
    
	public Program createProgram(Program program) {
		Wash wash = (Wash) program.getWashCycle();
		Rinse rinse = (Rinse) program.getRinseCycle();
		Spin spin = (Spin) program.getSpinCycle();
		
		ContentValues values = new ContentValues();
		values.put(COL_NAME, program.getName());
		values.put(COL_CUSTOM, program.getCustom());
		values.put(COL_DESCRIPTION, program.getDescription());
		values.put(COL_WASHLENGTH, wash.getLength());
		values.put(COL_WASHTEMP, wash.getTemp().getID());
		values.put(COL_WASHSTEAM, wash.getSteam());
		values.put(COL_WASHAG, wash.getAgitation().getID());
		values.put(COL_SOAK, wash.getPresoakLength());
		values.put(COL_RINSELENGTH, rinse.getLength());
		values.put(COL_RINSETEMP, rinse.getTemp().getID());
		values.put(COL_RINSESTEAM, rinse.getSteam());
		values.put(COL_RINSEAG, rinse.getAgitation().getID());
		values.put(COL_SPINLENGTH, spin.getLength());
		values.put(COL_SPINSPEED, spin.getSpinSpeed().getID());
		values.put(COL_SPINSTEAM, spin.getSteam());
		
		long insertId = database.insert(TABLE_PROGRAMS, null,
				values);
		String return_columns[] = {COL_NAME, COL_CUSTOM};
		Cursor cursor = database.query(TABLE_PROGRAMS,
				return_columns, ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Program newProgram = cursorToProgram(cursor);
		cursor.close();
		return newProgram;
	}

	public void deleteProgram(Program program) {
		long id = program.getId();
		System.out.println("program deleted with id: " + id);
		database.delete(TABLE_PROGRAMS, ID
				+ " = " + id, null);
	}

	public List<Program> getAllPrograms() {
		List<Program> programs = new ArrayList<Program>();

		Cursor cursor = database.query(TABLE_PROGRAMS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Program program = cursorToProgram(cursor);
			programs.add(program);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return programs;
	}
		
	public Program nameToProgram(String name){
		
		Cursor cursor = database.query(TABLE_PROGRAMS,
				allColumns, COL_NAME + " = '" + name + "'", null,
				null, null, null);
		
		boolean isEntry = cursor.moveToFirst();
		
		if(isEntry)
			return cursorToProgram(cursor);
		else
			return null;
	}

	private Program cursorToProgram(Cursor cursor) {
		
		int washSteamInt = cursor.getInt(6);
		Boolean washSteam = (washSteamInt == 1) ? true : false;
		
		Wash wash = new Wash(cursor.getInt(4),  //length
				Cycle.Temperature.values()[cursor.getInt(5)], //temperature
				Cycle.Level.values()[cursor.getInt(7)],	//agitation
				cursor.getInt(8), //presoakLength
				washSteam);					
		Log.i(DEBUG_TAG, wash.toString());
		
		int rinseSteamInt = cursor.getInt(11);
		Boolean rinseSteam = (rinseSteamInt == 1) ? true : false;
		
		Rinse rinse = new Rinse(cursor.getInt(9), //length
				Cycle.Temperature.values()[cursor.getInt(10)], //temperature
				Cycle.Level.values()[cursor.getInt(12)], //agitation 
				rinseSteam); //steam
		Log.i(DEBUG_TAG, rinse.toString());
		
		int spinSteamInt = cursor.getInt(15);
		Boolean spinSteam = (spinSteamInt == 1) ? true : false;

		int spin_length = cursor.getInt(13);
		int spin_speed = cursor.getInt(14);
		Spin spin = new Spin(spin_length, // length
				Cycle.Level.values()[spin_speed], // spin_speed
				spinSteam); //steam
				
		Program program = new Program(cursor.getLong(0), cursor.getString(1), 
				cursor.getString(3), wash, rinse, spin);
		return program;
	}

	public SQLiteDatabase getDatabase(){
		return database;
	}
	
	public WasherDatabaseHandler getDBHelper(){
		return dbHelper;
	}
}