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
	private float playerX = 15;
	private float playerY = 15;
	int[] animationLength = { 150, 150, 150, 150 };

	public Player(int levelWidth, int levelHeight) throws SlickException {
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

	public void update(int delta, Input input) throws SlickException {
		float multiplier = 0.1f;

		boolean movementKeyIsDown = input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_RIGHT);
		if (movementKeyIsDown) player.update(delta);

		if (input.isKeyDown(Input.KEY_UP)) {
			player = playerWalkingUp;
			playerY -= delta * multiplier;
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			player = playerWalkingDown;
			playerY += delta * multiplier;
		} else if (input.isKeyDown(Input.KEY_LEFT)) {
			player = playerWalkingLeft;
			playerX -= delta * multiplier;
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			player = playerWalkingRight;
			playerX += delta * multiplier;
		}

		viewportX = playerX + 8 - (800 / 2);
		viewportY = playerY + 2 - (600 / 2);

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
