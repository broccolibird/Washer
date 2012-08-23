package com.freescale.iastate.washer;


import com.freescale.iastate.washer.util.MenuInterface;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SearchActivity extends Activity implements MenuInterface {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stainmenu);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		ListView maintList = (ListView) findViewById(R.id.stainListView);

		Resources res = getResources();

		// Maintenance string arrays are stored in res/maintenance.xml
		String[] maintTypes = res.getStringArray(R.array.stain_type);
		final String[] maintData = res.getStringArray(R.array.stain_info);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, maintTypes);

		maintList.setAdapter(adapter);
		
		//sets the textview on the right to the maintenance string element corresponding to the same position selected from the list
		maintList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView maintText = (TextView) findViewById(R.id.stainTextView);
				maintText.setTextSize(18);
				maintText.setText(maintData[position]);
			}
		});
		rootIntent.setHelpText(getText(R.string.stain_help));
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
}
