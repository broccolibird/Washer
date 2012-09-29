package com.freescale.iastate.washer;

import com.freescale.iastate.washer.stain.StainListFragment.OnStainSelectedListener;
import com.freescale.iastate.washer.stain.StainViewFragment;
import com.freescale.iastate.washer.util.MenuInterface;
import com.freescale.iastate.washer.util.Stain;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class StainViewActivity extends Activity implements OnStainSelectedListener, MenuInterface {

	private StainViewFragment stainView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stainview);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		stainView = ((StainViewFragment)getFragmentManager().findFragmentById(R.id.stainviewfrag));
		stainView.setNoStainSelected();
	}

	@Override
	public void onStainSelected(Stain stain) {
		// TODO Auto-generated method stub
		stainView.updateStain(stain);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return rootIntent.onOptionsItemSelected(this, item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar, menu);
		return true;
	}
}
