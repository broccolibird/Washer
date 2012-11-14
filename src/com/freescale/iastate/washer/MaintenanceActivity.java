package com.freescale.iastate.washer;



import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.freescale.iastate.washer.maintenance.MaintenanceListFragment.OnMaintenanceItemSelectedListener;
import com.freescale.iastate.washer.maintenance.MaintenanceViewFragment;
import com.freescale.iastate.washer.util.MaintenanceItem;
import com.freescale.iastate.washer.util.MenuInterface;

public class MaintenanceActivity extends Activity implements MenuInterface, OnMaintenanceItemSelectedListener {
	
	private MaintenanceViewFragment maintView;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * @author: James A. Rich
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintlayout);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
        rootIntent.setHelpText("Maintenance Guide", getText(R.string.maintenance_help));

		maintView = ((MaintenanceViewFragment)getFragmentManager().findFragmentById(R.id.maintviewfrag));
		maintView.setNoMaintenanceItemSelected();
		
	}
	
	@Override
	public void onMaintenanceItemSelected(MaintenanceItem mi) {
		maintView.updateMaintenanceItem(mi);
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
	
}
