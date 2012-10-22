package com.freescale.iastate.washer.maintenance;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freescale.iastate.washer.R;
import com.freescale.iastate.washer.util.MaintenanceItem;

public class MaintenanceViewFragment extends Fragment{

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.maintenance, container, false);
	}
	
	public void updateMaintenanceItem(MaintenanceItem mi) {
		if(mi != null) {
			if(mi.type != null) {
				((TextView)getView().findViewById(R.id.type)).setText(mi.type);
			} else {
				((TextView)getView().findViewById(R.id.type)).setText("Unknown Type");
			}
			
			if(mi.title != null) {
				((TextView)getView().findViewById(R.id.title)).setText(mi.title);
			} else {
				((TextView)getView().findViewById(R.id.title)).setText("");
			}
			
			LinearLayout descriptionList = (LinearLayout) getView().findViewById(R.id.descriplist);
			descriptionList.removeAllViews();
			descriptionList.invalidate();
			
			if(mi.description != null) {
				int descripLength = mi.description.length;
				
				for(int i = 0; i < descripLength; i++) {
					TextView newTV = new TextView(getActivity());
					newTV.setText(mi.description[i]);
					((LinearLayout)getView().findViewById(R.id.descriplist)).addView(newTV);
					
				}
			} 
			
			
			if(mi.source != null) {
				((TextView)getView().findViewById(R.id.source)).setText(mi.source);
			} else {
				((TextView)getView().findViewById(R.id.source)).setText("");
			}
		}
		
	}
	public void setNoMaintenanceItemSelected() {
		// TODO Auto-generated method stub
		
	}

}
