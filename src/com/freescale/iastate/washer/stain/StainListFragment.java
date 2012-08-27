package com.freescale.iastate.washer.stain;

import com.freescale.iastate.washer.R;
import com.freescale.iastate.washer.data.StainDataSource;
import com.freescale.iastate.washer.util.Stain;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class StainListFragment extends ListFragment {

	private CursorAdapter adapter;
	private String columns[] = {StainDataSource.COL_TYPE, 
			StainDataSource.COL_FABRIC, StainDataSource.ID};
	private Context context;
	private OnStainSelectedListener stainSelectListener;
	private StainDataSource source;
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
		
		source = new StainDataSource(context);
		source.open();
		cursor = source.getAllStains(columns);
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
            stainSelectListener = (OnStainSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnStainSelectedListener");
        }
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		//Toast.makeText(context, test.toString(), Toast.LENGTH_SHORT).show(); 
		Cursor cursor = adapter.getCursor();
		cursor.moveToPosition(pos);
		//(SQLiteCursor) getListView().getItemAtPosition(pos);
		//SQLiteCursor cursor = (SQLiteCursor) adapter.getItem(pos);
		Stain stain = StainDataSource.cursorToStain(cursor);
		stainSelectListener.onStainSelected(stain); 
	}
	
	public interface OnStainSelectedListener {
		public void onStainSelected(Stain stain);
	}
	
}
