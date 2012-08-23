package com.freescale.iastate.washer;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.RadioButton;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;

import com.freescale.iastate.washer.customprogram.*;
import com.freescale.iastate.washer.data.ProgramDataSource;
import com.freescale.iastate.washer.dialmenu.DialModel;
import com.freescale.iastate.washer.util.Cycle.Level;
import com.freescale.iastate.washer.util.Cycle.Size;
import com.freescale.iastate.washer.util.Cycle.Temperature;
import com.freescale.iastate.washer.util.MenuInterface;
import com.freescale.iastate.washer.util.Program;
import com.freescale.iastate.washer.util.Rinse;
import com.freescale.iastate.washer.util.Spin;
import com.freescale.iastate.washer.util.Wash;
import com.freescale.iastate.washer.util.Wash.Dispenser;

public class CustomProgramActivity extends Activity implements DialModel.Listener, MenuInterface {
	
	String selection;
	private ProgramDataSource datasource;
	private Program program;
	
		
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //unpack intent
        Intent intent = getIntent();
		selection = intent.getStringExtra("selection");
		
		if(selection != null){
			datasource = new ProgramDataSource(this);
			datasource.open();
			program = datasource.nameToProgram(selection);
			datasource.close();
		}else{
			program = Program.getDefaultProgram();
		}
		
        final ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        Tab program_tab = bar.newTab()
        		.setText("Program")
        		.setTabListener(new TabListener<ProgramSettingsFragment>(
        				this, "simple", ProgramSettingsFragment.class));
        bar.addTab(program_tab);
        
        Tab wash_tab = bar.newTab()
                .setText("Wash Cycle")
                .setTabListener(new TabListener<CustomWashFragment>(
                        this, "simple", CustomWashFragment.class));
        bar.addTab(wash_tab);
        
        Tab rinse_tab = bar.newTab()
                .setText("Rinse Cycle")
                .setTabListener(new TabListener<CustomRinseFragment>(
                        this, "simple", CustomRinseFragment.class));
        bar.addTab(rinse_tab);
        
        Tab spin_tab = bar.newTab()
                .setText("Spin Cycle")
                .setTabListener(new TabListener<CustomSpinFragment>(
                        this, "simple", CustomSpinFragment.class));
        bar.addTab(spin_tab);
        
        rootIntent.setHelpText(getText(R.string.customize_help));
        
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar, menu);
		return true;
	}
	
	public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
	    private Fragment mFragment;
	    private final Activity mActivity;
	    private final String mTag;
	    private final Class<T> mClass;

	    /** Constructor used each time a new tab is created.
	      * @param activity  The host Activity, used to instantiate the fragment
	      * @param tag  The identifier tag for the fragment
	      * @param clz  The fragment's Class, used to instantiate the fragment
	      */
	    public TabListener(Activity activity, String tag, Class<T> clz) {
	        mActivity = activity;
	        mTag = tag;
	        mClass = clz;
	    }

	    /* The following are each of the ActionBar.TabListener callbacks */

	    public void onTabSelected(Tab tab, FragmentTransaction ft) {
	        // Check if the fragment is already initialized
	        if (mFragment == null) {
	            // If not, instantiate and add it to the activity
	            mFragment = Fragment.instantiate(mActivity, mClass.getName());
	            ft.add(android.R.id.content, mFragment, mTag);
	        } else {
	            // If it exists, simply attach it in order to show it
	            ft.attach(mFragment);
	        }
	    }

	    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	        if (mFragment != null) {
	            // Detach the fragment, because another one is being attached
	            ft.detach(mFragment);
	        }
	    }

	    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	        // User selected the already selected tab. Usually do nothing.
	    }
	}

	@Override
	public void onDialPositionChanged(DialModel sender, int nicksChanged) {
		// Does nothing in this context
		
	}

	@Override
	public void onDialPressed(DialModel sender) {
		// TODO Auto-generated method stub
		
		Intent intent = new Intent(this, ProgressActivity.class);
		intent.putExtra("program", program);
		 
		startActivity(intent);
	}

	public void setWashTemp(Temperature wash_temp) {
		program.getWashCycle().setTemp(wash_temp);
		
	}

	public void setWashAg(Level wash_ag) {
		program.getWashCycle().setAgitation(wash_ag);
		
	}
	
	public void setWashSteam(boolean wash_steam){
		program.getWashCycle().setSteam(wash_steam);
	}

	public void setPresoak(boolean presoak) {
		if(presoak)
			program.getWashCycle().setPresoakLength(5);
		else
			program.getWashCycle().setPresoakLength(0);
	}
	
	public Temperature getWashTemp() {
		return program.getWashCycle().getTemp();
	}
	
	public Level getWashAg() {
		return program.getWashCycle().getAgitation();
	}

	public boolean getWashSteam() {
		return program.getWashCycle().getSteam();
	}

	public boolean getPresoak() {
		int presoak_time = program.getWashCycle().getPresoakLength();
		
		if(presoak_time > 0)
			return true;
		else
			return false;
	}

	public Temperature getRinseTemp() {
		return program.getRinseCycle().getTemp();
	}
	
	public void setRinseSteam(boolean rinse_steam) {
		program.getRinseCycle().setSteam(rinse_steam);
		
	}

	public Level getRinseAg() {
		return program.getRinseCycle().getAgitation();
	}

	public boolean getRinseSteam() {
		return program.getRinseCycle().getSteam();
	}

	public void setRinseTemp(Temperature rinse_temp) {
		program.getRinseCycle().setTemp(rinse_temp);
		
	}

	public void setRinseAg(Level rinse_ag) {
		program.getRinseCycle().setAgitation(rinse_ag);
		
	}

	public boolean getSpinSteam() {
		return program.getSpinCycle().getSteam();
	}

	public Level getSpinSpeed() {
		return program.getSpinCycle().getSpinSpeed();
	}

	public void setSpinSpeed(Level spin_speed) {
		program.getSpinCycle().setSpinSpeed(spin_speed);
		
	}

	public void setSpinSteam(boolean spin_steam) {
		program.getSpinCycle().setSteam(spin_steam);
		
	}

	public Size getLoadSize() {
		return program.getLoadSize();
	}

	public Level getSoilLevel() {
		return program.getSoilLevel();
	}

	public Dispenser getDispenser() {
		return program.getWashCycle().getDispenser();
	}

	public Level getDispenseAmount() {
		return program.getWashCycle().getDispenseAmount();
	}

	public void setLoadSize(Size load_size) {
		program.setLoadSize(load_size);
		
	}

	public void setSoilLevel(Level soil_level) {
		program.setSoilLevel(soil_level);
		
	}

	public void setDispenser(Dispenser dispenser) {
		program.getWashCycle().setDispenser(dispenser);
		
	}

	public void setDispenseAmount(Level dispense_amt) {
		program.getWashCycle().setDispenseAmount(dispense_amt);
		
	}

	
	
}
