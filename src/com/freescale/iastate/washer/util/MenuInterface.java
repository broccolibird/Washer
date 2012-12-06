package com.freescale.iastate.washer.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.freescale.iastate.washer.MaintenanceActivity;
import com.freescale.iastate.washer.R;
import com.freescale.iastate.washer.WasherActivity;
import com.freescale.iastate.washer.WeatherActivity;

public interface MenuInterface {
	RootIntent rootIntent = new RootIntent();
	
	public boolean onCreateOptionsMenu(Menu menu);
	public boolean onOptionsItemSelected(MenuItem item);
	
	public static class RootIntent {
		public Intent stainIntent;
		public Intent maintenanceIntent;
		public Intent settingsIntent;
		public Intent homeIntent;
		public Intent testIntent;
		public Intent weatherIntent;
		
		public boolean onOptionsItemSelected(Activity act, MenuItem item) {

			switch (item.getItemId()) {
			case android.R.id.home:
				act.startActivity(rootIntent.homeIntent);
				return true;
			case R.id.help:
				AlertDialog dialog = new AlertDialog.Builder(act).create();
				dialog.setTitle(helpTitle);
				dialog.setMessage(helpText);
								
				dialog.show();
				return true;
			case R.id.stain:
				act.startActivity(stainIntent);
				return true;
			case R.id.maintenance:
				act.startActivity(rootIntent.maintenanceIntent);
				return true;
			case R.id.weather:
				act.startActivity(rootIntent.weatherIntent);
			default:
				return false;
			}
		}
		
		private String helpText;
		private String helpTitle;
		
		public void setHelpText(CharSequence title, CharSequence text) {
			this.helpTitle = (String) title;
			this.helpText = (String) text;
		}
	
		/**
		 * Create intents for the MenuInterface
		 */
		public void initIntents(Activity act) {
			rootIntent.homeIntent = new Intent(act, WasherActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			rootIntent.maintenanceIntent = new Intent(act, MaintenanceActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			rootIntent.weatherIntent = new Intent(act, WeatherActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		}
		
	}
}
