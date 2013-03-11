package com.mattdavben.emeraldsisters.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public abstract class Entity {

	protected Vector2f position;

	public Entity() {
		position = new Vector2f(0, 0);
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public abstract void update(GameContainer gc, int delta);

	public abstract void render(GameContainer gc, Viewport viewport, Graphics gr);

}