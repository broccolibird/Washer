package com.freescale.iastate.washer.util;

import com.freescale.iastate.washer.MaintenanceActivity;
import com.freescale.iastate.washer.R;
import com.freescale.iastate.washer.StainMenuActivity;
import com.freescale.iastate.washer.WasherActivity;
import com.freescale.iastate.washer.WeatherActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
		
		public RootIntent() {
			
		}
		
		public boolean onOptionsItemSelected(Activity act, MenuItem item) {

			switch (item.getItemId()) {
			case android.R.id.home:
				act.startActivity(rootIntent.homeIntent);
				return true;
			case R.id.help:
				Toast.makeText(act, helpText, Toast.LENGTH_LONG).show();
				return true;
			case R.id.stain:
				act.startActivity(stainIntent);
				return true;
			case R.id.maintenance:
				act.startActivity(rootIntent.maintenanceIntent);
				return true;
			case R.id.settings:		
				Toast.makeText(act, item.getTitle(), Toast.LENGTH_SHORT).show();
				return true;
			case R.id.weather:
				act.startActivity(rootIntent.weatherIntent);
			default:
				//return act.onOptionsItemSelected(item);
				return false;
			}
		}
		
		private String helpText;
		
		public void setHelpText(CharSequence charSequence) {
			this.helpText = (String) charSequence;
		}
	
		/**
		 * Create intents for the MenuInterface
		 */
		public void initIntents(Activity act) {
			rootIntent.homeIntent = new Intent(act, WasherActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			rootIntent.stainIntent = new Intent(act, StainMenuActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			rootIntent.maintenanceIntent = new Intent(act, MaintenanceActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			rootIntent.weatherIntent = new Intent(act, WeatherActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			//rootIntent.settingsIntent =  new Intent(this, SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			//rootIntent.testIntent = new Intent(this, StainViewActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		}
		
	}
}
