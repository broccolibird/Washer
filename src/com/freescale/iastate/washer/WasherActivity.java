package com.freescale.iastate.washer;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.freescale.iastate.washer.data.WasherDatabaseHandler;
import com.freescale.iastate.washer.dialmenu.DialFragment;
import com.freescale.iastate.washer.util.MenuInterface;


public class WasherActivity  extends Activity implements MenuInterface {

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		initIntents();
        rootIntent.setHelpText(getText(R.string.main_help));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return rootIntent.onOptionsItemSelected(this, item);
	}
	
	public void customizeProgram(View v){
		DialFragment dialFrag = (DialFragment) getFragmentManager().findFragmentById(R.id.dialfragment);
		String selection = dialFrag.getSelectedButton();
		Intent customWash= new Intent(this, CustomProgramActivity.class);
		if(selection != null){
			customWash.putExtra("selection", selection);

		}
		this.startActivity(customWash);
	}
	
	/**
	 * Create intents for the MenuInterface
	 */
	public void initIntents() {
		rootIntent.homeIntent = new Intent(this, WasherActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		rootIntent.stainIntent = new Intent(this, StainViewActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		rootIntent.maintenanceIntent = new Intent(this, MaintenanceActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		//rootIntent.settingsIntent =  new Intent(this, SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		//rootIntent.testIntent = new Intent(this, StainViewActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

	}
	
	public void startWash(String selection) {
//		SQLiteDatabase.deleteDatabase(new File(WasherDatabaseHandler.DB_PATH+"/"+WasherDatabaseHandler.DB_NAME));
		if( selection == null ) {
			Toast.makeText(this, R.string.no_selection, Toast.LENGTH_SHORT).show();
		} else {
			Intent startWash= new Intent(this, ProgressActivity.class);
			startWash.putExtra("selection", selection);

			// TODO : Set values on this menu
			startWash.putExtra("load_size", 3);
			startWash.putExtra("soil_level", 2);
			this.startActivity(startWash);
		}
	}
}
