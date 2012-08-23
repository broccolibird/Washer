package com.freescale.iastate.washer.dialmenu;

import com.freescale.iastate.washer.dialmenu.DialModel.Listener;

import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DialMenu {
	
	Context context;
	String[] menu_options;
	int num_options;
	String selected_option = null;
	
	DialRadioGroup rg;
	RadioButton[] button;
	
	DialView dial;
	
	/**
	 * Creates a new DialMenu instance
	 * 
	 * @param context
	 * @param left_buttons
	 * @param right_buttons
	 * @param dial_pane
	 * @param menu_options
	 * @param tv
	 */
	public DialMenu(Context context, LinearLayout left_buttons, 
					LinearLayout right_buttons, RelativeLayout dial_pane,
					String[] menu_options, TextView tv){
		this.context = context;
        this.menu_options = menu_options;
        
        //find number of menu items to display
        num_options = menu_options.length;      
        
        rg = new DialRadioGroup(this, tv);
        
        addButtons(left_buttons, right_buttons);
        
        dial = new DialView(context, num_options);
        dial.getModel().addListener((Listener) context);
        dial_pane.addView(dial);
    }
	
	/**
	 * Adds buttons to the layout
	 * @param left_buttons - LinearLayout holding half of the buttons
	 * @param right_buttons - LinearLayout holding half of the buttons
	 */
	public void addButtons(LinearLayout left_buttons, LinearLayout right_buttons){
		
        button = new RadioButton[num_options];
        
        //divide buttons in half for each side
        int half = num_options/2;  
		
        //Create first half of buttons and add to left panel
		int i = half-1;
        for(; i >= 0; i--){
        	button[i] = new RadioButton(context);
        	button[i].setText(menu_options[i]);
	        button[i].setLayoutParams(new LayoutParams(250,
	        				ViewGroup.LayoutParams.WRAP_CONTENT));
	        left_buttons.addView(button[i]);
	        rg.addRadioButton(button[i]);
        }
        
        //Create second half of buttons and add to right panel
        for(i = half; i < num_options; i++){
        	button[i] = new RadioButton(context);
        	button[i].setText(menu_options[i]);
	        button[i].setLayoutParams(new LayoutParams(250,
	        				ViewGroup.LayoutParams.WRAP_CONTENT));
	        right_buttons.addView(button[i]);
	        rg.addRadioButton(button[i]);
        }
	}
	
	/**
	 * Selects the correct radio button and sets the menu's selected option
	 * @param selection - index of selected option
	 * @param tv - reference of textview to be updated with selection
	 */
	public void selectRadioButton(int selection, TextView tv){
		//selected_option = (String) button[selection].getText();
		rg.onCheckedChanged(button[selection], true);
	}
	
	/**
	 * Returns the option currently selected in the menu
	 * 
	 * @return selected_option
	 */
	public String getSelectedOption(){
		return selected_option;
	}

	/**
	 * Set the menu's current selected option
	 *  -- called when a button is selected directly
	 * 
	 * @param selected_option
	 */
	public void setSelectedOption(String selected_option){
		this.selected_option = selected_option;
		
		// re-draw the dial in the correct location
		dial.invalidate();
	}
}
