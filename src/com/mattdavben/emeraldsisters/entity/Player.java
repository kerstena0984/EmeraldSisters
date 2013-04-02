package com.mattdavben.emeraldsisters.entity;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

import com.google.common.eventbus.Subscribe;
import com.mattdavben.emeraldsisters.EventNexus;
import com.mattdavben.emeraldsisters.Viewport;
import com.mattdavben.emeraldsisters.map.Environment;
import com.mattdavben.emeraldsisters.player.PlayerMoveEvent;
import com.mattdavben.emeraldsisters.player.PlayerMoveListener;
import com.mattdavben.emeraldsisters.sprite.CharacterSprite;

public final class Player extends WorldEntity implements PlayerMoveListener {

	private Input input;
	public boolean blockedLeft;
	public boolean blockedRight;
	public boolean blockedUp;
	public boolean blockedDown;
	private CharacterSprite sprite;
	public final static float WALKING_SPEED = 5.0f;

	public Player(Input input, Vector2f startingPosition) throws SlickException {
		EventNexus.register(this);

		this.input = input;
		this.currentPosition = startingPosition;
		sprite = new CharacterSprite("TestingSprite");

		float currentX = currentPosition.x;
		float currentY = currentPosition.y;
		float collisionBoxWidth = sprite.getSpriteWidth() - 8;
		float collisionBoxHeight = sprite.getSpriteHeight() / 2 - 2;
		float collisionBoxOffset = 1;

		float[] points = { currentX + collisionBoxOffset, currentY + collisionBoxOffset,
				currentX + collisionBoxOffset + collisionBoxWidth, currentY + collisionBoxOffset,
				currentX + collisionBoxOffset + collisionBoxWidth, currentY + collisionBoxOffset + collisionBoxHeight,
				currentX + collisionBoxOffset, currentY + collisionBoxOffset + collisionBoxHeight };

		collisionShape = new Polygon(points);

		blockedLeft = false;
		blockedRight = false;
		blockedUp = false;
		blockedDown = false;
	}

	public void render(Viewport viewport) {
		sprite.draw(currentPosition, viewport.position);
	}

	public void update(int delta) {
		updateDirectionBasedOnUserInput();

		float distance = WALKING_SPEED * Environment.TILE_WIDTH * delta / 1000f;

		if (movementButtonIsPressed()) {
			sprite.update(delta);

			if (input.isKeyDown(Input.KEY_UP) && !blockedUp) {
				currentPosition.y -= distance;
			} else if (input.isKeyDown(Input.KEY_DOWN) && !blockedDown) {
				currentPosition.y += distance;
			}
			if (input.isKeyDown(Input.KEY_LEFT) && !blockedLeft) {
				currentPosition.x -= distance;
			} else if (input.isKeyDown(Input.KEY_RIGHT) && !blockedRight) {
				currentPosition.x += distance;
			}
		}

		collisionShape.setX(currentPosition.x + 4);
		collisionShape.setY(currentPosition.y + 24);
	}

	public enum Direction {
		NORTH, SOUTH, WEST, EAST, NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST;
	}

	public void setToNotBlocked() {
		blockedLeft = false;
		blockedRight = false;
		blockedUp = false;
		blockedDown = false;
	}

	private void updateDirectionBasedOnUserInput() {
		Direction direction = Direction.NORTH;
		if (input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_RIGHT) && !input.isKeyDown(Input.KEY_LEFT)) {
			direction = Direction.NORTH;
		} else if (input.isKeyDown(Input.KEY_DOWN) && !input.isKeyDown(Input.KEY_RIGHT)
				&& !input.isKeyDown(Input.KEY_LEFT)) {
			direction = Direction.SOUTH;
		} else if (input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_UP)
				&& !input.isKeyDown(Input.KEY_DOWN)) {
			direction = Direction.WEST;
		} else if (input.isKeyDown(Input.KEY_RIGHT) && !input.isKeyDown(Input.KEY_UP)
				&& !input.isKeyDown(Input.KEY_DOWN)) {
			direction = Direction.EAST;
		} else if (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_RIGHT)) {
			direction = Direction.NORTHEAST;
		} else if (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_LEFT)) {
			direction = Direction.NORTHWEST;
		} else if (input.isKeyDown(Input.KEY_DOWN) && input.isKeyDown(Input.KEY_RIGHT)) {
			direction = Direction.SOUTHEAST;
		} else if (input.isKeyDown(Input.KEY_DOWN) && input.isKeyDown(Input.KEY_LEFT)) {
			direction = Direction.SOUTHWEST;
		}
		sprite.updateDirection(direction);
	}

	private boolean movementButtonIsPressed() {
		return input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_LEFT)
				|| input.isKeyDown(Input.KEY_RIGHT);
	}

	@Subscribe
	public void listen(PlayerMoveEvent event) {
		this.currentPosition = event.getNewPosition();
	}

}
