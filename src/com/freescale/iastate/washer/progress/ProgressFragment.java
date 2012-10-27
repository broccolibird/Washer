package com.freescale.iastate.washer.progress;

import com.freescale.iastate.washer.R;
import com.freescale.iastate.washer.data.ProgramDataSource;
import com.freescale.iastate.washer.util.Program;
import com.freescale.iastate.washer.util.Rinse;
import com.freescale.iastate.washer.util.Spin;
import com.freescale.iastate.washer.util.Wash;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProgressFragment extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.progressfragment, container, false);

	}
	
	/**
	 * Displays information about the program currently running
	 * 
	 * @param program - program to be shown
	 */
	public void updateProgramView(Program program){
		// Unpackage intent to retrieve user selection
		Intent intent = getActivity().getIntent();
		Bundle bundle = intent.getExtras();
		String selection = bundle.getString("selection");
		if(selection != null){
			ProgramDataSource datasource = new ProgramDataSource(getActivity());
			datasource.open();
			
			// Retrieve selected program from database
			program = datasource.nameToProgram(selection);
			datasource.close();
			program.setSoilLevel(bundle.getInt("soil_level"));
			program.setLoadSize(bundle.getInt("load_size"));
		}else{
			program = bundle.getParcelable("program");
			selection = program.getName();
		}
		
		TextView program_name = (TextView) getView().findViewById(R.id.programName);
		program_name.setText(selection);
		
		TextView program_desc = (TextView) getView().findViewById(R.id.programDescription);
		TextView program_length = (TextView) getView().findViewById(R.id.programLength);
		TextView load_size = (TextView) getView().findViewById(R.id.loadSize);
		TextView soil_level = (TextView) getView().findViewById(R.id.soilLevel);
		
		program_desc.setText(program.getDescription());
		program_length.setText("" + program.getLength());
		load_size.setText("Load Size: " + program.getLoadSize().getLabel());
		soil_level.setText("Soil Level: " + program.getSoilLevel().getLabel());
    
		TextView presoak = (TextView) getView().findViewById(R.id.presoakLength);
		TextView wash_length = (TextView) getView().findViewById(R.id.washLength);		
		TextView wash_temp = (TextView) getView().findViewById(R.id.washTemperature);
		TextView wash_ag = (TextView) getView().findViewById(R.id.washAgitation);
		TextView wash_steam = (TextView) getView().findViewById(R.id.washSteam);
		
		Wash wash = (Wash) program.getWashCycle();
		presoak.setText("Presoak length: " + wash.getPresoakLength());
		wash_length.setText("Length: " + wash.getLength());
		wash_temp.setText("Temperature: " + wash.getTemp());

		
		TextView rinse_length = (TextView) getView().findViewById(R.id.rinseLength);
		TextView rinse_temp = (TextView) getView().findViewById(R.id.rinseTemperature);
		TextView rinse_ag = (TextView) getView().findViewById(R.id.rinseAgitation);
		TextView rinse_steam = (TextView) getView().findViewById(R.id.rinseSteam);
		
		Rinse rinse = (Rinse) program.getRinseCycle();
		rinse_length.setText("Length: " + rinse.getLength());
		rinse_temp.setText("Temperature: " + rinse.getTemp().getLabel());

		

		TextView spin_length = (TextView) getView().findViewById(R.id.spinLength);
		TextView spin_speed = (TextView) getView().findViewById(R.id.spinSpeed);
		TextView spin_steam = (TextView) getView().findViewById(R.id.spinSteam);
		
		Spin spin = (Spin) program.getSpinCycle();
		spin_length.setText("Length: " + spin.getLength());
		spin_speed.setText("Spin speed: " + spin.getSpinSpeed().getLabel());
	}


}
