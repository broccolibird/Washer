package com.freescale.iastate.washer.dialmenu;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

public class DialModel {
	public interface Listener {
		void onDialPositionChanged(DialModel sender, int nicksChanged);
		void onDialPressed(DialModel sender);
	}
	
	private List<Listener> listeners = new ArrayList<Listener>(); 

	private int totalNicks;
	
	private int currentNick = 0;
	
	private DialView view = null;
	
	public DialModel() {
	}
	
	public DialModel(DialView parent, int numOptions) {
		view = parent;
		totalNicks = numOptions;
	}

	public DialView getDialView(){
		return view;
	}
	
	public final float getRotationInDegrees() {
		return (360.0f / totalNicks) * currentNick;
	}

	public final void rotate(int nicks) {
		currentNick = (currentNick + nicks);
		if (currentNick >= totalNicks) {
			currentNick %= totalNicks;
		} else if (currentNick < 0) {
			currentNick = (totalNicks + currentNick);
		}
		
		for (Listener listener : listeners) {
			listener.onDialPositionChanged(this, nicks);
		}
	}
	
	public final void makeSelection() {
		for (Listener listener : listeners) {
			listener.onDialPressed(this);
		}
	}
	
	public final List<Listener> getListeners() {
		return listeners;
	}

	public void setTotalNicks(int nicks){
		totalNicks = nicks;
	}
	
	public final int getTotalNicks() {
		return totalNicks;
	}

	public final int getCurrentNick() {
		return currentNick;
	}

	/**
	 * Set the index of the current Nick
	 * @param index
	 */
	public void setCurrentNick(int index){
		currentNick = index;
	}
	
	public final void addListener(Listener listener) {
		listeners.add(listener);
	}
	
	public final void removeListener(Listener listener) {
		listeners.remove(listener);
	}

	private static String getBundlePrefix() {
		return DialModel.class.getSimpleName() + ".";
	}
	
	public final void save(Bundle bundle) {
		String prefix = getBundlePrefix();
		
		bundle.putInt(prefix + "totalNicks", totalNicks);
		bundle.putInt(prefix + "currentNick", currentNick);
	}
	
	public static DialModel restore(Bundle bundle) {
		DialModel model = new DialModel();
		
		String prefix = getBundlePrefix();
		model.totalNicks = bundle.getInt(prefix + "totalNicks");
		model.currentNick = bundle.getInt(prefix + "currentNick");
		
		return model;
	}
	

}
