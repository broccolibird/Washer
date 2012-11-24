
package com.freescale.iastate.washer;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.freescale.iastate.washer.data.ProgramDataSource;
import com.freescale.iastate.washer.dialmenu.DialFragment;
import com.freescale.iastate.washer.util.Cycle;
import com.freescale.iastate.washer.util.MenuInterface;
import com.freescale.iastate.washer.util.Program;


public class WasherActivity  extends Activity implements MenuInterface {

	float loadSizeStart = 0;
	float loadSizeEnd = 4;
	int loadSize = 2;
	
	float soilLevelStart = 0;
	float soilLevelEnd = 4;
	int soilLevel = 2;
	
	TextView loadSizeText;
	TextView soilLevelText;
	Switch steamSwitch;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		initIntents();
        rootIntent.setHelpText("Wash Menu", getText(R.string.main_help));
        
        loadSizeText = (TextView) findViewById(R.id.loadSizeText);
        loadSizeText.setText("Load Size: Medium");
        
        SeekBar loadSizeSeek = (SeekBar) findViewById(R.id.loadSizeSeek);
        loadSizeSeek.setProgress((int)(100/(loadSizeEnd-loadSizeStart)*loadSize));
        loadSizeSeek.setOnSeekBarChangeListener( new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				float temp = progress;
				float dis = loadSizeEnd-loadSizeStart;
				loadSize = (int) FloatMath.ceil((loadSizeStart + ((temp/100)*dis)));
				
				String size = (String) Cycle.Size.values()[loadSize].getLabel();
				loadSizeText.setText("Load Size: " + size);
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {	}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
        soilLevelText = (TextView) findViewById(R.id.soilLevelText);
        soilLevelText.setText("Soil Level: Medium");
        
        SeekBar soilLevelSeek = (SeekBar) findViewById(R.id.soilLevelSeek);
        soilLevelSeek.setProgress((int)(100/(soilLevelEnd-soilLevelStart)*soilLevel));
        soilLevelSeek.setOnSeekBarChangeListener( new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				float temp = progress;
				float dis = soilLevelEnd-soilLevelStart;
				soilLevel = (int) FloatMath.ceil((soilLevelStart + ((temp/100)*dis)));
				
				String level = (String) Cycle.Level.values()[soilLevel].getLabel();
				soilLevelText.setText("Soil Level: " + level);
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {	}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
        steamSwitch = (Switch) findViewById(R.id.steamSwitch);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return rootIntent.onOptionsItemSelected(this, item);
	}
	
	public void customizeProgram(View v){
		DialFragment dialFrag = (DialFragment) getFragmentManager().findFragmentById(R.id.dialfragment);
		String selection = dialFrag.getSelectedButton();
		
		Intent customWash= new Intent(this, CustomProgramActivity.class);
		
		if(selection != null){
			
			// Retrieve wash program from database
			ProgramDataSource datasource = new ProgramDataSource(this);
			datasource.open();
			Program program = datasource.nameToProgram(selection);
			datasource.close();
			
			// customize the wash to user selections
			program.setLoadSize(loadSize);
			program.setSoilLevel(soilLevel);
			program.setSteam(steamSwitch.isChecked());
					
			if(program != null){
				customWash.putExtra("program", program);
			}
		}
		

		// start the CustomProgramActivity
				
		this.startActivity(customWash);
	}
	
	/**
	 * Create intents for the MenuInterface
	 */
	public void initIntents() {
		rootIntent.homeIntent = new Intent(this, WasherActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		rootIntent.stainIntent = new Intent(this, StainViewActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		rootIntent.maintenanceIntent = new Intent(this, MaintenanceActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		rootIntent.weatherIntent = new Intent(this, WeatherActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		//rootIntent.settingsIntent =  new Intent(this, SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		//rootIntent.testIntent = new Intent(this, StainViewActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

	}
	
	public void startWash(String selection) {
//		SQLiteDatabase.deleteDatabase(new File(WasherDatabaseHandler.DB_PATH+"/"+WasherDatabaseHandler.DB_NAME));
		if( selection == null ) {
			Toast.makeText(this, R.string.no_selection, Toast.LENGTH_SHORT).show();
		} else {
			
			// Retrieve wash program from database
			ProgramDataSource datasource = new ProgramDataSource(this);
			datasource.open();
			Program program = datasource.nameToProgram(selection);
			datasource.close();
			
			// customize the wash to user selections
			program.setLoadSize(loadSize);
			program.setSoilLevel(soilLevel);
			program.setSteam(steamSwitch.isChecked());
			
			// start the progress activity
			Intent startWash= new Intent(this, ProgressActivity.class);
			startWash.putExtra("program", program);

			this.startActivity(startWash);
		}
	}
}
