package com.freescale.iastate.washer;


import com.freescale.iastate.washer.data.StainDataSource;
import com.freescale.iastate.washer.util.MenuInterface;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class StainMenuActivity extends Activity implements MenuInterface {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stainmenu);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

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
	
	public void viewAllStains(View view) {
		Intent intent = new Intent(this, StainViewActivity.class);
		this.startActivity(intent);
	}
	
	public void viewByFabric(View view) {
		Intent intent = new Intent(this, StainViewActivity.class);
		String fabric = null;
		if(view == findViewById(R.id.buttonWashable)) {
			fabric = StainDataSource.FABRIC_WASHABLE;
		} else if ( view == findViewById(R.id.buttonCarpet)) {
			fabric = StainDataSource.FABRIC_CARPET;
		} else if ( view == findViewById(R.id.buttonUpholstery)){
			fabric = StainDataSource.FABRIC_UPHOLSTERY;
		}
		
		intent.putExtra("query", fabric);
		this.startActivity(intent);
	}
	
}
