package com.freescale.iastate.washer.dialmenu;

import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class DialRadioGroup implements RadioButton.OnCheckedChangeListener {  
	 
	private CompoundButton checkedButton = null;  
	private TextView tv;
	private DialMenu dm;
	
	/**
	 * Create a new DialRadio Group
	 * 
	 * @param dm - DialMenu
	 * @param tv - TextView
	 */
	DialRadioGroup(DialMenu dm, TextView tv){
		this.dm = dm;
		this.tv = tv;
	}
	
	/**
	 * Add radio button to group
	 * 
	 * @param rb - new RadioButton
	 */
	public void addRadioButton(RadioButton rb) {    
		rb.setOnCheckedChangeListener(this);   
	}  
	  
	@Override
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
			
			dm.setSelectedOption((String) checkedButton.getText());
			//tv.setText(checkedButton.getText());
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
