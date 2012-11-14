package com.freescale.iastate.washer.maintenance;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
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
import com.freescale.iastate.washer.data.MaintenanceDataSource;
import com.freescale.iastate.washer.data.WasherContentProvider;
import com.freescale.iastate.washer.util.MaintenanceItem;

public class MaintenanceListFragment extends ListFragment 
					implements LoaderManager.LoaderCallbacks<Cursor>{

	private CursorAdapter adapter;
	private String columns[] = {MaintenanceDataSource.COL_TYPE, 
			MaintenanceDataSource.COL_TITLE, MaintenanceDataSource.ID};
	private OnMaintenanceItemSelectedListener maintenanceSelectListener;
	private MaintenanceDataSource source;
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
		
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
		MenuItem searchViewMI = menu.findItem(R.id.menu_search);
		SearchView searchView = (SearchView) searchViewMI.getActionView();
		searchView.setOnQueryTextListener(queryListener);
		searchView.setIconifiedByDefault(false);
		searchViewMI.setVisible(true);
	}
	
	@Override
	public void onStart(){
		super.onStart();
		
		source = new MaintenanceDataSource(getActivity().getApplicationContext());
		source.open();

		Bundle extras = getActivity().getIntent().getExtras();
		if( extras != null) {
			String type = extras.getString("query");	
			cursor = source.getMaintenanceItemByType(type);
		} else {
			cursor = source.getAllMaintenanceItems();
		}
		
		int textLocations[] = {R.id.text2, R.id.text1 };
		adapter = new SimpleCursorAdapter(getActivity(),
				R.layout.stainlistitem, cursor, columns, textLocations, 0);
		setListAdapter(adapter);
		
		
	}
	
	public void onStop() {
		super.onStop();
		
		cursor.close();
		source.close();
		
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            maintenanceSelectListener = (OnMaintenanceItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnMaintenanceSelectedListener");
        }
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		Cursor cursor = adapter.getCursor();
		cursor.moveToPosition(pos);
		MaintenanceItem mi = MaintenanceDataSource.cursorToMaintenanceItem(cursor);
		maintenanceSelectListener.onMaintenanceItemSelected(mi); 
	}
	
	public interface OnMaintenanceItemSelectedListener {
		public void onMaintenanceItemSelected(MaintenanceItem mi);
	}
	
	final private OnQueryTextListener queryListener = new OnQueryTextListener() {       

	    @Override
	    public boolean onQueryTextChange(String newText) {
	        if (TextUtils.isEmpty(newText)) {
	            getActivity().getActionBar().setSubtitle("List");               
	            currentQuery = null;
	        } else {
	            getActivity().getActionBar().setSubtitle("Searching for: " + newText);
	            currentQuery = newText;

	        }   
	        
	        getLoaderManager().restartLoader(0, null, MaintenanceListFragment.this); 
	        
	        return false;
	    }

	    @Override
	    public boolean onQueryTextSubmit(String query) {            
	        Toast.makeText(getActivity(), "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
	        return false;
	    }
	};

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		
		String whereClause = "";
		String[] selectionArgs = null;		
		if (!TextUtils.isEmpty(currentQuery)) {
			whereClause += MaintenanceDataSource.COL_TITLE + " LIKE ?";
			selectionArgs  = new String[] {"%" + currentQuery + "%"};
			return new CursorLoader(getActivity(), WasherContentProvider.MI_CONTENT_URI, 
					MaintenanceDataSource.allColumns, whereClause, selectionArgs, null);
			
		}
		return new CursorLoader(getActivity(),
				WasherContentProvider.MI_CONTENT_URI, MaintenanceDataSource.allColumns, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		adapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
		
	}
		
}
