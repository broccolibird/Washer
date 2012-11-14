package com.freescale.iastate.washer.util;

import android.os.Parcelable;

public abstract class Cycle implements Parcelable{
	
	public enum Temperature{
		HOT (4, "Hot"), 
		WARM (3, "Warm"), 
		COOL (2, "Cool"), 
		COLD (1, "Cold"),
		NONE (0, "None");
	
		private int id;
		private String label;
		
		private Temperature(int id, String label){
			this.id = id;
			this.label = label;
		}
		
		public int getID(){	return id;}
		public String getLabel(){ return label;}
	};
	
	public enum Level{
		NONE (0, "None"),
		LOW (1, "Low"),
		MEDIUM (2, "Medium"), 
		HIGH (3, "High"), 
		MAX (4, "Max");
		
		private int id;
		private String label;
		
		private Level(int id, String label){
			this.id = id;
			this.label = label;
		}
		
		public int getID(){ return id;}
		public String getLabel(){return label;}
	};
	
	public enum Size{
		XS (0, "XSmall"), 
		SMALL (1, "Small"),  
		MEDIUM (2, "Medium"), 
		LARGE (3, "Large"),
		XL (4, "XLarge");
		
		private int id;
		private String label;
		
		private Size(int id, String label){
			this.id = id;
			this.label = label;
		}
		
		public int getID(){ return id;}

		public CharSequence getLabel() { return label;}
	};
	
	protected int length;
	
	
	public Cycle(int length){
		this.length = length;
	}
	
	public Cycle() {
	}

	public int getLength(){
		return length;
	}
	
	public void setLength(int length){
		this.length = length;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
