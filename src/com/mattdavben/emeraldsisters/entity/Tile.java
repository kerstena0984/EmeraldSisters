package com.mattdavben.emeraldsisters.entity;

import org.newdawn.slick.geom.Vector2f;

import com.mattdavben.emeraldsisters.map.Environment;

public class Tile {

	public final int X, Y;
	protected Vector2f position;

	public Tile(Vector2f position) {
		this.position = position;
		this.X = (int) position.getX() / Environment.TILE_WIDTH;
		this.Y = (int) position.getY() / Environment.TILE_WIDTH;
	}

	public Vector2f getPosition() {
		return position;
	}

}
