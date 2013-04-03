package com.mattdavben.emeraldsisters.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

import com.mattdavben.emeraldsisters.world.Viewport;

public class WorldEntity extends Entity {

	protected Polygon collisionShape;

	public WorldEntity() {
	}

	public WorldEntity withCollisionShape(int startingX, int startingY,
			int width, int height) {
		float[] points = { startingX, startingY, startingX + width, startingY,
				startingX + width, startingY + height, startingX,
				startingY + height };
		this.collisionShape = new Polygon(points);
		return this;
	}

	public void update(GameContainer gc, int delta) {
	}

	public Polygon getCollisionShape() {
		return collisionShape;
	}

	public Polygon getCollisionShapeCopy() {
		return collisionShape.copy();
	}

	@Override
	public void render(GameContainer gc, Viewport viewport, Graphics gr) {

	}

}
