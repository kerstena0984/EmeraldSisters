package com.mattdavben.emeraldsisters.map;

public class MapTransitionEvent {

	private final String newMap;

	public MapTransitionEvent(String message) {
		this.newMap = message;
	}

	public String getNewMap() {
		return newMap;
	}
}
