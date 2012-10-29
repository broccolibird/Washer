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
	
	public void updateStain(Stain stain) {
		if(stain != null) {
			if(stain.getType() != null) {
				getView().findViewById(R.id.type_label).setVisibility(View.VISIBLE);
				((TextView)getView().findViewById(R.id.type_label)).setText("Stain Type:");
				getView().findViewById(R.id.type).setVisibility(View.VISIBLE);
				((TextView)getView().findViewById(R.id.type)).setText(stain.getType());
			} else {
				getView().findViewById(R.id.type_label).setVisibility(View.GONE);
				getView().findViewById(R.id.type).setVisibility(View.GONE);
				((TextView)getView().findViewById(R.id.type)).setText("Unknown Type");
			}
			
			if(stain.getFabric() != null) {
				getView().findViewById(R.id.fabric_label).setVisibility(View.VISIBLE);
				getView().findViewById(R.id.fabric).setVisibility(View.VISIBLE);
				((TextView)getView().findViewById(R.id.fabric)).setText(stain.getFabric());
			} else {
				getView().findViewById(R.id.fabric_label).setVisibility(View.GONE);
				getView().findViewById(R.id.fabric).setVisibility(View.GONE);
				((TextView)getView().findViewById(R.id.fabric)).setText("");
			}
			
			if(stain.getSuppliesString() != null) {
				getView().findViewById(R.id.supplies_label).setVisibility(View.VISIBLE);
				getView().findViewById(R.id.supplies).setVisibility(View.VISIBLE);
				((TextView)getView().findViewById(R.id.supplies)).setText(stain.getSuppliesString());
			} else {
				getView().findViewById(R.id.supplies_label).setVisibility(View.GONE);
				getView().findViewById(R.id.supplies).setVisibility(View.GONE);
				((TextView)getView().findViewById(R.id.supplies)).setText("");
			}
			
			if(stain.getStepsString() != null) {
				getView().findViewById(R.id.steps_label).setVisibility(View.VISIBLE);
				getView().findViewById(R.id.steps).setVisibility(View.VISIBLE);
				((TextView)getView().findViewById(R.id.steps)).setText(stain.getStepsString());
			} else {
				getView().findViewById(R.id.steps_label).setVisibility(View.GONE);
				getView().findViewById(R.id.steps).setVisibility(View.GONE);
				((TextView)getView().findViewById(R.id.steps)).setText("");
			}
			
			if(stain.getNotes() != null && !stain.getNotes().equals("") ) {
				getView().findViewById(R.id.notes_label).setVisibility(View.VISIBLE);
				getView().findViewById(R.id.notes).setVisibility(View.VISIBLE);
				((TextView)getView().findViewById(R.id.notes)).setText(stain.getNotes());
			} else {
				getView().findViewById(R.id.notes_label).setVisibility(View.GONE);
				getView().findViewById(R.id.notes).setVisibility(View.GONE);
				((TextView)getView().findViewById(R.id.notes)).setText("");
			}
			
			if(stain.getDisclaimer() != null && !stain.getDisclaimer().equals("")) {
				getView().findViewById(R.id.disclaimer_label).setVisibility(View.VISIBLE);
				getView().findViewById(R.id.disclaimer).setVisibility(View.VISIBLE);
				((TextView)getView().findViewById(R.id.disclaimer)).setText(stain.getDisclaimer());
			} else {
				getView().findViewById(R.id.disclaimer_label).setVisibility(View.GONE);
				getView().findViewById(R.id.disclaimer).setVisibility(View.GONE);
				((TextView)getView().findViewById(R.id.disclaimer)).setText("");
			}
			
			if(stain.getSource() != null) {
				getView().findViewById(R.id.source_label).setVisibility(View.VISIBLE);
				getView().findViewById(R.id.source).setVisibility(View.VISIBLE);
				((TextView)getView().findViewById(R.id.source)).setText(stain.getSource());
			} else {
				getView().findViewById(R.id.source_label).setVisibility(View.GONE);
				getView().findViewById(R.id.source).setVisibility(View.GONE);
				((TextView)getView().findViewById(R.id.source)).setText("");
			}
		} else {
			getView().findViewById(R.id.type_label).setVisibility(View.VISIBLE);
			((TextView)getView().findViewById(R.id.type_label)).setText("Stain Guide");
			getView().findViewById(R.id.type).setVisibility(View.VISIBLE);
			((TextView)getView().findViewById(R.id.type)).setText(R.string.stain_help);
			
			getView().findViewById(R.id.fabric_label).setVisibility(View.GONE);
			getView().findViewById(R.id.fabric).setVisibility(View.GONE);
			getView().findViewById(R.id.supplies_label).setVisibility(View.GONE);
			getView().findViewById(R.id.supplies).setVisibility(View.GONE);
			getView().findViewById(R.id.steps_label).setVisibility(View.GONE);
			getView().findViewById(R.id.steps).setVisibility(View.GONE);
			getView().findViewById(R.id.notes_label).setVisibility(View.GONE);
			getView().findViewById(R.id.notes).setVisibility(View.GONE);
			getView().findViewById(R.id.disclaimer_label).setVisibility(View.GONE);
			getView().findViewById(R.id.disclaimer).setVisibility(View.GONE);
			getView().findViewById(R.id.source_label).setVisibility(View.GONE);
			getView().findViewById(R.id.source).setVisibility(View.GONE);
			
		}
		
	}
	
	public void setNoStainSelected() {
		updateStain(null);
		
	}
}
