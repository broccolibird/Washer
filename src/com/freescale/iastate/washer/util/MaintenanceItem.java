package com.freescale.iastate.washer.util;

public class MaintenanceItem {
	public String type;
	public String title;
	public String[] description;
	public String source;
	public String source_url;
	
	public MaintenanceItem(String type, String title,
			String description, String source,
			String source_url) {
		this.type = type;
		this.title = title;
		
		if(description != null)
			this.description = description.split("[;]");
		else
			this.description = null;
		
		this.source = source;
		this.source_url = source_url;
		
	}
	
	public MaintenanceItem(String type, String title,
			String[] description, String source,
			String source_url) {
		this.type = type;
		this.title = title;
		this.description = description;
		this.source = source;
		this.source_url = source_url;
	}
	
	public String getDescriptionListString() {
		if(description == null)
			return null;
		
		int numItems = description.length;
		
		String toReturn = "";
		for(int i = 0; i < numItems; i++) {
			toReturn += description[i] +"; ";
		}
		
		int lastIndex = toReturn.lastIndexOf(',');
		toReturn = toReturn.substring(0, (lastIndex > 0)?lastIndex:0);
		
		return toReturn;
	}
	
	public String getDescriptionString() {
		if(description == null)
			return null;
		
		int numItems = description.length;
		
		String toReturn = "";
		for(int i = 0; i < numItems; i++) {
			toReturn += description[i];
			if(i != numItems - 1)
				toReturn += "\n";
		}
		return toReturn;
	}
	
}