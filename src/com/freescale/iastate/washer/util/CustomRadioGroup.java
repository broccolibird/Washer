package com.freescale.iastate.washer.util;

import android.widget.CompoundButton;
import android.widget.RadioButton;

public class CustomRadioGroup implements RadioButton.OnCheckedChangeListener {  
	 
	private CompoundButton checkedButton = null;
	
	
	/**
	 * Add radio button to group
	 * 
	 * @param compoundButton - new RadioButton
	 */
	public void addRadioButton(CompoundButton compoundButton) {    
		compoundButton.setOnCheckedChangeListener(this);   
	}  
	  
	
	/**
	 * Called when the checked state of a compound button has changed
	 * 
	 * @param buttonView - button with changed status
	 * @param isChecked - new status of button with changed status
	 */
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {  
		if (isChecked) {  
			if(checkedButton != null)
				checkedButton.setChecked(false);  
			checkedButton = buttonView;
			checkedButton.setChecked(true);
			
		}     
	 } 
	 
	/**
	 * Returns the selected RadioButton
	 * @return the selected RadioButton
	 */
	 public CompoundButton getCheckedRadioButton() {  
		 return checkedButton;  
	 }
 
} 
