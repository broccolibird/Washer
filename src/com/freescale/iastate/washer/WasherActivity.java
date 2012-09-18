package com.freescale.iastate.washer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.freescale.iastate.washer.dialmenu.DialRadioGroup;

public class WasherActivity  extends Activity {

	private ImageView dial;
	private static Bitmap imageOriginal, imageScaled;
	private static Matrix matrix;
	
	private int dialHeight = 0, dialWidth = 0;
	private int rbHeight = 0;
	
	DialRadioGroup rg;
	RadioButton button[];
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dial);
		
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
		
		dial = (ImageView) findViewById(R.id.testdial);
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
						
						
						// Place buttons
						button = new RadioButton[8];
				        
				        button[0] = (RadioButton) findViewById(R.id.radiobutton0);
				        button[1] = (RadioButton) findViewById(R.id.radiobutton1);
				        button[2] = (RadioButton) findViewById(R.id.radiobutton2);
				        button[3] = (RadioButton) findViewById(R.id.radiobutton3);
				        button[4] = (RadioButton) findViewById(R.id.radiobutton4);
				        button[5] = (RadioButton) findViewById(R.id.radiobutton5);
				        button[6] = (RadioButton) findViewById(R.id.radiobutton6);
				        button[7] = (RadioButton) findViewById(R.id.radiobutton7);
				        
				        rbHeight = button[0].getHeight();
				        rg = new DialRadioGroup();
				        
				        RelativeLayout layout = (RelativeLayout) findViewById(R.id.diallayout);
				        
				        int margin = (dialHeight-4*rbHeight)/3;
				        
				        //Create first half of buttons and add to left panel
						int i;
						LayoutParams params;
				        for(i = 0; i < 8; i++){
				        	params = (LayoutParams) button[i].getLayoutParams();
				            if( i != 3 && i != 7) {
				            	params.setMargins(0, 0, 0, margin);
				            } else {
				            	params.setMargins(0, 0, 0, 0);
				            }
				            button[i].setLayoutParams(params);
					        rg.addRadioButton(button[i]);
				        }    
				        
					}
					
				}
		
		});
		
		
	}
	
	private void rotateDial(float degrees) {
		matrix.postRotate(degrees, dialWidth / 2, dialHeight / 2);
		dial.setImageMatrix(matrix);
	}
	
	private double getAngle(double xTouch, double yTouch) {
		double x = xTouch - (dialWidth / 2d);
		double y = dialHeight - yTouch - (dialHeight / 2d);
		
		return Math.atan2(y, x) * 180 / Math.PI;
	}
	
	private class DialTouchListener implements OnTouchListener {

		private double startAngle;
		
		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction()) {
			
				case MotionEvent.ACTION_DOWN:
					startAngle = getAngle(event.getX(), event.getY());
					break;
					
				case MotionEvent.ACTION_MOVE:
					double currentAngle = getAngle(event.getX(), event.getY());
					rotateDial((float) (startAngle - currentAngle));
					startAngle = currentAngle;
					break;
			}
			
			return true;
		}
		
	}
	
}
