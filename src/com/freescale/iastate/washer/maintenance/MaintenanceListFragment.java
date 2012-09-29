package com.freescale.iastate.washer.maintenance;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.freescale.iastate.washer.R;
import com.freescale.iastate.washer.data.MaintenanceDataSource;
import com.freescale.iastate.washer.util.MaintenanceItem;

public class MaintenanceListFragment extends ListFragment {

	private CursorAdapter adapter;
	private String columns[] = {MaintenanceDataSource.COL_TYPE, 
			MaintenanceDataSource.COL_TITLE, MaintenanceDataSource.ID};
	private Context context;
	private OnMaintenanceItemSelectedListener maintenanceSelectListener;
	private MaintenanceDataSource source;
	private Cursor cursor;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.listfragment, container, false);

	}
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = getActivity().getApplicationContext();
		
		source = new MaintenanceDataSource(context);
		source.open();
		
		Bundle extras = getActivity().getIntent().getExtras();
		if( extras != null) {
			String type = extras.getString("query");	
			cursor = source.getMaintenanceItemByType(type);
		} else {
			cursor = source.getAllMaintenanceItems();
		}
		
		getActivity().startManagingCursor(cursor);
		int textLocations[] = {R.id.text1, R.id.text2 };
		adapter = new SimpleCursorAdapter(context,
				R.layout.stainlistitem, cursor, columns, textLocations);
		setListAdapter(adapter);
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            maintenanceSelectListener = (OnMaintenanceItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnStainSelectedListener");
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
	
}
