package com.freescale.iastate.washer;

import com.freescale.iastate.washer.data.ProgramDataSource;
import com.freescale.iastate.washer.progress.ProgressFragment;
import com.freescale.iastate.washer.progress.TimerFragment;
import com.freescale.iastate.washer.util.MenuInterface;
import com.freescale.iastate.washer.util.Program;
import com.freescale.iastate.washer.util.Rinse;
import com.freescale.iastate.washer.util.Spin;
import com.freescale.iastate.washer.util.Wash;
import com.freescale.iastate.washer.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressActivity extends Activity implements MenuInterface, TimerFragment.PercentDoneListener{

	private ProgramDataSource datasource;
	private String selection;
	private int index;
	private Program program = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress);
		
		ActionBar actionBar = getActionBar();
		
		
		final Button cancelButton = (Button) findViewById(R.id.buttonCancelWash);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		// Unpackage intent to retrieve user selection
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		selection = bundle.getString("selection");
		if(selection != null){
			datasource = new ProgramDataSource(this);
			datasource.open();
			
			// Retrieve selected program from database
			program = datasource.nameToProgram(selection);
			datasource.close();
			program.setSoilLevel(bundle.getInt("soil_level"));
			program.setLoadSize(bundle.getInt("load_size"));
		}else{
			program = bundle.getParcelable("program");
			selection = program.getName();
		}
	
		
		TimerFragment timer = (TimerFragment) getFragmentManager().findFragmentById(R.id.programTimer);
		ProgressFragment progress = (ProgressFragment) getFragmentManager().findFragmentById(R.id.programProgress);
		progress.updateProgramView(program);
		timer.createCountdown(program.getLength());
		//createCountdown();
		
		rootIntent.setHelpText(getText(R.string.progress_help));
	}
	

	
//	private void createCountdown(){
//		int program_length = program.getLength();
//		
//		final TextView program_timer = (TextView) findViewById(R.id.programTimer);
//		
//		new CountDownTimer(minutesToMillis(program_length), 1000) {
//
//		     @Override
//			public void onTick(long millisUntilFinished) {
//		         program_timer.setText(millisToTimeString(millisUntilFinished));
//		     }
//
//		     @Override
//			public void onFinish() {
//		         program_timer.setText("program done!");
//		     }
//		  }.start();
//	}
//	
//	private String millisToTimeString(long millis){
//		long total_seconds = millis / 1000;
//		
//		long minutes = total_seconds / 60;
//		long seconds = total_seconds % 60;
//		
//		String second_string = (seconds < 10) ? "0" + seconds : "" + seconds;
//		
//		return minutes + ":" + second_string;
//	}
//	
//	private int minutesToMillis(int minutes){
//		return 1000 * (minutes * 60);
//	}
		
	@Override
	protected void onDestroy() {
	    super.onDestroy();
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

	@Override
	public void percentDoneUpdated(int percent) {
		ProgressBar prog_bar = (ProgressBar) findViewById(R.id.progbar);
		prog_bar.setProgress(percent);
		
	}
}
