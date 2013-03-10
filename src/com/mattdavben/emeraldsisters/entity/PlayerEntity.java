package com.mattdavben.emeraldsisters.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

public class PlayerEntity extends Entity {

	private Shape collisionShape = null;

	public PlayerEntity(String id, Shape collisionShape) {
		super(id);

		this.collisionShape = collisionShape;
	}

	public Shape getCollisionShape() {
		return collisionShape;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		for (Component component : components) {
			component.update(gc, sb, delta);
		}
		
		collisionShape.setX(position.x);
		collisionShape.setY(position.y + 24);
	}

}
