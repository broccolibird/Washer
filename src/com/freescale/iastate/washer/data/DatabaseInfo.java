package com.freescale.iastate.washer.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import au.com.bytecode.opencsv.CSVReader;

import com.freescale.iastate.washer.util.Cycle.Level;
import com.freescale.iastate.washer.util.Cycle.Temperature;
import com.freescale.iastate.washer.util.MaintenanceItem;
import com.freescale.iastate.washer.util.Program;
import com.freescale.iastate.washer.util.Rinse;
import com.freescale.iastate.washer.util.Spin;
import com.freescale.iastate.washer.util.Stain;
import com.freescale.iastate.washer.util.Wash;

public class DatabaseInfo {

	public static void populateProgramTable(WasherDatabaseHandler wd, SQLiteDatabase db){
		Wash delicate_wash = new Wash(10, Temperature.values()[1], Level.values()[1], 0, false);
		Rinse delicate_rinse = new Rinse(5, Temperature.values()[1], Level.values()[0], false);
		Spin delicate_spin = new Spin(6, Level.values()[1], false);
		Program delicate = new Program(0, "Delicate",
				"Use for lightweight and delicate items. Examples include cotton-blend sweaters, linen shirts and dry-fit wear.",
				delicate_wash, delicate_rinse, delicate_spin);
		
		wd.addProgram( db, delicate);
				
		Wash white_wash = new Wash(15, Temperature.values()[4], Level.values()[3], 0, false);
		Rinse white_rinse = new Rinse(7, Temperature.values()[4], Level.values()[2], false);
		Spin white_spin = new Spin(5, Level.values()[2], false);
		Program whites = new Program(0, "White", "Use for white items.", white_wash, white_rinse, white_spin);
		
		wd.addProgram( db, whites);
		
		Wash color_wash = new Wash(13, Temperature.values()[1], Level.values()[2], 0, false);
		Rinse color_rinse = new Rinse(6, Temperature.values()[1], Level.values()[3], false);
		Spin color_spin = new Spin(5, Level.values()[2], false);
		Program colors = new Program(0, "Color", "Use on colored items.", color_wash, color_rinse, color_spin);
		wd.addProgram( db, colors);
		
		Wash dark_wash = new Wash(12, Temperature.values()[1], Level.values()[2], 0, false);
		Rinse dark_rinse = new Rinse(6, Temperature.values()[1], Level.values()[2], false);
		Spin dark_spin = new Spin(5, Level.values()[2], false);
		Program dark = new Program(0, "Dark", "Use on dark items.", dark_wash, dark_rinse, dark_spin);
		wd.addProgram( db, dark);
		
		Wash pp_wash = new Wash(13, Temperature.values()[3], Level.values()[2], 0, false);
		Rinse pp_rinse = new Rinse(6, Temperature.values()[2], Level.values()[1], false);
		Spin pp_spin = new Spin(5, Level.values()[1], false);
		Program permanent_press = new Program(0, "Permanent Press", "Use on EVERYTHING", pp_wash, pp_rinse, pp_spin);
		wd.addProgram( db, permanent_press);
		
		Wash quick_wash = new Wash(8, Temperature.values()[4], Level.values()[3], 0, false);
		Rinse quick_rinse = new Rinse(4, Temperature.values()[4], Level.values()[2], false);
		Spin quick_spin = new Spin(4, Level.values()[3], false);
		Program quick = new Program(0, "Quick", "wash your clothes quickly!", quick_wash, quick_rinse, quick_spin);
		wd.addProgram( db, quick);
		
		Wash cotton_wash = new Wash(13, Temperature.values()[3], Level.values()[2], 0, false);
		Rinse cotton_rinse = new Rinse(5, Temperature.values()[3], Level.values()[3], false);
		Spin cotton_spin = new Spin(5, Level.values()[3], false);
		Program cotton = new Program(0, "Cotton", "cotton, duh", cotton_wash, cotton_rinse, cotton_spin);
		wd.addProgram( db, cotton);
		
		Wash sanitize_wash = new Wash(15, Temperature.values()[4], Level.values()[3], 10, false);
		Rinse sanitize_rinse = new Rinse(7, Temperature.values()[4], Level.values()[4], false);
		Spin sanitize_spin = new Spin(7, Level.values()[3], false);
		Program sanitize = new Program(0, "Sanitize", "sanitize all the things!", sanitize_wash, sanitize_rinse, sanitize_spin);
		wd.addProgram( db, sanitize);
		
		Wash steam_wash = new Wash(17, Temperature.values()[4], Level.values()[2], 0, true);
		Rinse steam_rinse = new Rinse(7, Temperature.values()[4], Level.values()[2], true);
		Spin steam_spin = new Spin(4, Level.values()[2], true);
		Program steam = new Program(0, "Steam", "Steams clothes", steam_wash, steam_rinse, steam_spin);
		wd.addProgram( db, steam);
	}
	
	public static void populateStainTable(Context context, WasherDatabaseHandler wd, SQLiteDatabase db){

		AssetManager assetManager = context.getAssets();
		Stain stain;
		CSVReader csvReader = null;
		
		try {
			InputStream csvStream = assetManager.open("Stains.csv");
			InputStreamReader csvStreamReader = new InputStreamReader(csvStream);
			csvReader = new CSVReader(csvStreamReader);
			String[] line;
			
			csvReader.readNext();
			
			while ((line = csvReader.readNext()) != null) {
				if(line.length == 9 ) {
					stain = new Stain(line[1], line[2], 
							line[3], line[4], line[5], line[6], 
							line[7], line[8]);
					wd.addStain(db, stain);
					if(line[0].equals("31"))
						break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(csvReader != null)
			try {
				csvReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static void populateMaintenanceTable(Context context, WasherDatabaseHandler wd, SQLiteDatabase db){

		AssetManager assetManager = context.getAssets();
		MaintenanceItem mi;
		CSVReader csvReader = null;
		
		try {
			InputStream csvStream = assetManager.open("Maintenance.csv");
			InputStreamReader csvStreamReader = new InputStreamReader(csvStream);
			csvReader = new CSVReader(csvStreamReader);
			String[] line;
			
			csvReader.readNext();
			
			while ((line = csvReader.readNext()) != null) {
				if(line.length == 6 ) {
					mi = new MaintenanceItem(line[1], line[2], 
							line[3], line[4], line[5]);
					wd.addMaintenanceItem(db, mi);
					if(line[0].equals("31"))
						break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(csvReader != null)
			try {
				csvReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
