package com.freescale.iastate.washer.util;

public class Stain {

	private String type;
	private String fabric;
	private String[] supplies;
	private String[] steps;
	private String notes;
	private String disclaimer;
	private String source;
	private String source_url;
	
	//private Wash wash;
	
	public Stain(String type, String fabric, String supplies,
			String steps, String notes, String disclaimer,
			String source, String source_url) {
		this.type = type;
		this.fabric = fabric;
		
		if(supplies != null)
			this.supplies = supplies.split("[;]");
		else
			this.supplies = null;
		
		if(steps != null)
			this.steps = steps.split("[;]");
		else
			this.steps = null;
		
		this.notes = notes;
		this.disclaimer = disclaimer;
		this.source = source;
		this.source_url = source_url;
	}
	
	public Stain(String type, String fabric, String[] supplies,
			String[] steps, String notes, String disclaimer,
			String source, String source_url) {
		this.type = type;
		this.fabric = fabric;
		this.supplies = supplies;
		this.steps = steps;
		this.notes = notes;
		this.disclaimer = disclaimer;
		this.source = source;
		this.source_url = source_url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFabric() {
		return fabric;
	}

	public void setFabric(String fabric) {
		this.fabric = fabric;
	}

	public String[] getSupplies() {
		return supplies;
	}
	
	public String getSuppliesString() {
		if(supplies == null)
			return null;
		
		int numSupplies = supplies.length;
		
		String toReturn = "";
		for(int i = 0; i < numSupplies; i++) {
			if(i != 0)
				toReturn += ", ";
			toReturn += supplies[i];
		}
		
		return toReturn;
	}

	public void setSupplies(String[] supplies) {
		this.supplies = supplies;
	}

	public String[] getSteps() {
		return steps;
	}
	
	public String getStepsString() {
		if(steps == null)
			return null;
		
		int numSteps = steps.length;
		
		String toReturn = "";
		for(int i = 0; i < numSteps; i++) {
			toReturn += steps[i] +", ";
		}
		
		int lastIndex = toReturn.lastIndexOf(',');
		toReturn = toReturn.substring(0, (lastIndex > 0)?lastIndex:0);
		
		return toReturn;
	}

	public void setSteps(String[] steps) {
		this.steps = steps;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceURL() {
		return source_url;
	}

	public void setSourceURL(String source_url) {
		this.source_url = source_url;
	}
	
}
