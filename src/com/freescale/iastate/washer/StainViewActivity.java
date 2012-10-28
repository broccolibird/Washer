package com.freescale.iastate.washer;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import com.freescale.iastate.washer.data.StainDataSource;
import com.freescale.iastate.washer.stain.StainListFragment;
import com.freescale.iastate.washer.stain.StainListFragment.OnStainSelectedListener;
import com.freescale.iastate.washer.stain.StainViewFragment;
import com.freescale.iastate.washer.util.CustomRadioGroup;
import com.freescale.iastate.washer.util.MenuInterface;
import com.freescale.iastate.washer.util.Stain;

public class StainViewActivity extends Activity implements OnStainSelectedListener, MenuInterface {

	private StainViewFragment stainView;
	private StainListFragment stainList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stainview);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

        rootIntent.setHelpText("Stain Guide", getText(R.string.stain_help));
		
		stainView = ((StainViewFragment)getFragmentManager().findFragmentById(R.id.stainviewfrag));
		stainView.setNoStainSelected();
		
		stainList = ((StainListFragment)getFragmentManager().findFragmentById(R.id.stainlist));
		
		CustomRadioGroup buttonGrp = new CustomRadioGroup();
		buttonGrp.addRadioButton((CompoundButton)findViewById(R.id.buttonWashable));
		buttonGrp.addRadioButton((CompoundButton)findViewById(R.id.buttonCarpet));
		buttonGrp.addRadioButton((CompoundButton)findViewById(R.id.buttonUpholstery));
		buttonGrp.addRadioButton((CompoundButton)findViewById(R.id.buttonViewAll));
		((CompoundButton)findViewById(R.id.buttonViewAll)).setChecked(true);
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
	
	public void viewAllStains(View view) {
		stainList.setAllStains(true);
	}
	
	public void viewByFabric(View view) {
		
		String fabric = null;
		
		if(view == findViewById(R.id.buttonWashable)) {
			fabric = StainDataSource.FABRIC_WASHABLE;
		} else if ( view == findViewById(R.id.buttonCarpet)) {
			fabric = StainDataSource.FABRIC_CARPET;
		} else if ( view == findViewById(R.id.buttonUpholstery)){
			fabric = StainDataSource.FABRIC_UPHOLSTERY;
		}
		
		if(fabric != null) {
			stainList.setAllStains(false);
		} else {
			stainList.setAllStains(true);
		}
		
		stainList.setFabric(fabric);
	}
}
