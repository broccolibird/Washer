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
				((TextView)getView().findViewById(R.id.type)).setVisibility(View.VISIBLE);
			} else {
				((TextView)getView().findViewById(R.id.type)).setVisibility(View.GONE);
			}
			
			if(mi.title != null) {
				((TextView)getView().findViewById(R.id.title)).setText(mi.title);
				((TextView)getView().findViewById(R.id.title)).setVisibility(View.VISIBLE);
			} else {
				((TextView)getView().findViewById(R.id.title)).setVisibility(View.GONE);
			}
						
			if(mi.getDescriptionString() != null && !mi.getDescriptionString().equals("")) {
				((TextView)getView().findViewById(R.id.descrip)).setText(mi.getDescriptionString());
				((TextView)getView().findViewById(R.id.descrip)).setVisibility(View.VISIBLE);
			} else {
				((TextView)getView().findViewById(R.id.descrip)).setVisibility(View.GONE);
			}
			
			
			if(mi.source != null) {
				((TextView)getView().findViewById(R.id.source)).setText(mi.source);
				((TextView)getView().findViewById(R.id.source)).setVisibility(View.VISIBLE);
				((TextView)getView().findViewById(R.id.source_label)).setVisibility(View.VISIBLE);
			} else {
				((TextView)getView().findViewById(R.id.source)).setVisibility(View.GONE);
				((TextView)getView().findViewById(R.id.source_label)).setVisibility(View.VISIBLE);
			}
		} else {
			getView().findViewById(R.id.title).setVisibility(View.VISIBLE);
			((TextView)getView().findViewById(R.id.title)).setText("Maintenance Guide");
			getView().findViewById(R.id.descrip).setVisibility(View.VISIBLE);
			((TextView)getView().findViewById(R.id.descrip)).setText(R.string.maintenance_help);
			
			getView().findViewById(R.id.type).setVisibility(View.GONE);
			getView().findViewById(R.id.source_label).setVisibility(View.GONE);
			getView().findViewById(R.id.source).setVisibility(View.GONE);
		}
		
	}
	public void setNoMaintenanceItemSelected() {
		updateMaintenanceItem(null);
		
	}

}
