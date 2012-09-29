package com.freescale.iastate.washer.maintenance;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
			
			if(mi.getDescriptionString() != null) {
				((TextView)getView().findViewById(R.id.descrip)).setText(mi.getDescriptionString());
			} else {
				((TextView)getView().findViewById(R.id.descrip)).setText("");
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
