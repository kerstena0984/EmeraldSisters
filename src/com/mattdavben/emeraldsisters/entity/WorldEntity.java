package com.mattdavben.emeraldsisters.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class WorldEntity extends Entity {

	private int width;
	private int height;
	private float x;
	private float y;
	protected Shape collisionShape;

	public WorldEntity() {
	}

	public WorldEntity withCollisionShape(int startingX, int startingY, int width, int height) {
		this.currentPosition = new Vector2f(startingX, startingY);
		this.collisionShape = new Rectangle(startingX, startingY, width, height);
		this.width = width;
		this.height = height;
		return this;
	}

	public void render(Graphics gr, Viewport viewport) {
		gr.draw(new Rectangle(x - viewport.position.x, y - viewport.position.y, width, height));
	}

	@Override
	public void update(GameContainer gc, int delta) {

	}

	public void renderCollisionBox(Viewport viewport, Graphics gr) {
		float x = collisionShape.getX();
		float y = collisionShape.getY();
		collisionShape.setX(x - viewport.position.x);
		collisionShape.setY(y - viewport.position.y);
		gr.setColor(Color.red);
		gr.draw(collisionShape);
		collisionShape.setX(x);
		collisionShape.setY(y);
	}

	public Shape getCollisionShape() {
		return collisionShape;
	}

	@Override
	public void render(GameContainer gc, Viewport viewport, Graphics gr) {

	}

}
