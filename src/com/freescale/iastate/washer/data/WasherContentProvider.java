package com.freescale.iastate.washer.data;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class WasherContentProvider extends ContentProvider {

	private WasherDatabaseHandler db;
	
	// USED for UriMatcher
	private static final int STAINS = 5;
	private static final int STAINS_ID = 6;
	
	private static final String AUTHORITY = "com.freescale.iastate.washer.contentprovider";
	private static final String BASE_PATH = "stains";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
	
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/stains";
	public static final String CONTENT_ITE_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/stain";
	
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH, STAINS);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", STAINS_ID);
	}

	@Override
	public boolean onCreate() {
		db = new WasherDatabaseHandler(getContext());
		
		return false;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		
		checkColumns(projection);
		
		queryBuilder.setTables(StainDataSource.TABLE);
		
		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
		case STAINS:
			break;
		case STAINS_ID:
			queryBuilder.appendWhere(StainDataSource.ID + "="
					+ uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Unkown URI: " + uri);
		}
		
		SQLiteDatabase database = db.getWritableDatabase();
		Cursor cursor = queryBuilder.query(database, projection, selection,
				selectionArgs, null, null, sortOrder);
		
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = db.getWritableDatabase();
		int rowsDeleted = 0;
		long id = 0;
		switch (uriType) {
		case STAINS:
			id = sqlDB.insert(StainDataSource.TABLE, null, values);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(BASE_PATH + "/" + id);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = db.getWritableDatabase();
		int rowsDeleted = 0;
		switch (uriType) {
		case STAINS:
			rowsDeleted = sqlDB.delete(StainDataSource.TABLE, selection, selectionArgs);
			break;
		case STAINS_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(StainDataSource.TABLE,
						StainDataSource.ID + "=" + id,
						null);
			} else {
				rowsDeleted = sqlDB.delete(StainDataSource.TABLE,
						StainDataSource.ID + "=" + id
						+ " and " + selection,
						selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = db.getWritableDatabase();
		int rowsUpdated = 0;
		switch (uriType) {
		case STAINS:
			rowsUpdated = sqlDB.update(StainDataSource.TABLE, values, selection, selectionArgs);
			break;
		case STAINS_ID: 
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(StainDataSource.TABLE,
						values, StainDataSource.ID + "=" + id,
						null);
			} else {
				rowsUpdated = sqlDB.update(StainDataSource.TABLE, 
						values, 
						StainDataSource.ID + "=" + id
						+ " and " + selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

	private void checkColumns(String[] projection) {
		if(projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
			
			HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(StainDataSource.allColumns));
			if(!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException("Unknown columns in projection");
			}
		}
	}
}
