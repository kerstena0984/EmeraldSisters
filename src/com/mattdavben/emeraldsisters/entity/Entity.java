package com.mattdavben.emeraldsisters.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public abstract class Entity {

	protected Vector2f currentPosition;

	public Entity() {
		currentPosition = new Vector2f(0, 0);
	}

	public Vector2f getPosition() {
		return currentPosition;
	}

	public void setPosition(Vector2f position) {
		this.currentPosition = position;
	}

	public abstract void update(GameContainer gc, int delta);

	public abstract void render(GameContainer gc, Viewport viewport, Graphics gr);

}