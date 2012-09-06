package com.freescale.iastate.washer.util;

import com.freescale.iastate.washer.R;

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
			case R.id.test:
				act.startActivity(testIntent);
				return true;
			default:
				return act.onOptionsItemSelected(item);
			}
		}
		
		private String helpText;
		
		public void setHelpText(CharSequence charSequence) {
			this.helpText = (String) charSequence;
		}
	}
	
}
