package com.freescale.iastate.washer.stain;

import com.freescale.iastate.washer.R;
import com.freescale.iastate.washer.util.Stain;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StainViewFragment extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		return inflater.inflate(R.layout.stain, container, false);
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	public void updateStain(Stain stain) {
		if(stain != null) {
			if(stain.getType() != null) {
				((TextView)getView().findViewById(R.id.type)).setText(stain.getType());
			} else {
				((TextView)getView().findViewById(R.id.type)).setText("Unknown Type");
			}
			
			if(stain.getFabric() != null) {
				((TextView)getView().findViewById(R.id.fabric)).setText(stain.getFabric());
			} else {
				((TextView)getView().findViewById(R.id.fabric)).setText("");
			}
			
			if(stain.getSuppliesString() != null) {
				((TextView)getView().findViewById(R.id.supplies)).setText(stain.getSuppliesString());
			} else {
				((TextView)getView().findViewById(R.id.supplies)).setText("");
			}
			
			if(stain.getStepsString() != null) {
				((TextView)getView().findViewById(R.id.steps)).setText(stain.getStepsString());
			} else {
				((TextView)getView().findViewById(R.id.steps)).setText("");
			}
			
			if(stain.getNotes() != null) {
				((TextView)getView().findViewById(R.id.notes)).setText(stain.getNotes());
			} else {
				((TextView)getView().findViewById(R.id.notes)).setText("");
			}
			
			if(stain.getDisclaimer() != null) {
				((TextView)getView().findViewById(R.id.disclaimer)).setText(stain.getDisclaimer());
			} else {
				((TextView)getView().findViewById(R.id.disclaimer)).setText("");
			}
			
			if(stain.getSource() != null) {
				((TextView)getView().findViewById(R.id.source)).setText(stain.getSource());
			} else {
				((TextView)getView().findViewById(R.id.source)).setText("");
			}
		}
		
	}
	
	public void setNoStainSelected() {
		Stain noStain = new Stain("Please select a stain", null, (String[])null, null,
				null, null, null, null);
	}
}
