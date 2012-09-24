package com.freescale.iastate.washer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.freescale.iastate.washer.dialmenu.DialFragment;


public class WasherActivity  extends Activity {

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
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
	
}
