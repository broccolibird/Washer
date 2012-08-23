package com.freescale.iastate.washer;

import com.freescale.iastate.washer.stain.StainListFragment.OnStainSelectedListener;
import com.freescale.iastate.washer.stain.StainViewFragment;
import com.freescale.iastate.washer.util.Stain;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class StainViewActivity extends Activity implements OnStainSelectedListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stainview);
		((StainViewFragment)getFragmentManager().findFragmentById(R.id.stainviewfrag)).setNoStainSelected();
	}

	@Override
	public void onStainSelected(Stain stain) {
		// TODO Auto-generated method stub
		Toast.makeText(this, stain.getType(), 5);
	}
}
