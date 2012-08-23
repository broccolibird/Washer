package com.freescale.iastate.washer.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Spin extends Cycle{
	
	private static final String DEBUG_TAG = "Spin";

	Level spin_speed;
	
	public Spin(int length, Level spin_speed, Boolean steam) {
		super(length, steam);
		this.spin_speed = spin_speed;
	}


	public Spin() {
		super();
	}
	
	public Spin(Parcel parcel) {
		readFromParcel(parcel);
	}


	public Level getSpinSpeed(){
		return spin_speed;
	}
	
	public void setSpinSpeed(Level spin_speed){
		this.spin_speed = spin_speed;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Log.v(DEBUG_TAG, "writeToParcel..."+ flags);
		dest.writeInt(spin_speed.getID());
		dest.writeByte((byte) (steam ? 1 : 0));
		dest.writeInt(length);
		
	}
	
	private void readFromParcel(Parcel in){
		Log.v(DEBUG_TAG, "readFromParcel...");
		
		Level levels[] = Level.values();
		int speed = in.readInt();
		for(int i = 0; i < levels.length; i++){
			if(levels[i].getID() == speed){
				spin_speed = levels[i];
			}
		}
		
		byte steam_byte = in.readByte();
		if(steam_byte == 1)
			steam = true;
		else
			steam = false;
		
		length = in.readInt();
	}
	
	public static final Parcelable.Creator CREATOR =
    	new Parcelable.Creator() {
            public Spin createFromParcel(Parcel in) {
                return new Spin(in);
            }
 
            public Spin[] newArray(int size) {
                return new Spin[size];
            }
        };

	public static Spin getDefaultSpin() {
		return new Spin(5, Level.MEDIUM, false);
	}
}
