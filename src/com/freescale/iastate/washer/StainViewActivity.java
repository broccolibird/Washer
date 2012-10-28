package com.freescale.iastate.washer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.freescale.iastate.washer.stain.StainListFragment.OnStainSelectedListener;
import com.freescale.iastate.washer.stain.StainViewFragment;
import com.freescale.iastate.washer.util.MenuInterface;
import com.freescale.iastate.washer.util.Stain;

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
//		
//		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//		MenuItem searchViewMI = menu.findItem(R.id.menu_search);
//		SearchView searchView = (SearchView) searchViewMI.getActionView();
//		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//		searchView.setIconifiedByDefault(false); //Do not iconify the widget; expand it by default
//		
		return true;
	}
}
