package com.mattdavben.emeraldsisters;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player {

	private Animation player, playerWalkingUp, playerWalkingDown, playerWalkingLeft, playerWalkingRight;
	private SpriteSheet characterSheet;
	private float playerPositionX = 0;
	private float playerPositionY = 0;
	int[] animationLength = { 150, 150, 150, 150 };

	public Player() throws SlickException {
		init();
	}

	public void init() throws SlickException {
		characterSheet = new SpriteSheet(new Image("res/Kate.png"), 16, 24);
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
		player.draw(x, y);
	}

	public void update(int delta, Input input) throws SlickException {
		float multiplier = 0.05f;

		boolean movementKeyIsDown = input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_RIGHT);
		if (movementKeyIsDown) player.update(delta);

		if (input.isKeyDown(Input.KEY_UP)) {
			player = playerWalkingUp;
			playerPositionY += delta * multiplier;
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			player = playerWalkingDown;
			playerPositionY -= delta * multiplier;
		} else if (input.isKeyDown(Input.KEY_LEFT)) {
			player = playerWalkingLeft;
			playerPositionX += delta * multiplier;
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			player = playerWalkingRight;
			playerPositionX -= delta * multiplier;
		}
	}

	public float getXPosition() {
		return playerPositionX;
	}

	public float getYPosition() {
		return playerPositionY;
	}
}
