package com.mattdavben.emeraldsisters;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player {

	private float playerX;
	private float playerY;
	private PlayerSprite playerSprite;
	private float steps;
	private int xMove;
	private int yMove;
	private float destinationX;
	private float destinationY;
	private Coordinate currentCoordinate;

	public Player() throws SlickException {
		playerSprite = new PlayerSprite("res/Kate.png");
		steps = 0;
		playerX = 0;
		playerY = 44;
		currentCoordinate = new Coordinate((int) playerX / 32, (int) playerY / 32);
	}

	public void draw(float x, float y) throws SlickException {
		playerSprite.draw(x, y, 32, 48);
	}

	private void walk(int x, int y, int delta) {
		float speed = 3.0f;
		float pixelsPerTile = 32;
		float distance = speed * pixelsPerTile * delta / 1000f;

		playerSprite.update(xMove, yMove, delta);

		if (snapToX(distance)) {
			playerX = destinationX;
		} else {
			playerX += x * distance;
		}

		if (snapToY(distance)) {
			playerY = destinationY;
		} else {
			playerY += y * distance;
		}

		steps -= distance;

		if (steps <= 0) steps = 0;

	}

	public void update(int delta, Input input) throws SlickException {
		if (steps == 0.0) {
			if (movementKeyIsDown(input)) {
				steps = 32;
			}
			if (input.isKeyDown(Input.KEY_UP)) {
				yMove = -1;
				destinationY = playerY - 32;
			} else if (input.isKeyDown(Input.KEY_DOWN)) {
				yMove = 1;
				destinationY = playerY + 32;
			} else if (input.isKeyDown(Input.KEY_LEFT)) {
				xMove = -1;
				destinationX = playerX - 32;
			} else if (input.isKeyDown(Input.KEY_RIGHT)) {
				xMove = 1;
				destinationX = playerX + 32;
			}
			if (input.isKeyDown(Input.KEY_LSHIFT)) {
				xMove *= 3;
				yMove *= 3;
			}
		}

		if (steps > 0) {
			walk(xMove, yMove, delta);
		}
		if (steps == 0) {
			xMove = 0;
			yMove = 0;
		}

		currentCoordinate = new Coordinate((int) playerX / 32, (int) playerY / 32);
	}

	private boolean snapToX(float distance) {
		return (destinationX > playerX && destinationX < playerX + distance) || (destinationX < playerX && destinationX > playerX - distance);
	}

	private boolean snapToY(float distance) {
		return (destinationY < playerY && destinationY > playerY - distance) || (destinationY > playerY && destinationY < playerY + distance);
	}

	private boolean movementKeyIsDown(Input input) {
		return input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_RIGHT);
	}

	public float getPlayerX() {
		return playerX;
	}

	public float getPlayerY() {
		return playerY;
	}
}
