
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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

public class CustomWashFragment extends Fragment {

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
		View v = inflater.inflate(R.layout.customwash, container, false);
		v = setupLayout(v);
		setWash();
		
        return v;
    }
	
	private View setupLayout(View v){
                
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

}
