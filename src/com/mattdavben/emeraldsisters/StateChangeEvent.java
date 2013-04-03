package com.mattdavben.emeraldsisters;

public class StateChangeEvent {
	private final int state;

	public StateChangeEvent(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}
}
