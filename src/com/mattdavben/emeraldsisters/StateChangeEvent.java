package com.mattdavben.emeraldsisters;

import org.newdawn.slick.state.BasicGameState;

public class StateChangeEvent {
	private final BasicGameState state;

	public StateChangeEvent(BasicGameState state) {
		this.state = state;
	}

	public BasicGameState getState() {
		return state;
	}
}
