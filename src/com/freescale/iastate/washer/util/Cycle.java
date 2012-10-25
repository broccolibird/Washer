package com.freescale.iastate.washer.util;

import android.os.Parcel;
import android.os.Parcelable;

import com.freescale.iastate.washer.util.Cycle.Level;

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
		MAX (4, "Max"), 
		HIGH (3, "High"), 
		MEDIUM (2, "Medium"), 
		LOW (1, "Low"), 
		NONE (0, "None");
		
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
		XL (4, "XLarge"), 
		LARGE (3, "Large"), 
		MEDIUM (2, "Medium"), 
		SMALL (1, "Small"), 
		XS (0, "XSmall");
		
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
