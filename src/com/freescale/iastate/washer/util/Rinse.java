package com.freescale.iastate.washer.util;

import com.freescale.iastate.washer.util.Cycle.Level;
import com.freescale.iastate.washer.util.Cycle.Temperature;
import com.freescale.iastate.washer.util.Wash.Dispenser;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Rinse extends Cycle{
	
	private static final String DEBUG_TAG = "Rinse";

	private Level agitation;
	private Temperature temp;
	
	public Rinse(int length, Temperature temp, Level agitation, Boolean steam) {
		super(length, steam);
		this.temp = temp;
		this.agitation = agitation;
	}
	

	public Rinse() {
		super();
	}

	public Rinse(Parcel parcel){
		readFromParcel(parcel);
	}

	public Level getAgitation(){
		return agitation;
	}
	
	public void setAgitation(Level agitation){
		this.agitation = agitation;
	}
	
	public Temperature getTemp(){
		return temp;
	}
	
	public void setTemp(Temperature temp){
		this.temp = temp;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Log.v(DEBUG_TAG, "writeToParcel..."+ flags);
		dest.writeInt(temp.getID());
		dest.writeInt(agitation.getID());
		dest.writeByte((byte) (steam ? 1 : 0));
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
		
		Level levels[] = Level.values();
		int ag = in.readInt();
		for(int i = 0; i < levels.length; i++){
			if(levels[i].getID() == ag){
				agitation = levels[i];
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
            public Rinse createFromParcel(Parcel in) {
                return new Rinse(in);
            }
 
            public Rinse[] newArray(int size) {
                return new Rinse[size];
            }
        };

	public static Rinse getDefaultRinse() {
		return new Rinse(8, Temperature.COLD, Level.MEDIUM, false);
	}

}
