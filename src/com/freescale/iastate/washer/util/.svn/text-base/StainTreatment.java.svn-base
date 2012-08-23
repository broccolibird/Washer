package com.freescale.iastate.washer.util;

public class StainTreatment {
	
	private String stain;
	private String fabric;
	private String directions;
	private Cycle wash;
	
	StainTreatment(String stain, String fabric, String directions, Cycle wash){
		this.stain = stain;
		this.fabric = fabric;
		this.directions = directions;
		this.wash = wash;
	}
	
	StainTreatment(String stain, String directions, Cycle wash){
		this.stain = stain;
		this.fabric = null;
		this.directions = directions;
		this.wash = wash;
	}
	
	public String getType(){
		return stain;
	}
	
	public void setType(String stain){
		this.stain = stain;
	}
	
	public String getFabric(){
		return fabric;
	}
	
	public void setFabric(String fabric){
		this.fabric = fabric;
	}
	
	public String getDirections(){
		return directions;
	}
	
	public void setDirections(String directions){
		this.directions = directions;
	}
	
	public Cycle getWash(){
		return wash;
	}
	
	public void setWash(Cycle wash){
		this.wash = wash;
	}
}
