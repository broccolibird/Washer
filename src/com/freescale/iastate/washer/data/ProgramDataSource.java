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
    public static final String COL_STEAM = "steam";
    public static final String COL_AGITATION = "agitation";
    public static final String COL_SOAK = "presoak_length";
    // Programs table - Rinse Cycle
    public static final String COL_RINSELENGTH = "rinse_length";
    public static final String COL_RINSETEMP = "rinse_temperature";
    // Programs table - Spin Cycle
    public static final String COL_SPINLENGTH = "spin_length";
    public static final String COL_SPINSPEED = "spin_speed";
    
	private String[] allColumns = { ID, 		//0
			COL_NAME, COL_CUSTOM,				//1, 2
			COL_DESCRIPTION, COL_WASHLENGTH,	//3, 4
			COL_WASHTEMP, COL_STEAM, 			//5, 6
			COL_AGITATION, COL_SOAK,			//7, 8
			COL_RINSELENGTH, COL_RINSETEMP,		//9, 10
			COL_SPINLENGTH, COL_SPINSPEED };	//11, 12
	
	public static final String CREATE_TABLE_PROGRAMS = "CREATE TABLE "
            + TABLE_PROGRAMS + "(" + ID + " INTEGER PRIMARY KEY, " 
    		+ COL_NAME + " TEXT, " + COL_CUSTOM + " INTEGER, " 
            + COL_DESCRIPTION + " TEXT, " + COL_WASHLENGTH + " INTEGER, "
            + COL_WASHTEMP + " INTEGER, " + COL_STEAM + "INTEGER, " 
            + COL_AGITATION + "INTEGER, "+ COL_SOAK + " INTEGER, "
            + COL_RINSELENGTH + " INTEGER, " + COL_RINSETEMP + " INTEGER, "
            + COL_SPINLENGTH + " INTEGER, " + COL_SPINSPEED + " INTEGER);";
	
	
	
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
    			+ program.getDescription() + "', '"	+ wash.getLength() + "', '" 
    			+ wash.getTemp().getID() + "', '" + program.getSteam() + "', '"
    			+ program.getAgitation() + "', '" + wash.getPresoakLength() + "', '" 
    			+ rinse.getLength() + "', '" + rinse.getTemp().getID() + "', '" 
    			+ spin.getLength() + "', '" + spin.getSpinSpeed().getID() + "');");
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
		values.put(COL_STEAM, program.getSteam());
		values.put(COL_AGITATION, program.getAgitation().getID());
		values.put(COL_SOAK, wash.getPresoakLength());
		values.put(COL_RINSELENGTH, rinse.getLength());
		values.put(COL_RINSETEMP, rinse.getTemp().getID());
		values.put(COL_SPINLENGTH, spin.getLength());
		values.put(COL_SPINSPEED, spin.getSpinSpeed().getID());
		
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
		
		Wash wash = new Wash(cursor.getInt(3),  //length
				Cycle.Temperature.values()[cursor.getInt(5)], //temperature
				cursor.getInt(8)); //presoakLength					
		Log.i(DEBUG_TAG, wash.toString());
				
		Rinse rinse = new Rinse(cursor.getInt(9), //length
				Cycle.Temperature.values()[cursor.getInt(10)]); //temperature
				
		Log.i(DEBUG_TAG, rinse.toString());

		int spin_length = cursor.getInt(11);
		int spin_speed = cursor.getInt(12);
		Spin spin = new Spin(spin_length, // length
				Cycle.Level.values()[spin_speed]); // spin_speed
		
		int steamInt = cursor.getInt(6);
		Boolean steam = (steamInt == 1) ? true : false;
		
		Program program = new Program(cursor.getLong(0), // id
				cursor.getString(1), /*name*/ cursor.getString(3), //description
				steam, Cycle.Level.values()[cursor.getInt(7)], //agitation
				wash, rinse, spin);
		return program;
	}

	public SQLiteDatabase getDatabase(){
		return database;
	}
	
	public WasherDatabaseHandler getDBHelper(){
		return dbHelper;
	}
}