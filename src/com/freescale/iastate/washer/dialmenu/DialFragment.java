package com.freescale.iastate.washer.dialmenu;

import com.freescale.iastate.washer.R;
import com.freescale.iastate.washer.WasherActivity;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class DialFragment extends Fragment {
	
	private ImageView dial;
	private static Bitmap imageOriginal, imageScaled;
	private static Matrix matrix;
	
	private int dialHeight = 0, dialWidth = 0;
	private int rbHeight = 0;
	
	DialRadioGroup rg;
	int numButtons = 8;
	RadioButton button[];
	int startAngle[];
	int endAngle[];
	
	String menuOptions[];
	
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.dial, container);
		
		// load dial image
		if (imageOriginal == null) {
			imageOriginal = BitmapFactory.decodeResource(getResources(),
					R.drawable.dial);
		}
		
		// initialize image matrix
		if (matrix == null) {
			matrix = new Matrix();
		} else {
			matrix.reset();
		}
		
		dial = (ImageView) v.findViewById(R.id.testdial);
		dial.setOnTouchListener(new DialTouchListener());
		dial.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener () {

				public void onGlobalLayout() {
					if (dialHeight == 0 || dialWidth == 0) {
						dialHeight = dial.getHeight();
						dialWidth = dial.getWidth();
						
						// resize
						Matrix resize = new Matrix();
						resize.postScale((float)Math.min(
								dialWidth, dialHeight)/ 
								(float) imageOriginal.getWidth(),
								(float) Math.min(dialWidth, dialHeight)/
								(float) imageOriginal.getHeight());
						
						imageScaled = Bitmap.createBitmap(imageOriginal, 0,
								0, imageOriginal.getWidth(), imageOriginal.getHeight(),
								resize, false);
						
						float translateX = dialWidth / 2 -
								imageScaled.getWidth() / 2;
						float translateY = dialHeight / 2 -
								imageScaled.getHeight() / 2;
						matrix.postTranslate(translateX, translateY);
						
						dial.setImageBitmap(imageScaled);
						dial.setImageMatrix(matrix);
						
						
						// create buttons
						button = new RadioButton[numButtons];
				        
				        button[0] = (RadioButton) getActivity().findViewById(R.id.radiobutton0);
				        button[1] = (RadioButton) getActivity().findViewById(R.id.radiobutton1);
				        button[2] = (RadioButton) getActivity().findViewById(R.id.radiobutton2);
				        button[3] = (RadioButton) getActivity().findViewById(R.id.radiobutton3);
				        button[4] = (RadioButton) getActivity().findViewById(R.id.radiobutton4);
				        button[5] = (RadioButton) getActivity().findViewById(R.id.radiobutton5);
				        button[6] = (RadioButton) getActivity().findViewById(R.id.radiobutton6);
				        button[7] = (RadioButton) getActivity().findViewById(R.id.radiobutton7);
				        
				        menuOptions = getResources().getStringArray(R.array.cycle_types);
						for(int i=0; i < numButtons; i++) {
							button[i].setText(menuOptions[i]);
						}
						
				        rbHeight = button[0].getHeight();
				        
				        // add buttons to radio group
				        rg = new DialRadioGroup();
				        for(int i = 0; i < numButtons; i++) {
				        	rg.addRadioButton(button[i]);
				        }
				        
//				        RelativeLayout layout = (RelativeLayout) findViewById(R.id.diallayout);
				        
				        int yMargin = (dialHeight-4*rbHeight)/3;
				        int xMargin = -dialHeight/4+10; 
				        
				        // place buttons
				        // right side, going counter-clockwise
						int i;
						LayoutParams params;
						params = (LayoutParams) button[0].getLayoutParams();
						params.setMargins(xMargin, 0, 0, yMargin); //left, top, right, bottom
						
						button[0].setLayoutParams(params);
						
						params = (LayoutParams) button[1].getLayoutParams();
						params.setMargins(0, 0, 0, yMargin); 
						button[1].setLayoutParams(params);
						
						params = (LayoutParams) button[2].getLayoutParams();
						params.setMargins(0, 0, 0, yMargin); 
						button[2].setLayoutParams(params);
						
						params = (LayoutParams) button[3].getLayoutParams();
						params.setMargins(xMargin, 0, 0, 0); 
						button[3].setLayoutParams(params);
						
						// right side, starting at the top
						params = (LayoutParams) button[4].getLayoutParams();
						params.setMargins(0, 0, xMargin, 0); 
						button[4].setLayoutParams(params);
						
						params = (LayoutParams) button[5].getLayoutParams();
						params.setMargins(0, 0, 0, yMargin); 
						button[5].setLayoutParams(params);
						
						params = (LayoutParams) button[6].getLayoutParams();
						params.setMargins(0, 0, 0, yMargin); 
						button[6].setLayoutParams(params);
						
						params = (LayoutParams) button[7].getLayoutParams();
						params.setMargins(0, 0, xMargin, yMargin); 
						button[7].setLayoutParams(params);
				        
						// set angles corresponding to each button
				        startAngle = new int[numButtons];
				        endAngle = new int[numButtons];
				        
				        startAngle[0] = 20;
				        endAngle[0] = 35;
				        
				        startAngle[1] = 75;
				        endAngle[1] = 80;
				        
				        startAngle[2] = 110;
				        endAngle[2] = 125;
				        
				        startAngle[3] = 145;
				        endAngle[3] = 160;
				        
				        startAngle[7] = -endAngle[0];
				        endAngle[7] = -startAngle[0];
				        
				        startAngle[6] = -endAngle[1];
				        endAngle[6] = -startAngle[1];
				        
				        startAngle[5] = -endAngle[2];
				        endAngle[5] =  -startAngle[2];
				        
				        startAngle[4] = -endAngle[3];
				        endAngle[4] = -startAngle[3];
				        
				        
					}
					
				}
		
		});
		
		return v;
	}
	
	private void rotateDial(float degrees) {
		double startAngle = getCurrentDialAngle();
		
		matrix.postRotate(degrees, dialWidth / 2, dialHeight / 2);
		dial.setImageMatrix(matrix);
		
		double endAngle = getCurrentDialAngle();
		
		selectButton(startAngle, endAngle);
			
	}
	
	private void selectButton(double angle1, double angle2) {
		for(int i=0; i < numButtons; i++) {
			if(angle2 > startAngle[i] && angle2 < endAngle[i]) {
				button[i].setChecked(true);
			} 
		}
	}
	
	public String getSelectedButton() {
		return (String) rg.getCheckedRadioButton().getText();
	}
	
	private double getAngle(double xTouch, double yTouch) {
		double x = xTouch - (dialWidth / 2d);
		double y = dialHeight - yTouch - (dialHeight / 2d);
		
		return Math.atan2(y, x) * 180 / Math.PI;
	}
	
	private double getCurrentDialAngle() {
		float[] values = new float[9];
		matrix.getValues(values);
		return Math.atan2(values[3], values[4]) * 180 / Math.PI;
	}
	
	private boolean isInCenter(double xTouch, double yTouch) {
		double quarterHeight = dialHeight/4.0;
		
		if(xTouch > quarterHeight && xTouch < 3*quarterHeight &&
				yTouch > quarterHeight && yTouch < 3*quarterHeight) {
			return true;
		}
		return false;
	}
	
	private class DialTouchListener implements OnTouchListener {

		private double startAngle;
		private boolean startInCenter;
		
		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction()) {
			
				case MotionEvent.ACTION_DOWN:
					// sets the start angle of the user's finger press
					// (not the angle of the dial)
					double x = event.getX();
					double y = event.getY();
					
					startInCenter = isInCenter(x, y);
					
					startAngle = getAngle(x, y);
					break;
					
				case MotionEvent.ACTION_MOVE:
					// sets the current angle of the user's finger press
					// (not the angle of the dial)
					double currentAngle = getAngle(event.getX(), event.getY());
					rotateDial((float)(startAngle - currentAngle));
					startAngle = currentAngle;
					break;
					
				case MotionEvent.ACTION_UP:
					if(startInCenter && isInCenter(event.getX(), event.getY())) {
						((WasherActivity) getActivity())
							.startWash((String)rg.getCheckedRadioButton().getText());
					}
					break;
			}
			
			return true;
		}
		
	}
}
