package com.mattdavben.emeraldsisters.player;

import org.newdawn.slick.geom.Vector2f;

public class PlayerMoveEvent {
	
	private Vector2f newPosition;
	
	public PlayerMoveEvent(Vector2f newPosition) {
		this.newPosition = newPosition;
	}
	
	public Vector2f getNewPosition() {
		return newPosition;
	}
	
}
