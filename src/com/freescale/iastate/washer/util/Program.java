package com.freescale.iastate.washer.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.freescale.iastate.washer.util.Cycle.Level;
import com.freescale.iastate.washer.util.Cycle.Size;

public class Program implements Parcelable{
	
	private static final String DEBUG_TAG = "Program";
	
	// Constant values
	String name;
	int id;
	private boolean custom;
	private String description;
	private boolean steam;
	private Level agitation;
	
	Wash wash_cycle;
	Rinse rinse_cycle;
	Spin spin_cycle;
	
	// Values determined by user
	// Higher soil level increases wash time
	private Level soil_level;
	
	// Load size affects water level
	private Size load_size;
	
	public Program() {		
		wash_cycle = new Wash();
		rinse_cycle = new Rinse();
		spin_cycle = new Spin();
	}
	
	public Program(Parcel parcel){
		readFromParcel(parcel);
	}
	
	public Program(String name, String description, 
				boolean steam, Level agitation,
				Wash wash_cycle, Rinse rinse_cycle, Spin spin_cycle, 
				Level soil_level, Size load_size){
		this.name = name;
		this.description = description;
		this.wash_cycle = wash_cycle;
		this.rinse_cycle = rinse_cycle;
		this.spin_cycle = spin_cycle;
		this.soil_level = soil_level;
		this.load_size = load_size;
		this.steam = steam;
		this.agitation = agitation;
	}
	
	public Program(long id, String name, String description, 
			boolean steam, Level agitation,
			Wash wash_cycle, Rinse rinse_cycle, Spin spin_cycle){
		this.name = name;
		this.description = description;
		this.wash_cycle = wash_cycle;
		this.rinse_cycle = rinse_cycle;
		this.spin_cycle = spin_cycle;
		this.soil_level = null;
		this.load_size = null;
		this.agitation = agitation;
		this.steam = steam;
	}
	
	public Program(String name, String description, Level soil_level,
			Size load_size){
		this.name = name;
		this.description = description;
		
		this.wash_cycle = null;
		this.rinse_cycle = null;
		this.spin_cycle = null;
		
		this.soil_level = soil_level;
		this.load_size = load_size;
	}

	public String getName() {
		return name;
	}
		
	@Override
	public String toString(){
		return description;	
	}
	
	public boolean getCustom(){
		return custom;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public Wash getWashCycle(){
		return wash_cycle;
	}
	
	public void setWashCycle(Wash wash_cycle){
		this.wash_cycle = wash_cycle;
	}
	
	public Rinse getRinseCycle(){
		return rinse_cycle;
	}
	
	public void setRinseCycle(Rinse rinse_cycle){
		this.rinse_cycle = rinse_cycle;
	}
	
	public Spin getSpinCycle(){
		return spin_cycle;
	}
	
	public void setSpinCycle(Spin spin_cycle){
		this.spin_cycle = spin_cycle;
	}

	public int getLength() {
		int total_length = 0;
		
		if(wash_cycle != null){
			total_length += wash_cycle.getPresoakLength();
			total_length += wash_cycle.getLength();
		}
		
		if(rinse_cycle != null)
			total_length += rinse_cycle.getLength();
		
		if(spin_cycle != null)
			total_length += spin_cycle.getLength();
		
		return total_length;
	}

	public long getId() {
		return id;
	}
	
	public Size getLoadSize(){
		return load_size;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Log.v(DEBUG_TAG, "writeToParcel..."+ flags);
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeByte((byte) (custom ? 1 : 0));
		dest.writeString(description);
		dest.writeByte((byte) (steam ? 1 : 0));
		
		if(agitation != null)
			dest.writeInt(agitation.getID());
		else
			dest.writeInt(2);
		
		if(soil_level != null)
			dest.writeInt(soil_level.getID());
		else
			dest.writeInt(1);
		
		if(load_size != null)
			dest.writeInt(load_size.getID());
		else
			dest.writeInt(2);
		
		dest.writeParcelable(spin_cycle, flags);
		dest.writeParcelable(rinse_cycle, flags);
		dest.writeParcelable(wash_cycle, flags);
	}
	
	private void readFromParcel(Parcel in){
		Log.v(DEBUG_TAG, "readFromParcel...");
		
		id = in.readInt();
		name = in.readString();
		
		custom = (in.readByte() == 1) ? true : false; 
		
		description = in.readString();
		
		steam = (in.readByte() == 1) ? true : false;
		
		Level levels[] = Level.values();
		
		int ag = in.readInt();
		for(int i = 0; i < levels.length; i++) {
			if(levels[i].getID() == ag) {
				agitation = levels[i];
			}
		}
		
		int soil = in.readInt();
		for(int i = 0; i < levels.length; i++){
			if(levels[i].getID() == soil){
				soil_level = levels[i];
			}
		}
		
		
		Size[] sizes = Size.values();
		int load = in.readInt();
		for(int i = 0; i < sizes.length; i++){
			if(sizes[i].getID() == load){
				load_size = sizes[i];
			}
		}
		
		spin_cycle = in.readParcelable(Spin.class.getClassLoader());
		rinse_cycle = in.readParcelable(Rinse.class.getClassLoader());
		wash_cycle = in.readParcelable(Wash.class.getClassLoader());
		
	}
	
	public static final Parcelable.Creator CREATOR =
	    	new Parcelable.Creator() {
	            public Program createFromParcel(Parcel in) {
	                return new Program(in);
	            }
	 
	            public Program[] newArray(int size) {
	                return new Program[size];
	            }
	        };

	public Level getSoilLevel() {
		return soil_level;
	}

	public void setLoadSize(Size load_size) {
		this.load_size = load_size;
		
	}

	public void setSoilLevel(Level soil_level) {
		this.soil_level = soil_level;
		
	}

	public static Program getDefaultProgram(){
		Wash wash_cycle = Wash.getDefaultWash();
		Rinse rinse_cycle = Rinse.getDefaultRinse();
		Spin spin_cycle = Spin.getDefaultSpin();
		
		Program program = new Program("Custom", "This program was customized by you!", 
				false, Level.MEDIUM,
				wash_cycle, rinse_cycle, spin_cycle, 
				Level.LOW, Size.MEDIUM);
		
		return program;
		
	}

	public boolean setSoilLevel(int lvl) {
		Level levels[] = Level.values();
		for(int i = 0; i < levels.length; i++){
			if(levels[i].getID() == lvl){
				soil_level = levels[i];
				return true;
			}
		}
		return false;
	}
	
	public boolean setLoadSize(int size){
		Size sizes[] = Size.values();
		for(int i = 0; i < sizes.length; i++){
			if(sizes[i].getID() == size){
				load_size = sizes[i];
				return true;
			}
		}
		return false;
	}

	public Level getAgitation() {
		return agitation;
	}

	public boolean getSteam() {
		return steam;
	}

	public void setSteam(boolean steam) {
		this.steam = steam;
		
	}

	public void setAgitation(Level agitation) {
		this.agitation = agitation;
	}
	
}