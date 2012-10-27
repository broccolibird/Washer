
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
import android.widget.Switch;
import android.widget.TextView;

public class CustomSpinFragment extends Fragment {

	Temperature temperature;
	Level speed;
	Switch steam_switch;
	Switch presoak_switch;
	
	RadioGroup speed_grp;
	CustomProgramActivity act;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
		act = (CustomProgramActivity) getActivity();
		View v = inflater.inflate(R.layout.customwash, container, false);
		v = setupLayout(v);
		setSpin();
		
        return v;
    }
	
	private View setupLayout(View v){
        
        // Set up Left Panel
        LinearLayout left_pane = (LinearLayout) v.findViewById(R.id.left_pane);
        
        
        // Set up right panel
        LinearLayout right_pane = (LinearLayout) v.findViewById(R.id.right_pane);
               
        // 		Add speed radio group to right pane
        TextView speed_tv = new TextView(getActivity());
        speed_tv.setText("Select Agitation Level:");
        right_pane.addView(speed_tv);
        
        Level[] agitation_levels = Level.values();
        speed_grp = new RadioGroup(getActivity());
        OnClickListener agitation_listener = new OnClickListener() {
            public void onClick(View v) {
                selectSpeed(v);
            }
        };
        RadioButton speed_rb[] = new RadioButton[agitation_levels.length];
        for(int i = 0; i < agitation_levels.length; i++){
        	speed_rb[i] = new RadioButton(getActivity());
        	speed_rb[i].setText(agitation_levels[i].getLabel());
        	speed_rb[i].setOnClickListener(agitation_listener);
        	speed_grp.addView(speed_rb[i]);
        }
        right_pane.addView(speed_grp);
        
        return v;
	}
	
private void setSpin(){
		
		Level ag = act.getSpinSpeed();
		if(ag != null){
			int count = speed_grp.getChildCount();
			for (int i=0; i<count; i++) {
	            RadioButton rb = (RadioButton) speed_grp.getChildAt(i);
	            if(ag.getLabel().compareTo((String) rb.getText()) == 0){
	            	rb.setChecked(true);
	            }
	        }
		}
	}
	
	private void selectSpeed(View v){
		RadioButton button = (RadioButton) v;
	    String text = (String) button.getText();
	    
	    Level levels[] = Level.values();
	    for(int i = 0; i < levels.length; i++){
	    	if(text.compareTo(levels[i].getLabel()) == 0){
	    		speed = levels[i];
	    		act.setSpinSpeed(speed);
	    		break;
	    	}
	    }
	    
	}

}
