package com.freescale.iastate.washer.customprogram;

import com.freescale.iastate.washer.CustomProgramActivity;
import com.freescale.iastate.washer.R;
import com.freescale.iastate.washer.util.Cycle.*;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ProgramSettingsFragment extends Fragment {

	Level soil_level;
	Size load_size;
	
	RadioGroup soil_level_grp;
	RadioGroup load_size_grp;
	RadioGroup dispenser_grp;
	RadioGroup dispense_amt_grp;
	
	CustomProgramActivity act;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
		act = (CustomProgramActivity) getActivity();
		View v = inflater.inflate(R.layout.customwash, container, false);
		v = setupLayout(v);
		setWash();
		
        return v;
    }
	
	private View setupLayout(View v){
        
        // Set up Left Panel
        LinearLayout left_pane = (LinearLayout) v.findViewById(R.id.left_pane);
        left_pane.setOrientation(0);
        
        LinearLayout left1 = new LinearLayout(act);
        LinearLayout left2 = new LinearLayout(act);
        left1.setOrientation(1);
        left1.setPadding(30, 30, 30, 30);
        left2.setOrientation(1);
        left2.setPadding(30, 30, 30, 30);
        
        TextView load_size_tv = new TextView(getActivity());
        load_size_tv.setText("Load Size:");
        left1.addView(load_size_tv);
        
        //		Add Load Size Radio Group to left pane
        Size[] sizes = Size.values();
        load_size_grp = new RadioGroup(getActivity());
        OnClickListener load_size_listener = new OnClickListener() {
            public void onClick(View v) {
                selectLoadSize(v);
            }
        };
        RadioButton load_size_rb[] = new RadioButton[sizes.length];
        for(int i = 0; i < sizes.length; i++){
        	load_size_rb[i] = new RadioButton(getActivity());
        	load_size_rb[i].setText(sizes[i].getLabel());
        	load_size_rb[i].setOnClickListener(load_size_listener);
        	load_size_grp.addView(load_size_rb[i]);
        }
        left1.addView(load_size_grp);
        left_pane.addView(left1);
        
        TextView soil_level_tv = new TextView(getActivity());
        soil_level_tv.setText("Select Soil Level:");
        left2.addView(soil_level_tv);
        
        //		Add Soil Level Radio Group to left pane
        Level[] levels= Level.values();
        soil_level_grp = new RadioGroup(getActivity());
        OnClickListener soil_level_listener= new OnClickListener() {
            public void onClick(View v) {
                selectSoilLevel(v);
            }
        };
        
        RadioButton soil_level_rb[] = new RadioButton[levels.length];
        for(int i = 0; i < levels.length; i++){
        	soil_level_rb[i] = new RadioButton(getActivity());
        	soil_level_rb[i].setText(levels[i].getLabel());
        	soil_level_rb[i].setOnClickListener(soil_level_listener);
        	soil_level_grp.addView(soil_level_rb[i]);
        }
        left2.addView(soil_level_grp); 
        left_pane.addView(left2);
                        
        return v;
	}
	
	private void setWash(){
		
		load_size = act.getLoadSize();
		if(load_size != null){
			int count = load_size_grp.getChildCount();
			for (int i=0; i<count; i++) {
	            RadioButton rb = (RadioButton) load_size_grp.getChildAt(i);
	            if(((String)load_size.getLabel()).compareTo((String) rb.getText()) == 0){
	            	rb.setChecked(true);
	            }
	        }
		}
		
		soil_level = act.getSoilLevel();
		if(soil_level != null){
			int count = soil_level_grp.getChildCount();
			for (int i=0; i<count; i++) {
	            RadioButton rb = (RadioButton) soil_level_grp.getChildAt(i);
	            if(soil_level.getLabel().compareTo((String) rb.getText()) == 0){
	            	rb.setChecked(true);
	            }
	        }
		}
		
		
	}
	
	private void selectLoadSize(View v){
		RadioButton button = (RadioButton) v;
	    String text = (String) button.getText();
	    
	    Size sizes[] = Size.values();
	    for(int i = 0; i < sizes.length; i++){
	    	if(text.compareTo((String)sizes[i].getLabel()) == 0){
	    		load_size = sizes[i];
	    		act.setLoadSize(load_size);
	    	}
	    }
	    
	}
	
	private void selectSoilLevel(View v){
		RadioButton button = (RadioButton) v;
	    String text = (String) button.getText();
	    
	    Level levels[] = Level.values();
	    for(int i = 0; i < levels.length; i++){
	    	if(text.compareTo(levels[i].getLabel()) == 0){
	    		soil_level = levels[i];
	    		act.setSoilLevel(soil_level);
	    		break;
	    	}
	    }
	    
	}

}
