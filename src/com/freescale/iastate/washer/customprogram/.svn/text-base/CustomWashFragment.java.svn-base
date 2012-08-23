package com.freescale.iastate.washer.customprogram;

import com.freescale.iastate.washer.CustomProgramActivity;
import com.freescale.iastate.washer.R;
import com.freescale.iastate.washer.dialmenu.DialView;
import com.freescale.iastate.washer.dialmenu.DialModel.Listener;
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

public class CustomWashFragment extends Fragment {

	DialView dial;
	Temperature temperature;
	Level agitation;
	Switch steam_switch;
	Switch presoak_switch;
	
	RadioGroup temperature_grp;
	RadioGroup agitation_grp;
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
        
        TextView presoak_tv = new TextView(getActivity());
        presoak_tv.setText("Presoak:");
        left_pane.addView(presoak_tv);
        
        presoak_switch = new Switch(getActivity());
        presoak_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				act.setPresoak(isChecked);
			}
		});
        left_pane.addView(presoak_switch);
        
        TextView temperature_tv = new TextView(getActivity());
        temperature_tv.setText("Select Wash Temperature:");
        left_pane.addView(temperature_tv);
        
        //		Add Temperature Radio Group to left pane
        Temperature[] water_temps = Temperature.values();
        temperature_grp = new RadioGroup(getActivity());
        OnClickListener temperature_listener = new OnClickListener() {
            public void onClick(View v) {
                selectTemperature(v);
            }
        };
        RadioButton temperature_rb[] = new RadioButton[water_temps.length];
        for(int i = 0; i < water_temps.length; i++){
        	temperature_rb[i] = new RadioButton(getActivity());
        	temperature_rb[i].setText(water_temps[i].getLabel());
        	temperature_rb[i].setOnClickListener(temperature_listener);
        	temperature_grp.addView(temperature_rb[i]);
        }
        left_pane.addView(temperature_grp); 
        
        
        // Set up right panel
        LinearLayout right_pane = (LinearLayout) v.findViewById(R.id.right_pane);
        
        //		Add steam TextView and Switch to right pane
        TextView steam_tv = new TextView(getActivity());
        steam_tv.setText("Steam:");
        right_pane.addView(steam_tv);
        
        steam_switch = new Switch(getActivity());
        steam_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				act.setWashSteam(isChecked);
			}
		});
        right_pane.addView(steam_switch);
        
        // 		Add agitation radio group to right pane
        TextView agitation_tv = new TextView(getActivity());
        agitation_tv.setText("Select Agitation Level:");
        right_pane.addView(agitation_tv);
        
        Level[] agitation_levels = Level.values();
        agitation_grp = new RadioGroup(getActivity());
        OnClickListener agitation_listener = new OnClickListener() {
            public void onClick(View v) {
                selectAgitation(v);
            }
        };
        RadioButton agitation_rb[] = new RadioButton[agitation_levels.length];
        for(int i = 0; i < agitation_levels.length; i++){
        	agitation_rb[i] = new RadioButton(getActivity());
        	agitation_rb[i].setText(agitation_levels[i].getLabel());
        	agitation_rb[i].setOnClickListener(agitation_listener);
        	agitation_grp.addView(agitation_rb[i]);
        }
        right_pane.addView(agitation_grp);
        
        return v;
	}
	
	private void setWash(){
		
		Temperature temp = act.getWashTemp();
		if(temp != null){
			int count = temperature_grp.getChildCount();
			for (int i=0; i<count; i++) {
	            RadioButton rb = (RadioButton) temperature_grp.getChildAt(i);
	            if(temp.getLabel().compareTo((String) rb.getText()) == 0){
	            	rb.setChecked(true);
	            }
	        }
		}
		
		Level ag = act.getWashAg();
		if(ag != null){
			int count = agitation_grp.getChildCount();
			for (int i=0; i<count; i++) {
	            RadioButton rb = (RadioButton) agitation_grp.getChildAt(i);
	            if(ag.getLabel().compareTo((String) rb.getText()) == 0){
	            	rb.setChecked(true);
	            }
	        }
		}
		
		boolean steam = act.getWashSteam();
		if(steam){
			steam_switch.setChecked(steam);
		}
		
		boolean presoak = act.getPresoak();
		if(presoak){
			presoak_switch.setChecked(presoak);
		}
	}
	
	private void selectTemperature(View v){
		RadioButton button = (RadioButton) v;
	    String text = (String) button.getText();
	    
	    Temperature temperatures[] = Temperature.values();
	    for(int i = 0; i < temperatures.length; i++){
	    	if(text.compareTo(temperatures[i].getLabel()) == 0){
	    		temperature = temperatures[i];
	    		act.setWashTemp(temperature);
	    	}
	    }
	    
	}
	
	private void selectAgitation(View v){
		RadioButton button = (RadioButton) v;
	    String text = (String) button.getText();
	    
	    Level levels[] = Level.values();
	    for(int i = 0; i < levels.length; i++){
	    	if(text.compareTo(levels[i].getLabel()) == 0){
	    		agitation = levels[i];
	    		act.setWashAg(agitation);
	    		break;
	    	}
	    }
	    
	}

}
