package com.freescale.iastate.washer.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Wash extends Cycle{
	
	private static final String DEBUG_TAG = "Wash";
			
	// values stored in database
	private Temperature temp;
	private int presoakLength;
	
	public Wash() {
		super();
	}
	
	public Wash(Parcel parcel){
		readFromParcel(parcel);
	}
	
	public Wash(int length, Temperature temp, Level soil_level,
			int presoakLength) {
		
		//TODO - Utilize soil_level to alter cycle length
		super(length);
		
		this.temp = temp;
		this.presoakLength = presoakLength;
	}
	
	public Wash(int length, Temperature temp, int presoakLength) {
		super(length);
		
		this.temp = temp;
		this.presoakLength = presoakLength;
	}

	public Temperature getTemp(){
		return temp;
	}
	
	public void setPresoak(boolean presoak) {
		if(presoak) {
			presoakLength = 5;
		} else {
			presoakLength = 0;
		}
	}
	
	public boolean getPresoak() {
		if(presoakLength > 0) {
			return true;
		}
		return false;
	}
	
	public int getPresoakLength(){
		return presoakLength;
	}
	
	public void setTemp(Temperature temp){
		this.temp = temp;
	}
	
	public void setPresoakLength(int length){
		this.presoakLength = length;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Log.v(DEBUG_TAG, "writeToParcel..."+ flags);
		dest.writeInt(temp.getID());
		dest.writeInt(presoakLength);
		dest.writeInt(length);
	}
	
	private void readFromParcel(Parcel in){
		Log.v(DEBUG_TAG, "readFromParcel...");
		
		Temperature temps[] = Temperature.values();
		int temperature = in.readInt();
		for(int i = 0; i < temps.length; i++){
			if(temps[i].getID() == temperature){
				temp = temps[i];
			}
		}
				
		presoakLength = in.readInt();
				
		length = in.readInt();
	}
	
	public static final Parcelable.Creator CREATOR =
    	new Parcelable.Creator() {
            public Wash createFromParcel(Parcel in) {
                return new Wash(in);
            }
 
            public Wash[] newArray(int size) {
                return new Wash[size];
            }
        };

	public static Wash getDefaultWash() {
		Wash wash_cycle = new Wash(12, Temperature.COLD, Level.LOW,
				 0);
		return wash_cycle;
	}
}
