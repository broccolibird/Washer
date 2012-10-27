package com.freescale.iastate.washer.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.freescale.iastate.washer.util.Cycle.Level;
import com.freescale.iastate.washer.util.Cycle.Temperature;
import com.freescale.iastate.washer.util.Wash.Dispenser;

public class Wash extends Cycle{
	
	private static final String DEBUG_TAG = "Wash";
	
	public enum Dispenser{
		BLEACH (3, "Bleach"), 
		DETERGENT_PLUS_FABRIC_SOFTENER (2, "Detergent plus Fabric Softener"), 
		DETERGENT (1, "Detergent"),
		NONE (0, "None");
		
		private int id;
		private String label;
		
		private Dispenser(int id, String label){
			this.id = id;
			this.label = label;
		}
		
		public int getID(){ return id;}

		public CharSequence getLabel() { return label; }
	};
	
	// use default settings or set from soil_level/load_size unless otherwise requested
	private Dispenser dispenser;
	private Level dispense_amount;
		
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
			Dispenser dispenser, Level dispense_amount,
			int presoakLength) {
		
		//TODO - Utilize soil_level to alter cycle length
		super(length);
		
		this.temp = temp;
		this.dispenser = dispenser;
		this.dispense_amount = dispense_amount;
		this.presoakLength = presoakLength;
	}
	
	public Wash(int length, Temperature temp, int presoakLength) {
		super(length);
		
		this.temp = temp;
		this.dispenser = Dispenser.NONE;
		this.dispense_amount = Cycle.Level.NONE;
		this.presoakLength = presoakLength;
	}

	public Temperature getTemp(){
		return temp;
	}
		
	public int getPresoakLength(){
		return presoakLength;
	}
	
	public Dispenser getDispenser(){
		return dispenser;
	}
	
	public Level getDispenseAmount(){
		return dispense_amount;
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
		dest.writeInt(dispense_amount.getID());
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
		
		presoakLength = in.readInt();
				
		length = in.readInt();
		
		Dispenser[] dispensers = Dispenser.values();
		int disp = in.readInt();
		for(int i = 0; i < dispensers.length; i++){
			if(dispensers[i].getID() == disp){
				dispenser = dispensers[i];
			}
		}
		
		int disp_amt = in.readInt();
		for(int i = 0; i < levels.length; i++){
			if(levels[i].getID() == disp_amt){
				dispense_amount = levels[i];
			}
		}
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

	public void setDispenser(Dispenser dispenser) {
		this.dispenser = dispenser;
		
	}

	public void setDispenseAmount(Level dispense_amt) {
		this.dispense_amount = dispense_amt;
		
	}

	public static Wash getDefaultWash() {
		Wash wash_cycle = new Wash(12, Temperature.COLD, Level.LOW,
				Dispenser.DETERGENT, Level.MEDIUM, 0);
		return wash_cycle;
	}
}
