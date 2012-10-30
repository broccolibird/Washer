package com.freescale.iastate.washer.data;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
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
	private static final int MAINTENANCE_ITEMS = 7;
	private static final int MAINTENANCE_ITEMS_ID = 8;
	
	private static final String AUTHORITY = "com.freescale.iastate.washer.contentprovider";
	
	private static final String STAIN_BASE_PATH = "stains";
	public static final Uri STAIN_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + STAIN_BASE_PATH);

	private static final String MI_BASE_PATH = "maintenance";
	public static final Uri MI_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + MI_BASE_PATH);
	
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, STAIN_BASE_PATH, STAINS);
		sURIMatcher.addURI(AUTHORITY, STAIN_BASE_PATH + "/#", STAINS_ID);
		sURIMatcher.addURI(AUTHORITY, MI_BASE_PATH, MAINTENANCE_ITEMS);
		sURIMatcher.addURI(AUTHORITY, MI_BASE_PATH + "/#", MAINTENANCE_ITEMS_ID);
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
				
		int uriType = sURIMatcher.match(uri);
		
		checkColumns(projection, uriType);
		
		switch (uriType) {
		case STAINS:
			queryBuilder.setTables(StainDataSource.TABLE);
			break;
		case STAINS_ID:
			queryBuilder.setTables(StainDataSource.TABLE);
			queryBuilder.appendWhere(StainDataSource.ID + "="
					+ uri.getLastPathSegment());
			break;
		case MAINTENANCE_ITEMS:
			queryBuilder.setTables(MaintenanceDataSource.TABLE);
			break;
		case MAINTENANCE_ITEMS_ID:
			queryBuilder.setTables(MaintenanceDataSource.TABLE);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
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
		
		String basePath = null;
		int rowsDeleted = 0;
		long id = 0;
		switch (uriType) {
		case STAINS:
			id = sqlDB.insert(StainDataSource.TABLE, null, values);
			basePath = STAIN_BASE_PATH;
			break;
		case MAINTENANCE_ITEMS:
			id = sqlDB.insert(MaintenanceDataSource.TABLE, null, values);
			basePath = MI_BASE_PATH;
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(basePath + "/" + id);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = db.getWritableDatabase();
		String id;
		int rowsDeleted = 0;
		switch (uriType) {
		case STAINS:
			rowsDeleted = sqlDB.delete(StainDataSource.TABLE, selection, selectionArgs);
			break;
		case STAINS_ID:
			id = uri.getLastPathSegment();
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
		case MAINTENANCE_ITEMS:
			rowsDeleted = sqlDB.delete(MaintenanceDataSource.TABLE, selection, selectionArgs);
			break;
		case MAINTENANCE_ITEMS_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(MaintenanceDataSource.TABLE,
						MaintenanceDataSource.ID + "=" + id,
						null);
			} else {
				rowsDeleted = sqlDB.delete(MaintenanceDataSource.TABLE,
						MaintenanceDataSource.ID + "=" + id
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
		
		String id;
		int rowsUpdated = 0;
		switch (uriType) {
		case STAINS:
			rowsUpdated = sqlDB.update(StainDataSource.TABLE, values, selection, selectionArgs);
			break;
		case STAINS_ID: 
			id = uri.getLastPathSegment();
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
		case MAINTENANCE_ITEMS:
			rowsUpdated = sqlDB.update(MaintenanceDataSource.TABLE, values, selection, selectionArgs);
			break;
		case MAINTENANCE_ITEMS_ID: 
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(MaintenanceDataSource.TABLE,
						values, MaintenanceDataSource.ID + "=" + id,
						null);
			} else {
				rowsUpdated = sqlDB.update(MaintenanceDataSource.TABLE, 
						values, 
						MaintenanceDataSource.ID + "=" + id
						+ " and " + selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

	private void checkColumns(String[] projection, int uriType) {
		if(projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
			HashSet<String> availableColumns;
			switch (uriType) {
			case STAINS:
			case STAINS_ID:
				availableColumns = new HashSet<String>(Arrays.asList(StainDataSource.allColumns));
				break;
			case MAINTENANCE_ITEMS:
			case MAINTENANCE_ITEMS_ID:
				availableColumns = new HashSet<String>(Arrays.asList(MaintenanceDataSource.allColumns));
				break;
			default:
				availableColumns = null;
				break;
			}
			
			System.out.println(availableColumns.toString());
			System.out.println(requestedColumns.toString());
			if(!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException("Unknown columns in projection");
			}
		}
	}
}
