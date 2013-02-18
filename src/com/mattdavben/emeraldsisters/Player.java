package com.mattdavben.emeraldsisters;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player {

	private Animation player, playerWalkingUp, playerWalkingDown, playerWalkingLeft, playerWalkingRight;
	private SpriteSheet characterSheet;

	private float viewportX;
	private float viewportY;
	private float playerX = 0;
	private float playerY = 44;
	int[] animationLength = { 150, 150, 150, 150 };

	public Player() throws SlickException {
		init();
	}

	public void init() throws SlickException {
		int playerWidth = 16;
		int playerHeight = 24;
		characterSheet = new SpriteSheet(new Image("res/Kate.png"), playerWidth, playerHeight);
		Image down0 = characterSheet.getSubImage(0, 0);
		Image down1 = characterSheet.getSubImage(1, 0);
		Image down2 = characterSheet.getSubImage(2, 0);
		Image left0 = characterSheet.getSubImage(3, 0);
		Image left1 = characterSheet.getSubImage(4, 0);
		Image left2 = characterSheet.getSubImage(5, 0);
		Image up0 = characterSheet.getSubImage(6, 0);
		Image up1 = characterSheet.getSubImage(7, 0);
		up1.setFilter(Image.FILTER_NEAREST);

		Image[] walkingDown = { down0, down1, down0, down2 };
		Image[] walkingUp = { up0, up1, up0.getFlippedCopy(true, false), up1.getFlippedCopy(true, false) };
		Image[] walkingLeft = { left0, left1, left0, left2 };
		Image[] walkingRight = { left0.getFlippedCopy(true, false), left1.getFlippedCopy(true, false), left0.getFlippedCopy(true, false), left2.getFlippedCopy(true, false), };

		playerWalkingDown = new Animation(walkingDown, animationLength, false);
		playerWalkingUp = new Animation(walkingUp, animationLength, false);
		playerWalkingLeft = new Animation(walkingLeft, animationLength, false);
		playerWalkingRight = new Animation(walkingRight, animationLength, false);
		player = playerWalkingDown;
	}

	public void draw(float x, float y) throws SlickException {
		player.draw(x, y, 32, 48);
	}

	float distanceSum = 0;
	float stepCount = 0;
	float steps = 0;
	int xMove;
	int yMove;

	private void walk(int x, int y, int delta) {
		stepCount++;
		float multiplier = 1.0f;
		float distance = 0.5f * multiplier;

		if (yMove > 0) player = playerWalkingDown;
		else if (yMove < 0) player = playerWalkingUp;
		else if (xMove > 0) player = playerWalkingRight;
		else if (xMove < 0) player = playerWalkingLeft;
		
		player.update(delta);

		playerX += x * distance;
		playerY += y * distance;

		distanceSum += delta * multiplier;
		steps -= 0.5;
	}

	public void update(int delta, Input input) throws SlickException {
		boolean movementKeyIsDown = input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_RIGHT);

		if (steps == 0) {
			if (movementKeyIsDown) {
				steps = 32;
			}
			if (input.isKeyDown(Input.KEY_UP)) {
				yMove = -1;
			} else if (input.isKeyDown(Input.KEY_DOWN)) {
				yMove = 1;
			} else if (input.isKeyDown(Input.KEY_LEFT)) {
				xMove = -1;
			} else if (input.isKeyDown(Input.KEY_RIGHT)) {
				xMove = 1;
			}
		}

		if (steps > 0) {
			walk(xMove, yMove, delta);
		}
		if (steps == 0){
			xMove = 0;
			yMove = 0;
		}

		viewportX = playerX + 16 - (800 / 2);
		viewportY = playerY + 24 - (600 / 2);

		if (viewportX <= 0.0f) viewportX = 0.0f;
		if (viewportY <= 0.0f) viewportY = 0.0f;
		if (viewportX >= (1280 - Main.WIDTH)) viewportX = (1280 - Main.WIDTH);
		if (viewportY >= (1280 - Main.HEIGHT)) viewportY = (1280 - Main.HEIGHT);
	}

	public float getPlayerX() {
		return playerX;
	}

	public float getPlayerY() {
		return playerY;
	}

	public float getViewportX() {
		return viewportX;
	}

	public float getViewportY() {
		return viewportY;
	}
}
