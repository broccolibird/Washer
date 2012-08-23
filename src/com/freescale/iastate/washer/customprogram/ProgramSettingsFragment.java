package com.freescale.iastate.washer.customprogram;

import com.freescale.iastate.washer.CustomProgramActivity;
import com.freescale.iastate.washer.R;
import com.freescale.iastate.washer.dialmenu.DialView;
import com.freescale.iastate.washer.dialmenu.DialModel.Listener;
import com.freescale.iastate.washer.util.Wash.Dispenser;
import com.freescale.iastate.washer.util.Cycle.*;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

public class ProgramSettingsFragment extends Fragment {

	DialView dial;
	
	Level soil_level;
	Size load_size;
	Dispenser dispenser;
	Level dispense_amt;
	
	RadioGroup soil_level_grp;
	RadioGroup load_size_grp;
	RadioGroup dispenser_grp;
	RadioGroup dispense_amt_grp;
	
	CustomProgramActivity act;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
		act = (CustomProgramActivity) getActivity();
		View v = inflater.inflate(R.layout.dial, container, false);
		v = setupLayout(v);
		setWash();
		
        return v;
    }
	
	private View setupLayout(View v){
        
        // Add Dial to View
        RelativeLayout dial_pane = (RelativeLayout) v.findViewById(R.id.dial_pane);
        dial = new DialView(act, 1);
        dial.getModel().addListener((Listener) act);
        dial_pane.addView(dial);
        
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
        
        // Set up right panel
        LinearLayout right_pane = (LinearLayout) v.findViewById(R.id.right_pane);
        right_pane.setOrientation(1);
        
        LinearLayout right1 = new LinearLayout(act);
        LinearLayout right2 = new LinearLayout(act);
        right1.setOrientation(1);
        right1.setPadding(10, 10, 10, 10);
        right2.setOrientation(1);
        right2.setPadding(30, 30, 30, 30);
        
        //		Add dispenser TextView and RadioGroup to right pane
        TextView dispenser_tv = new TextView(getActivity());
        dispenser_tv.setText("Select a Dispenser:");
        right1.addView(dispenser_tv);
        
        //		Add Dispenser Radio Group to left pane
        Dispenser[] dispensers= Dispenser.values();
        dispenser_grp = new RadioGroup(getActivity());
        OnClickListener dispenser_listener= new OnClickListener() {
            public void onClick(View v) {
                selectDispenser(v);
            }
        };
        
        RadioButton dispenser_rb[] = new RadioButton[levels.length];
        for(int i = 0; i < dispensers.length; i++){
        	dispenser_rb[i] = new RadioButton(getActivity());
        	dispenser_rb[i].setText(dispensers[i].getLabel());
        	dispenser_rb[i].setOnClickListener(dispenser_listener);
        	dispenser_grp.addView(dispenser_rb[i]);
        }
        right1.addView(dispenser_grp);
        right_pane.addView(right1);
        
        // 		Add dispenser amount radio group to right pane
        TextView dispense_amt_tv = new TextView(getActivity());
        dispense_amt_tv.setText("Select Dispense Amount:");
        right2.addView(dispense_amt_tv);
        
        dispense_amt_grp = new RadioGroup(getActivity());
        OnClickListener dispense_amt_listener = new OnClickListener() {
            public void onClick(View v) {
                selectDispenseAmt(v);
            }
        };
        RadioButton dispense_amt_rb[] = new RadioButton[levels.length];
        for(int i = 0; i < levels.length; i++){
        	dispense_amt_rb[i] = new RadioButton(getActivity());
        	dispense_amt_rb[i].setText(levels[i].getLabel());
        	dispense_amt_rb[i].setOnClickListener(dispense_amt_listener);
        	dispense_amt_grp.addView(dispense_amt_rb[i]);
        }
        right2.addView(dispense_amt_grp);
        right_pane.addView(right2);
        
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
		
		dispenser = act.getDispenser();
		if(dispenser != null){
			int count = dispenser_grp.getChildCount();
			for (int i=0; i<count; i++) {
	            RadioButton rb = (RadioButton) dispenser_grp.getChildAt(i);
	            if(((String)dispenser.getLabel()).compareTo((String) rb.getText()) == 0){
	            	rb.setChecked(true);
	            }
	        }
		}
		
		dispense_amt = act.getDispenseAmount();
		if(soil_level != null){
			int count = soil_level_grp.getChildCount();
			for (int i=0; i<count; i++) {
	            RadioButton rb = (RadioButton) dispense_amt_grp.getChildAt(i);
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
	
	private void selectDispenser(View v){
		RadioButton button = (RadioButton) v;
	    String text = (String) button.getText();
	    
	    Dispenser dispensers[] = Dispenser.values();
	    for(int i = 0; i < dispensers.length; i++){
	    	if(text.compareTo((String)dispensers[i].getLabel()) == 0){
	    		dispenser = dispensers[i];
	    		act.setDispenser(dispenser);
	    		break;
	    	}
	    }
	    
	}
	
	private void selectDispenseAmt(View v){
		RadioButton button = (RadioButton) v;
	    String text = (String) button.getText();
	    
	    Level levels[] = Level.values();
	    for(int i = 0; i < levels.length; i++){
	    	if(text.compareTo(levels[i].getLabel()) == 0){
	    		dispense_amt = levels[i];
	    		act.setDispenseAmount(dispense_amt);
	    		break;
	    	}
	    }
	    
	}

}
