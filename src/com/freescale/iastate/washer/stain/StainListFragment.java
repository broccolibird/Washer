package com.freescale.iastate.washer.stain;

import android.app.Activity;
import android.app.ListFragment;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.freescale.iastate.washer.R;
import com.freescale.iastate.washer.data.StainDataSource;
import com.freescale.iastate.washer.util.Stain;

public class StainListFragment extends ListFragment{

	private CursorAdapter adapter;
	private String columns[] = {StainDataSource.COL_TYPE, 
			StainDataSource.COL_FABRIC, StainDataSource.ID};
	private OnStainSelectedListener stainSelectListener;
	private StainDataSource source;
	private Cursor cursor;
	
	private String currentQuery = null;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.listfragment, container, false);

	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.actionbar, menu);
		
		MenuItem searchViewMI = menu.findItem(R.id.menu_search);
		searchViewMI.setVisible(true);
		SearchView searchView = (SearchView) searchViewMI.getActionView();
		searchView.setOnQueryTextListener(queryListener);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		source = new StainDataSource(getActivity().getApplicationContext());
		source.open();
		
		Intent intent = getActivity().getIntent();
		Bundle extras = intent.getExtras();
		if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			cursor = source.searchForStains(query);
		}else if( extras != null) {
			String fabric = extras.getString("fabric");
			cursor = source.getStainByFabric(fabric);
		} else {
			cursor = source.getAllStains();
		}
		
		getActivity().startManagingCursor(cursor);
		int textLocations[] = {R.id.text1, R.id.text2 };
		adapter = new SimpleCursorAdapter(getActivity().getApplicationContext(),
				R.layout.stainlistitem, cursor, columns, textLocations);
		setListAdapter(adapter);
	
	}
	
	public void onDestroy() {
		super.onDestroy();
		
		cursor.close();
		source.close();
		
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            stainSelectListener = (OnStainSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnStainSelectedListener");
        }
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		Cursor cursor = adapter.getCursor();
		cursor.moveToPosition(pos);
		Stain stain = StainDataSource.cursorToStain(cursor);
		stainSelectListener.onStainSelected(stain); 
	}
	
	public interface OnStainSelectedListener {
		public void onStainSelected(Stain stain);
	}
	
	final private OnQueryTextListener queryListener = new OnQueryTextListener() {       

	    @Override
	    public boolean onQueryTextChange(String newText) {
	        if (TextUtils.isEmpty(newText)) {
	            getActivity().getActionBar().setSubtitle("List");               
	            currentQuery = null;
	        } else {
	            getActivity().getActionBar().setSubtitle("List - Searching for: " + newText);
	            currentQuery = newText;

	        }   
	        
	        //getLoaderManager().restartLoader(0, null, StainListFragment.this); 
	        
	        return false;
	    }

	    @Override
	    public boolean onQueryTextSubmit(String query) {            
	        Toast.makeText(getActivity(), "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
	        return false;
	    }
	};
	
}
