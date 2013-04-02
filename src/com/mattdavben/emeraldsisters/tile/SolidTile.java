package com.mattdavben.emeraldsisters.tile;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.mattdavben.emeraldsisters.entity.Tile;

public class SolidTile extends Tile {

	private Rectangle collisionBox;

	public SolidTile(Vector2f position, Rectangle collisionBox) {
		super(position);
		this.collisionBox = collisionBox;
	}

	public Rectangle getCollisionBox() {
		return collisionBox;
	}

	public Rectangle getCollisionBoxCopy() {
		return new Rectangle(position.x, position.y, collisionBox.getWidth(),
				collisionBox.getHeight());
	}

}
